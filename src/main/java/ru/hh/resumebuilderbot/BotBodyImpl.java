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
    public void askNextQuestions(User user, Answer answer) {
        synchronized (userDataStorage.getMutex(user)) {
            MessageHandler messageHandler = selector.select(answer);
            messageHandler.handle(user, answer).forEach((question) -> messengerAdapter.ask(user, question));
        }
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
}
