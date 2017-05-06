package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.DBService;
import ru.hh.resumebuilderbot.TelegramUser;
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
    public List<Question> handle(TelegramUser telegramUser, Answer answer) {
        Integer currentNodeId = dbService.getNodeId(telegramUser);
        QuestionNode currentQuestionNode = graph.getNode(currentNodeId);
        log.info("User {} answer {} for question {}", telegramUser.getId(), answer.getAnswerBody(),
                currentQuestionNode.getQuestion().getText());
        List<Question> questions = new ArrayList<>(2);
        if (currentQuestionNode.answerIsValid(answer)) {
            String databaseField = currentQuestionNode.getFieldNameToSave();
            if (databaseField != null) {
                saveValue(telegramUser, databaseField, answer.getAnswerBody().toString());
            }
            currentNodeId = graph.getNextNodeIndex(currentNodeId, answer);
            dbService.saveNodeId(telegramUser, currentNodeId);
        } else {
            questions.add(new Question(TextsStorage.getText(TextId.INVALID_ANSWER)));
        }
        questions.add(graph.getNode(currentNodeId).getQuestion());
        return questions;
    }
}
