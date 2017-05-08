package ru.hh.resumebuilderbot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.message.handler.MessageHandler;

@Singleton
public class BotBodyImpl implements BotBody {
    private final Selector selector;
    private MessengerAdapter messengerAdapter;

    @Inject
    public BotBodyImpl(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void askNextQuestions(Long telegramId, Answer answer) {
        MessageHandler messageHandler = selector.select(answer);
        messageHandler.handle(telegramId, answer).forEach((question) -> messengerAdapter.ask(telegramId, question));
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
}
