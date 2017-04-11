package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.question.Question;

public interface MessengerAdapter {
    void ask(User user, Question question);

    void setBotBody(BotBody botBody);

    void start();
}
