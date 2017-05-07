package ru.hh.resumebuilderbot.database.service;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.UserDAO;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserServiceImpl extends GenericServiceImpl<User, Integer, UserDAO> implements UserService {
    @Inject
    public UserServiceImpl(UserDAO userDAO, SessionFactory sessionFactory) {
        super(userDAO, sessionFactory);
    }

    public User getUserByTelegramId(long telegramId) {
        return inTransaction(() -> dao.getByTelegramId(telegramId));
    }
}
