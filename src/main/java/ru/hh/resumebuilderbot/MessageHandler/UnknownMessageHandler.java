package ru.hh.resumebuilderbot.MessageHandler;


import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.QuestionGenerator;
import ru.hh.resumebuilderbot.TextsStorage;

public class UnknownMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        return new FixedQuestionGenerator(TextsStorage.getText("Unknown"));
    }
}
