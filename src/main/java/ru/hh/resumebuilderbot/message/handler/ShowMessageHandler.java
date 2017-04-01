package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.ShowAllQuestionGenerator;

import java.util.Queue;

public class ShowMessageHandler extends MessageHandler {
    @Override
    public Queue<QuestionGenerator> handle(Answer answer) {
        queue.add(new ShowAllQuestionGenerator());
        return queue;
    }
}
