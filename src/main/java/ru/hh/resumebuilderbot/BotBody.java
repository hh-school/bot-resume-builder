package ru.hh.resumebuilderbot;

public interface BotBody {
    void askNextQuestions(TelegramUser telegramUser, Answer answer);

    void setMessenger(MessengerAdapter messenger);
}
