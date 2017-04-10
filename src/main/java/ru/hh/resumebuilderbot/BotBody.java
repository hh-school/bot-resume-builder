package ru.hh.resumebuilderbot;

public interface BotBody {
    void answer(Answer answer, int timeoutMs);

    void setMessenger(MessengerAdapter messenger);
}
