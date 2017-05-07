package ru.hh.resumebuilderbot.database.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.Area;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

@Singleton
public class AreaDAOImpl extends GenericDAOImpl<Area, Integer> implements AreaDAO {

    private static final Logger log = LoggerFactory.getLogger(AreaDAOImpl.class);

    @Inject
    public AreaDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    private static final String AREA_BY_HH_ID = "FROM Area WHERE hh_id = :hh_id";

    private static final String AREA_BY_NAME = "FROM Area WHERE name = :name";

    public Area getAreaByHHId(Integer hhId){
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

    public Area getAreaByName(String name){
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
}
