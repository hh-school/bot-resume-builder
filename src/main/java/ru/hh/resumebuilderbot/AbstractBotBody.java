package ru.hh.resumebuilderbot;

interface AbstractBotBody {

    void answer(Answer answer, int timeoutMs);

    void setMessenger(MessengerAdapter messenger);
}
