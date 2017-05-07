package ru.hh.resumebuilderbot.cv.builder;

import ru.hh.resumebuilderbot.TelegramUser;

public interface CVBuilder {
    String build(TelegramUser telegramUser);
}
