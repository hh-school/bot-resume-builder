package ru.hh.resumebuilderbot;

public interface BotBody {
    void askNextQuestions(Long telegramId, Answer answer);

    void setMessenger(MessengerAdapter messenger);

    void provideSuggests(Long telegramId, String queryText, String queryId);

    void performNegotiation(Long telegramId, Integer messageId, String callbackData);

    void saveChosenSuggest(Long telegramId, Integer resultId, String queryText);

    void updateMessage(Long telegramId, Integer messageId, String callbackData);
}
