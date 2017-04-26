package ru.hh.resumebuilderbot.database.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.User;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

@Singleton
public class UserDAOImpl extends GenericDAOImpl<User, Integer> implements UserDAO {
    private final static Logger log = LoggerFactory.getLogger(UserDAOImpl.class);

    @Inject
    public UserDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    private final static String USER_BY_TELEGRAM_ID = "FROM TelegramUser WHERE telegram_id = :telegram_id";

    public User getByTelegramId(long telegramId){
        TypedQuery<User> query = getCurrentSession().createQuery(USER_BY_TELEGRAM_ID, User.class);
        query.setParameter("telegram_id", telegramId);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            log.info(e.getMessage());
        }
        return user;
    }
}
