package ru.hh.resumebuilderbot;

public interface BotBody {
    void askNextQuestions(User user, Answer answer);

    void setMessenger(MessengerAdapter messenger);
}
