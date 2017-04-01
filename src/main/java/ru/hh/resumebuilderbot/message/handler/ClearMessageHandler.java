package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.TextsStorage;
import ru.hh.resumebuilderbot.UserDataStorage;
import ru.hh.resumebuilderbot.question.generator.FirstQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;

import java.util.Queue;

public class ClearMessageHandler extends ProtoMessageHandler {
    @Override
    public Queue<QuestionGenerator> handle(Answer answer) {
        UserDataStorage.clear(answer.getChatId());
        queue.add(new FixedQuestionGenerator(TextsStorage.TextId.CLEARED));
        queue.add(new FirstQuestionGenerator());
        return queue;
    }
}
