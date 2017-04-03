package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.generator.ShowAllQuestionGenerator;

public class ShowMessageHandler extends MessageHandler {
    @Override
    public QuestionGeneratorsQueue handle(Answer answer) {
        questionsQueue.add(new ShowAllQuestionGenerator());
        return questionsQueue;
    }
}
