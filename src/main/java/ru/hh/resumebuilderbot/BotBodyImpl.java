package ru.hh.resumebuilderbot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.message.handler.MessageHandler;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

@Singleton
public class BotBodyImpl implements BotBody {
    private final UserDataStorage userDataStorage;
    private final Selector selector;
    private MessengerAdapter messengerAdapter;

    @Inject
    public BotBodyImpl(UserDataStorage userDataStorage, Selector selector) {
        this.userDataStorage = userDataStorage;
        this.selector = selector;
    }

    @Override
    public void askNextQuestions(TelegramUser telegramUser, Answer answer) {
        MessageHandler messageHandler = selector.select(answer);
        messageHandler.handle(telegramUser, answer).forEach((question) -> messengerAdapter.ask(telegramUser, question));
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
}
