package ru.hh.resumebuilderbot.message.handler;


import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;
import ru.hh.resumebuilderbot.texts.storage.TextId;

import java.util.Queue;

public class UnknownMessageHandler extends MessageHandler {
    @Override
    public Queue<QuestionGenerator> handle(Answer answer) {
        queue.add(new FixedQuestionGenerator(TextId.UNKNOWN));
        return queue;
    }
}
