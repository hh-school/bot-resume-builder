package ru.hh.resumebuilderbot;

interface MessengerAdapter {
    void ask(Question question, int timeoutMs);

    void setHandler(AbstractBotBody handler);

    void start();
}
