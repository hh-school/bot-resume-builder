package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.question.Question;

public interface MessengerAdapter {
    void ask(Long telegramId, Question question);

    void start();
}
