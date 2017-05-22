package ru.hh.resumebuilderbot.database.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.Area;

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
public class AreaDAOImpl extends GenericDAOImpl<Area, Integer> implements AreaDAO {
    private static final Logger log = LoggerFactory.getLogger(AreaDAOImpl.class);
    private static final String AREA_BY_HH_ID = "FROM Area WHERE hh_id = :hh_id";
    private static final String AREA_BY_NAME = "FROM Area WHERE name = :name";

    @Inject
    public AreaDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Area getAreaByHHId(Integer hhId) {
        TypedQuery<Area> query = getCurrentSession().createQuery(AREA_BY_HH_ID, Area.class);
        query.setParameter("hh_id", hhId);
        Area area = null;
        try {
            area = query.getSingleResult();
        } catch (NoResultException e) {
            log.info(e.getMessage());
        }
        return area;
    }

    @Override
    public Area getAreaByName(String name) {
        TypedQuery<Area> query = getCurrentSession().createQuery(AREA_BY_NAME, Area.class);
        query.setParameter("name", name);
        Area area = null;
        try {
            area = query.getSingleResult();
        } catch (NoResultException e) {
            log.info(e.getMessage());
        }
        return area;
    }

    @Override
    public Area getAreaByHHIdOrName(Integer hhId, String name) {
        if (hhId == null && name == null) {
            return null;
        }

        Session session = getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Area> criteriaQuery = criteriaBuilder.createQuery(Area.class);
        Root<Area> areas = criteriaQuery.from(Area.class);
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
