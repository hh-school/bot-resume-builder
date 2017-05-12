package ru.hh.resumebuilderbot;

public interface BotBody {
    void askNextQuestions(Long telegramId, Answer answer);

    void setMessenger(MessengerAdapter messenger);

    void provideSuggests(Long telegramId, String queryText, String queryId);
}
