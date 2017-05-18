package ru.hh.resumebuilderbot.telegram.handler.message;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.DBService;
import ru.hh.resumebuilderbot.database.model.SalaryCurrency;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.database.model.gender.Gender;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnswerMessageHandler extends MessageHandler {
    public AnswerMessageHandler(DBService dbService, Graph graph) {
        super(dbService, graph);
    }

    @Override
    public List<Question> handle(Long telegramId, Answer answer) {
        Integer currentNodeId = dbService.getNodeId(telegramId);
        QuestionNode currentQuestionNode = graph.getNode(currentNodeId);
        log.info("User {} answer {} for question {}", telegramId, answer.getAnswerBody(),
                currentQuestionNode.getQuestion().getText());
        List<Question> questions = new ArrayList<>(2);
        if (currentQuestionNode.answerIsValid(answer)) {
            String databaseField = currentQuestionNode.getFieldNameToSave();
            if (databaseField != null) {
                saveValue(telegramId, databaseField, answer.getAnswerBody().toString());
            }
            Integer nextNodeId = currentQuestionNode.getNextIndex(answer);
            currentQuestionNode = graph.getNode(nextNodeId);
            dbService.saveNodeId(telegramId, nextNodeId);
        } else if (currentNodeId == 3) {
            //FIXME чтобы не было попытки сохранить строковый ответ в поле телефона, но опросник пошел дальше
            Integer nextNodeId = currentQuestionNode.getNextIndex(answer);
            currentQuestionNode = graph.getNode(nextNodeId);
            dbService.saveNodeId(telegramId, nextNodeId);
        } else {
            questions.add(new Question(TextsStorage.getText(TextId.INVALID_ANSWER)));
        }
        questions.add(currentQuestionNode.getQuestion());
        return questions;
    }

    private void saveValue(Long telegramId, String field, String value) {
        switch (field) {
            case "firstName":
                dbService.saveFirstname(telegramId, value);
                break;
            case "lastName":
                dbService.saveLastName(telegramId, value);
                break;
            case "phoneNumber":
                dbService.savePhoneNumber(telegramId, value);
                break;
            case "birthDate":
                dbService.saveBirthDate(telegramId, getDateFromString(value, "yyyy.MM.dd"));
                break;
            case "gender":
                dbService.saveGender(telegramId, getGenderFromCode(value));
                break;
            case "area":
                dbService.saveUserArea(telegramId, value, null);
                break;
            case "educationType":
                dbService.addNewEducation(telegramId);
                dbService.saveEducationLevel(telegramId, EducationLevel.fromCode(value));
                break;
            case "institute":
                dbService.saveInstitute(telegramId, value);
                break;
            case "faculty":
                dbService.saveFaculty(telegramId, value);
                break;
            case "speciality":
                dbService.saveSpeciality(telegramId, value);
                break;
            case "educationEndDate":
                dbService.saveEducationYear(telegramId, Integer.valueOf(value));
                break;
            case "experienceAdd":
                if (value.equals("Да")) {
                    dbService.addNewExperience(telegramId);
                }
                break;
            case "company":
                dbService.saveExperienceCompany(telegramId, value);
                break;
            case "workPosition":
                dbService.saveExperiencePosition(telegramId, value);
                break;
            case "workBeginDate":
                dbService.saveExperienceStartDate(telegramId, getDateFromString(value, "yyyy.MM"));
                break;
            case "workEndDate":
                dbService.saveExperienceEndDate(telegramId, getDateFromString(value, "yyyy.MM"));
                break;
            case "experienceDescription":
                dbService.saveExperienceDescription(telegramId, value);
                break;
            case "career_objective":
                dbService.saveCareerObjective(telegramId, value);
                break;
            case "salaryAmount":
                dbService.saveSalaryAmount(telegramId, Integer.valueOf(value));
                break;
            case "salaryCurrency":
                dbService.saveSalaryCurrency(telegramId, SalaryCurrency.valueOf(value));
                break;
            case "skill":
                dbService.saveSkill(telegramId, value);
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
            log.error("Date conversation failed with error {}", e.getMessage());
            return new Date(System.currentTimeMillis());
        }
    }

    private Gender getGenderFromCode(String code) {
        if (code.equals("Мужской") || code.equals("Одеколон")) {
            return Gender.MALE;
        }
        if (code.equals("Женский") || code.equals("Духи")) {
            return Gender.FEMALE;
        }
        throw new UnsupportedOperationException("The code " + code + " is not supported!");
    }
}
