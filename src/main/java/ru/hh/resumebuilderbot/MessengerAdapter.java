package ru.hh.resumebuilderbot;

public interface MessengerAdapter {
    void ask(Question question);

    void setHandler(AbstractBotBody handler);

    void start();
}
