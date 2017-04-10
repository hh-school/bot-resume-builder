package ru.hh.resumebuilderbot;

public interface MessengerAdapter {
    void ask(Question question);

    void setBotBody(BotBody botBody);

    void start();
}
