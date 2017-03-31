package ru.hh.resumebuilderbot;

public interface AbstractBotBody {
    void answer(Answer answer, int timeoutMs);

    void setMessenger(MessengerAdapter messenger);
}
