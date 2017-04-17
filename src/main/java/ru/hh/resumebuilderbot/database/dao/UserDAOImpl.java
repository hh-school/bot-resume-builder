package ru.hh.resumebuilderbot.database.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;

import javax.validation.constraints.NotNull;

@Singleton
public class UserDAOImpl extends GenericDAOImpl<User, Integer> implements UserDAO {
    @Inject
    public UserDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
