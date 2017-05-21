package ru.hh.resumebuilderbot.database.dao.education;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.dao.experience.ExperienceDAOImpl;
import ru.hh.resumebuilderbot.database.model.education.Education;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

@Singleton
public class EducationDAOImpl extends GenericDAOImpl<Education, Integer> implements EducationDAO {
    private static final Logger log = LoggerFactory.getLogger(ExperienceDAOImpl.class);
    private static final String EDUCATION_BY_TELEGRAM_ID = (
            "SELECT e FROM Education e INNER JOIN User u WITH u.nodeRelationId = e.id WHERE telegram_id = :telegram_id"
    );

    @Inject
    public EducationDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Education getCurrentByTelegramId(Long telegramId) {
        TypedQuery<Education> query = getCurrentSession().createQuery(EDUCATION_BY_TELEGRAM_ID, Education.class);
        query.setParameter("telegram_id", telegramId);
        Education education = null;
        try {
            education = query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            log.info(e.getMessage());
        }
        return education;
    }
}
