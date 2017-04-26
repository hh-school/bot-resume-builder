package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.question.Question;

public interface MessengerAdapter {
    void ask(TelegramUser telegramUser, Question question);

    void start();
}
