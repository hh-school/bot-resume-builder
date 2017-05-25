package ru.hh.resumebuilderbot.cv.builder;

import ru.hh.resumebuilderbot.database.model.User;

public interface CVBuilder<T> {
    T build(User telegramId);
}
