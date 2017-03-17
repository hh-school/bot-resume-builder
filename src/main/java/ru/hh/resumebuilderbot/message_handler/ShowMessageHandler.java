package ru.hh.resumebuilderbot.message_handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question_generator.QuestionGenerator;
import ru.hh.resumebuilderbot.question_generator.ShowAllQuestionGenerator;

public class ShowMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        return new ShowAllQuestionGenerator();
    }
}
