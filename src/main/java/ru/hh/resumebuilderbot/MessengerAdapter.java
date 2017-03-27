package ru.hh.resumebuilderbot;

public interface MessengerAdapter {
    void ask(Question question);

    public void setHandler(AbstractBotBody handler);

    void start();
}
