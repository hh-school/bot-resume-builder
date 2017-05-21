package ru.hh.resumebuilderbot.database.service.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAO;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class GenericServiceImpl<T, PK extends Serializable, DAOT extends GenericDAO<T, PK>>
        implements GenericService<T, PK> {
    protected final SessionFactory sessionFactory;
    protected final DAOT dao;

    public GenericServiceImpl(DAOT dao, SessionFactory sessionFactory) {
        this.dao = dao;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(T entity) {
        inTransaction(() -> dao.create(entity));
    }

    @Override
    public T get(PK id) {
        return inTransaction(() -> dao.get(id));
    }

    @Override
    public List<T> getAll() {
        return inTransaction(dao::getAll);
    }

    @Override
    public void update(T entity) {
        inTransaction(() -> dao.update(entity));
    }

    @Override
    public void delete(T entity) {
        inTransaction(() -> dao.delete(entity));
    }

    protected <K> K inTransaction(Supplier<K> supplier) {
        Optional<Transaction> transaction = beginTransaction();
        try {
            K result = supplier.get();
            transaction.ifPresent(Transaction::commit);
            return result;
        } catch (RuntimeException e) {
            transaction.ifPresent(Transaction::rollback);
            throw e;
        }
    }

    protected void inTransaction(Runnable runnable) {
        inTransaction(() -> {
            runnable.run();
            return null;
        });
    }

    protected Optional<Transaction> beginTransaction() {
        Transaction transaction = getCurrentSession().getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
            return Optional.of(transaction);
        }
        return Optional.empty();
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
