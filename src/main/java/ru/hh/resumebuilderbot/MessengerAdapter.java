package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.question.Question;

public interface MessengerAdapter {
    void ask(ChatId chatId, Question question);

    void setBotBody(BotBody botBody);

    void start();
}
