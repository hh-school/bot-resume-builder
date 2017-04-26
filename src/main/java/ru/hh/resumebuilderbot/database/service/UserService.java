package ru.hh.resumebuilderbot.database.service;

import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.database.service.base.GenericService;

public interface UserService extends GenericService<User, Integer> {
    public User getUserByTelegramId(Integer telegram_id);
}
