package ru.hh.resumebuilderbot.message.handler;


import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.TextsStorage;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;

import java.util.Queue;

public class UnknownMessageHandler extends ProtoMessageHandler implements MessageHandler {
    @Override
    public Queue<QuestionGenerator> handle(Answer answer) {
        queue.add(new FixedQuestionGenerator(TextsStorage.TextId.UNKNOWN));
        return queue;
    }
}
