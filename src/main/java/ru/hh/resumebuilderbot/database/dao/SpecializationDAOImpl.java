package ru.hh.resumebuilderbot.database.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.Specialization;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

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
}
