package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.UserDataStorage;
import ru.hh.resumebuilderbot.TextsStorage;
import ru.hh.resumebuilderbot.question.generator.FirstQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;

public class ClearMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        UserDataStorage.clear(answer.getChatId());
        QuestionGenerator result = new FirstQuestionGenerator();
        result.setPrefix(TextsStorage.getText("Cleared"));
        return result;
    }
}
