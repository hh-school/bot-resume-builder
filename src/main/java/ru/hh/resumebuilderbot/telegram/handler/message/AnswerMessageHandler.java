package ru.hh.resumebuilderbot.telegram.handler.message;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator.GenderValidator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnswerMessageHandler extends MessageHandler {
    public AnswerMessageHandler(DBProcessor dbProcessor, Graph graph) {
        super(dbProcessor, graph, AnswerMessageHandler.class);
    }

    @Override
    public List<Question> handle(Long telegramId, Answer answer) {
        Integer currentNodeId = dbProcessor.getNodeId(telegramId);
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
            dbProcessor.setNodeId(telegramId, nextNodeId);
        } else if (currentNodeId == 3 && answer.getAnswerBody().toString().equals("Ввести другой номер телефона")) {
            //FIXME чтобы не было попытки сохранить строковый ответ в поле телефона, но опросник пошел дальше
            Integer nextNodeId = currentQuestionNode.getNextIndex(answer);
            currentQuestionNode = graph.getNode(nextNodeId);
            dbProcessor.setNodeId(telegramId, nextNodeId);
        } else {
            questions.add(new Question(currentQuestionNode.getInvalidAnswerNotification()));
        }
        if (currentNodeId == 13)
        {
            // push
        }
        questions.add(currentQuestionNode.getQuestion());
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
                dbProcessor.setPhoneNumber(telegramId, value);
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
}
