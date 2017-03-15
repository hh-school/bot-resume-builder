package ru.hh.resumebuilderbot;

interface AbstractBotBody {
    void onAnswer(Answer answer, int timeoutMs);

    void onStartChat(ChatId chatId);
}
