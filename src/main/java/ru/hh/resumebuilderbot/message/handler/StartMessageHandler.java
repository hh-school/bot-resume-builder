package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.question.generator.FirstQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

public class StartMessageHandler extends MessageHandler {
    @Override
    public QuestionGeneratorsQueue handle(Answer answer) {
        ChatId chatId = answer.getChatId();
        if (UserDataStorage.contains(chatId)) {
            questionGeneratorsQueue.add(new FixedQuestionGenerator(TextId.ALREADY_STARTED));
        } else {
            UserDataStorage.startNewChat(chatId);
            questionGeneratorsQueue.add(new FixedQuestionGenerator(TextId.HELLO));
            questionGeneratorsQueue.add(new FirstQuestionGenerator());
        }
        return questionGeneratorsQueue;
    }
}
