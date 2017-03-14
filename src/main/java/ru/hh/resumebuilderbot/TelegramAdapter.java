package ru.hh.resumebuilderbot;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

// Заглушка для адаптера с серверу Telegram
public class TelegramAdapter implements MessengerAdapter {
    @Override
    public void ask(Question question, int timeoutMs) {
        throw new NotImplementedException();
    }

    @Override
    public void setHandler(AbstractBotBody bot) {
        throw new NotImplementedException();
    }

    @Override
    public void start() {
        throw new NotImplementedException();
    }
}
