package ru.hh.resumebuilderbot.telegram.handler.message;

import retrofit2.Response;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.Config;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.http.HHHTTPService;
import ru.hh.resumebuilderbot.http.HHUtils;
import ru.hh.resumebuilderbot.http.response.entity.Vacancy;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.ReplyKeyboardEnum;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator.GenderValidator;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnswerMessageHandler extends MessageHandler {
    private final HHHTTPService hhHTTPService;

    public AnswerMessageHandler(DBProcessor dbProcessor, Graph graph, HHHTTPService hhHTTPService) {
        super(dbProcessor, graph, AnswerMessageHandler.class);
        this.hhHTTPService = hhHTTPService;
    }

    @Override
    public List<Question> handle(Long telegramId, Answer answer) {
        Integer currentNodeId = dbProcessor.getNodeId(telegramId);
        QuestionNode currentQuestionNode = graph.getNode(currentNodeId);
        log.info("User {} answer {} for question {}", telegramId, answer.getAnswerBody(),
                currentQuestionNode.getQuestion().getText());
        List<Question> questions = new ArrayList<>(2);
        if (answerIsValid(currentQuestionNode, answer, telegramId)) {
            String databaseField = currentQuestionNode.getFieldNameToSave();
            if (databaseField != null) {
                saveValue(telegramId, databaseField, answer.getAnswerBody().toString());
            }
            Integer nextNodeId = currentQuestionNode.getNextIndex(answer);
            currentQuestionNode = graph.getNode(nextNodeId);
            dbProcessor.setNodeId(telegramId, nextNodeId);
        } else {
            questions.add(new Question(currentQuestionNode.getInvalidAnswerNotification()));
        }
        if (currentNodeId == 12 && answer.getAnswerBody().toString().equals("Опубликовать резюме на сайте hh.ru")) {
            handleResumePush(telegramId);
        }
        questions.add(currentQuestionNode.getQuestion());

        if (currentNodeId == 12 && answer.getAnswerBody().toString().equals("Опубликовать резюме на сайте hh.ru")) {
            questions.addAll(getSimilarVacancies(telegramId));
        }
        return questions;
    }

    private void handleResumePush(Long telegramId) {
        User user = dbProcessor.getUser(telegramId);
        String authorizationHeader = HHUtils.buildAuthorizationHeader(
                Config.ACCESS_TOKEN_TYPE,
                Config.ACCESS_TOKEN
        );
        try {
            Response<Void> createResponse = hhHTTPService.createResume(user, authorizationHeader).execute();
            if (createResponse.code() != 201) {
                log.error("Error at resume push {}", createResponse.errorBody().string());
                return;
            }
            String hhResumeId = HHUtils.getResumeId(createResponse.headers().get("Location"));
            dbProcessor.setHHResumeId(telegramId, hhResumeId);
            Response<Void> publishResponse = hhHTTPService.publishResume(hhResumeId, authorizationHeader).execute();
            if (publishResponse.code() != 204) {
                log.error("Error at resume publish {}", publishResponse.errorBody().string());
            }
        } catch (IOException e) {
            log.error("Error at resume push", e);
        }
    }

    private List<Question> getSimilarVacancies(Long telegramId) {
        User user = dbProcessor.getUser(telegramId);
        String authorizationHeader = HHUtils.buildAuthorizationHeader(
                Config.ACCESS_TOKEN_TYPE,
                Config.ACCESS_TOKEN
        );
        List<Question> questions = new ArrayList<>();
        try {
            Response<List<Vacancy>> vacancyResponse = hhHTTPService.listResumeSimilarVacancies(user.getHhResumeId(),
                    authorizationHeader).execute();
            if (vacancyResponse.code() != 200) {
                log.error("Error at vacancy get {}", vacancyResponse.errorBody().string());
                return questions;
            }
            int maxQuestionAmount = vacancyResponse.body().size() > 3 ? 3 : vacancyResponse.body().size();
            for (Vacancy vacancy : vacancyResponse.body().subList(0, maxQuestionAmount)) {
                Question question = new Question(vacancy.getUrl(), Question.DEFAULT_SUGGEST_TYPE,
                        ReplyKeyboardEnum.NEGOTIATION);
                question.setCallbackData("negotiation:" + vacancy.getId());
                questions.add(question);
            }
        } catch (IOException e) {
            log.error("Error at vacancies get", e);
        }
        return questions;
    }

    private void saveValue(Long telegramId, String field, String value) {
        switch (field) {
            case "firstName":
                dbProcessor.setFirstName(telegramId, value);
                break;
            case "lastName":
                dbProcessor.setLastName(telegramId, value);
                break;
            case "phoneNumber":
                //FIXME чтобы не было попытки сохранить строковый ответ в поле телефона, но опросник пошел дальше
                if (!value.equals("Ввести другой номер телефона")) {
                    dbProcessor.setPhoneNumber(telegramId, value);
                }
                break;
            case "email":
                dbProcessor.setEmail(telegramId, value);
                break;
            case "birthDate":
                dbProcessor.setBirthDate(telegramId, getDateFromString(value, "yyyy.MM.dd"));
                break;
            case "gender":
                dbProcessor.setGender(telegramId, GenderValidator.getGenderFromCode(value));
                break;
            case "area":
                dbProcessor.saveUserArea(telegramId, value, null);
                break;
            case "educationType":
                dbProcessor.createUserEducation(telegramId, EducationLevel.fromCode(value));
                break;
            case "institute":
                dbProcessor.setCurrentEducationInstituteName(telegramId, value);
                break;
            case "faculty":
                dbProcessor.setCurrentEducationFacultyName(telegramId, value);
                break;
            case "speciality":
                dbProcessor.setCurrentEducationSpecialityName(telegramId, value);
                break;
            case "educationEndDate":
                dbProcessor.setCurrentEducationYear(telegramId, Integer.valueOf(value));
                break;
            case "experienceAdd":
                if (value.equals("Да")) {
                    dbProcessor.createUserExperience(telegramId);
                }
                break;
            case "company":
                dbProcessor.saveCurrentExperienceCompany(telegramId, value);
                break;
            case "workPosition":
                dbProcessor.setCurrentExperiencePosition(telegramId, value);
                break;
            case "workBeginDate":
                dbProcessor.setCurrentExperienceStartDate(telegramId, getDateFromString(value, "yyyy.MM"));
                break;
            case "workEndDate":
                dbProcessor.setCurrentExperienceEndDate(telegramId, getDateFromString(value, "yyyy.MM"));
                break;
            case "experienceDescription":
                dbProcessor.setCurrentExperienceDescription(telegramId, value);
                break;
            case "career_objective":
                dbProcessor.setCareerObjective(telegramId, value);
                break;
            case "salary":
                dbProcessor.saveSalary(telegramId, Integer.valueOf(value));
                break;
            case "skill":
                if (!value.equals("/stop")) {
                    dbProcessor.saveUserSkill(telegramId, value);
                }
                break;
            default:
                log.warn("User {}. Не найдено поле {} в базе данных. Попытка сохранить данные в невалидном поле",
                        telegramId, field);
                break;
        }
    }

    private Date getDateFromString(String rawDate, String pattern) {
        try {
            DateFormat format = new SimpleDateFormat(pattern);
            return format.parse(rawDate);
        } catch (ParseException e) {
            log.error("Date conversation failed", e);
            return new Date(System.currentTimeMillis());
        }
    }

    private boolean answerIsValid(QuestionNode currentQuestionNode, Answer answer, Long telegramId) {
        if (currentQuestionNode.getQuestion().getReplyKeyboardEnum() == ReplyKeyboardEnum.STRONG_SUGGEST) {
            if (currentQuestionNode.getFieldNameToSave().equals("area") &&
                    dbProcessor.getUser(telegramId).getArea() == null) {
                return false;
            } else if (currentQuestionNode.getFieldNameToSave().equals("career_objective") &&
                    dbProcessor.getUser(telegramId).getCareerObjective() == null) {
                return false;
            }
        }
        return currentQuestionNode.answerIsValid(answer);
    }
}
