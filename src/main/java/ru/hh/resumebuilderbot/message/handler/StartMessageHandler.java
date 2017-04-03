package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.question.generator.FirstQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

import java.util.Queue;

public class StartMessageHandler extends MessageHandler {
    @Override
    public Queue<QuestionGenerator> handle(Answer answer) {
        ChatId chatId = answer.getChatId();
        if (UserDataStorage.contains(chatId)) {
            queue.add(new FixedQuestionGenerator(TextId.ALREADY_STARTED));
        } else {
            UserDataStorage.startNewChat(chatId);
            queue.add(new FixedQuestionGenerator(TextId.HELLO));
            queue.add(new FirstQuestionGenerator());
        }
        return queue;
    }
}
