package ru.hh.resumebuilderbot.database.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.Specialization;

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
public class SpecializationDAOImpl extends GenericDAOImpl<Specialization, Integer> implements SpecializationDAO {
    private static final Logger log = LoggerFactory.getLogger(SpecializationDAOImpl.class);
    private static final String SPECIALIZATION_BY_HH_ID = "FROM Specialization WHERE hh_id = :hh_id";
    private static final String SPECIALIZATION_BY_NAME = "FROM Specialization WHERE name = :name";

    @Inject
    public SpecializationDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Specialization getSpecializationByName(String name) {
        TypedQuery<Specialization> query = getCurrentSession()
                .createQuery(SPECIALIZATION_BY_NAME, Specialization.class);
        query.setParameter("name", name);
        Specialization specialization = null;
        try {
            specialization = query.getSingleResult();
        } catch (NoResultException e) {
            log.info(e.getMessage());
        }
        return specialization;
    }

    @Override
    public Specialization getSpecializationByHHId(String hhId) {
        TypedQuery<Specialization> query = getCurrentSession()
                .createQuery(SPECIALIZATION_BY_HH_ID, Specialization.class);
        query.setParameter("hh_id", hhId);
        Specialization specialization = null;
        try {
            specialization = query.getSingleResult();
        } catch (NoResultException e) {
            log.info(e.getMessage());
        }
        return specialization;
    }

    @Override
    public Specialization getSpecializationByHHIdOrName(String hhId, String name) {
        if (hhId == null && name == null) {
            return null;
        }

        Session session = getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Specialization> criteriaQuery = criteriaBuilder.createQuery(Specialization.class);
        Root<Specialization> areas = criteriaQuery.from(Specialization.class);
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
