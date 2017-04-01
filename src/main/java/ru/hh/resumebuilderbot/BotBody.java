package ru.hh.resumebuilderbot;

public interface BotBody {
    void answer(Answer answer);

    void setMessenger(MessengerAdapter messenger);
}
