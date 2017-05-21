package ru.hh.resumebuilderbot.database.dao.experience;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.experience.Experience;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

@Singleton
public class ExperienceDAOImpl extends GenericDAOImpl<Experience, Integer> implements ExperienceDAO {
    private static final Logger log = LoggerFactory.getLogger(ExperienceDAOImpl.class);
    private static final String EXPERIENCE_BY_TELEGRAM_ID = (
            "SELECT e FROM Experience e INNER JOIN User u WITH u.nodeRelationId = e.id WHERE telegram_id = :telegram_id"
    );

    @Inject
    public ExperienceDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Experience getCurrentByTelegramId(Long telegramId) {
        TypedQuery<Experience> query = getCurrentSession().createQuery(EXPERIENCE_BY_TELEGRAM_ID, Experience.class);
        query.setParameter("telegram_id", telegramId);
        Experience experience = null;
        try {
            experience = query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            log.info(e.getMessage());
        }
        return experience;
    }
}
