package ru.hh.resumebuilderbot;

public interface BotBody {
    void answer(User user, Answer answer);

    void setMessenger(MessengerAdapter messenger);
}
