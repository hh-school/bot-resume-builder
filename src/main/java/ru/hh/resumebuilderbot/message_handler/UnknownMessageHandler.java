package ru.hh.resumebuilderbot.message_handler;


import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question_generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question_generator.QuestionGenerator;
import ru.hh.resumebuilderbot.TextsStorage;

public class UnknownMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        return new FixedQuestionGenerator(TextsStorage.getText("Unknown"));
    }
}
