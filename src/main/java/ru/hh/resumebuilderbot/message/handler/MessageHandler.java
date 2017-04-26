package ru.hh.resumebuilderbot.message.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.TelegramUser;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

import java.util.List;

public abstract class MessageHandler {
    protected final static Logger log = LoggerFactory.getLogger(MessageHandler.class);
    protected UserDataStorage userDataStorage;

    protected MessageHandler(UserDataStorage userDataStorage) {
        // TODO not all handlers require userDataStorage
        this.userDataStorage = userDataStorage;
    }

    public abstract List<Question> handle(TelegramUser telegramUser, Answer answer);
}
