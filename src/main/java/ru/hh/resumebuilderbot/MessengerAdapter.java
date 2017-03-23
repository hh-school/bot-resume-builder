package ru.hh.resumebuilderbot;

interface MessengerAdapter {
    void ask(Question question);

    void setHandler(AbstractBotBody handler);

    void start();
}
