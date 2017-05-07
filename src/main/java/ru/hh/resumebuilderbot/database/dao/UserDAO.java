package ru.hh.resumebuilderbot.database.dao;

import ru.hh.resumebuilderbot.database.dao.base.GenericDAO;
import ru.hh.resumebuilderbot.database.model.User;

public interface UserDAO extends GenericDAO<User, Integer> {
    User getByTelegramId(long telegramId);
}
