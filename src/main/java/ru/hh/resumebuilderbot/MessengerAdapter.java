package ru.hh.resumebuilderbot;

interface MessengerAdapter {
    void ask(Question question);

    public void setHandler(AbstractBotBody handler);

    void start();
}
