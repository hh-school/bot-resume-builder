package ru.hh.resumebuilderbot;

public interface MessengerAdapter {
    void ask(ChatId chatId, Question question);

    void setBotBody(BotBody botBody);

    void start();
}
