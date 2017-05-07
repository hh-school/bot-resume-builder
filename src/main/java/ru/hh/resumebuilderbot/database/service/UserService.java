package ru.hh.resumebuilderbot.database.service;

import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.database.service.base.GenericService;

public interface UserService extends GenericService<User, Integer> {
    User getUserByTelegramId(long telegramId);
}
