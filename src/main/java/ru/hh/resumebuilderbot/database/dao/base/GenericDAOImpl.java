package ru.hh.resumebuilderbot.database.dao.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericDAOImpl<T, PK extends Serializable> implements GenericDAO<T, PK> {
    protected final SessionFactory sessionFactory;
    @SuppressWarnings("unchecked")
    private final Class<T> type = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0]);

    public GenericDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(T entity) {
        getCurrentSession().save(entity);
    }

    public T get(PK id) {
        return getCurrentSession().get(type, id);
    }

    public List<T> getAll() {
        Session session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(type);
        return session.createQuery(criteriaQuery.select(criteriaQuery.from(type))).getResultList();
    }

    public void update(T entity) {
        getCurrentSession().update(entity);
    }

    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
