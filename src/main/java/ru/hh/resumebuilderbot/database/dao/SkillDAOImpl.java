package ru.hh.resumebuilderbot.database.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.Skill;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class SkillDAOImpl extends GenericDAOImpl<Skill, Integer> implements SkillDAO {
    private static final Logger log = LoggerFactory.getLogger(SkillDAOImpl.class);
    private static final String SKILL_BY_HH_ID = "FROM Skill WHERE hh_id = :hh_id";
    private static final String SKILL_BY_NAME = "FROM Skill WHERE name = :name";

    @Inject
    public SkillDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Skill getSkillByHHId(Integer hhId) {
        TypedQuery<Skill> query = getCurrentSession().createQuery(SKILL_BY_HH_ID, Skill.class);
        query.setParameter("hh_id", hhId);
        Skill skill = null;
        try {
            skill = query.getSingleResult();
        } catch (NoResultException e) {
            log.info(e.getMessage());
        }
        return skill;
    }

    @Override
    public Skill getSkillByName(String name) {
        TypedQuery<Skill> query = getCurrentSession().createQuery(SKILL_BY_NAME, Skill.class);
        query.setParameter("name", name);
        Skill skill = null;
        try {
            skill = query.getSingleResult();
        } catch (NoResultException e) {
            log.info(e.getMessage());
        }
        return skill;
    }

    @Override
    public Skill getSkillByHHIdOrName(Integer hhId, String name) {
        if (hhId == null && name == null) {
            return null;
        }

        Session session = getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Skill> criteriaQuery = criteriaBuilder.createQuery(Skill.class);
        Root<Skill> areas = criteriaQuery.from(Skill.class);
        List<Predicate> predicates = new ArrayList<>(2);
        if (hhId != null) {
            predicates.add(criteriaBuilder.equal(areas.get("hhId"), hhId));
        }
        if (name != null) {
            predicates.add(criteriaBuilder.equal(areas.get("name"), name));
        }
        criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));

        try {
            return session.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            log.info(e.getMessage());
            return null;
        }
    }
}
