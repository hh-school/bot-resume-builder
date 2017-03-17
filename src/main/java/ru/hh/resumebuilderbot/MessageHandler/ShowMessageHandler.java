package ru.hh.resumebuilderbot.MessageHandler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.QuestionGenerator.QuestionGenerator;
import ru.hh.resumebuilderbot.QuestionGenerator.ShowAllQuestionGenerator;

public class ShowMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        return new ShowAllQuestionGenerator();
    }
}
