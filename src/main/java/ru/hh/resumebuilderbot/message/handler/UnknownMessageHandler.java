package ru.hh.resumebuilderbot.message.handler;


import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;
import ru.hh.resumebuilderbot.TextsStorage;

public class UnknownMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        return new FixedQuestionGenerator(TextsStorage.getText("Unknown"));
    }
}
