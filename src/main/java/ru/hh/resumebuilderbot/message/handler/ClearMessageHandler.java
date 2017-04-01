package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.question.generator.FirstQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGeneratorsQueue;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

public class ClearMessageHandler extends MessageHandler {
    @Override
    public QuestionGeneratorsQueue handle(ChatId chatId, Answer answer) {
        UserDataStorage.clear(chatId);
        questionGeneratorsQueue.add(new FixedQuestionGenerator(TextId.CLEARED));
        questionGeneratorsQueue.add(new FirstQuestionGenerator());
        return questionGeneratorsQueue;
    }
}
