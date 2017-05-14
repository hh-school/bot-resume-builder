package ru.hh.resumebuilderbot.telegram.handler.message;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.DBService;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.database.model.gender.Gender;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;

import java.util.ArrayList;
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
            currentNodeId = graph.getNextNodeIndex(currentNodeId, answer);
            dbService.saveNodeId(telegramId, currentNodeId);
        } else {
            questions.add(new Question(TextsStorage.getText(TextId.INVALID_ANSWER)));
        }
        questions.add(graph.getNode(currentNodeId).getQuestion());
        return questions;
    }

    private void saveValue(Long telegramId, String field, String value) {
        switch (field) {
            case "phone":
                dbService.savePhoneNumber(telegramId, value);
                break;
            case "firstName":
                dbService.saveFirstname(telegramId, value);
                break;
            case "lastName":
                dbService.saveLastName(telegramId, value);
                break;
            case "sex":
                dbService.saveGender(telegramId, Gender.fromCode(value.charAt(0)));
                break;
            case "town":
                dbService.saveUserArea(telegramId, value);
                break;
            case "educationType":
                dbService.addNewEducation(telegramId);
                dbService.saveEducationLevel(telegramId, EducationLevel.fromCode(value));
                break;
            case "institution":
                dbService.saveInstitute(telegramId, value);
                break;
            default:
                log.warn("User {}. Не найдено поле {} в базе данных. Попытка сохранить данные в невалидном поле",
                        telegramId, field);
                break;
        }
    }
}
