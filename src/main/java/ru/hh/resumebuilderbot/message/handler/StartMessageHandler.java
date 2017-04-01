package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.TextsStorage;
import ru.hh.resumebuilderbot.UserDataStorage;
import ru.hh.resumebuilderbot.question.generator.FirstQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;

import java.util.Queue;

public class StartMessageHandler extends ProtoMessageHandler {
    @Override
    public Queue<QuestionGenerator> handle(Answer answer) {
        ChatId chatId = answer.getChatId();
        if (UserDataStorage.contains(chatId)) {
            queue.add(new FixedQuestionGenerator(TextsStorage.TextId.ALREADY_STARTED));
        } else {
            UserDataStorage.startNewChat(chatId);
            queue.add(new FixedQuestionGenerator(TextsStorage.TextId.HELLO));
            queue.add(new FirstQuestionGenerator());
        }
        return queue;
    }
}
