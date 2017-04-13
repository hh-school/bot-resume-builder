package ru.hh.resumebuilderbot.database.service.base;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAO;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class GenericServiceImpl<T, PK extends Serializable> implements GenericService<T, PK> {
    protected SessionFactory sessionFactory;
    private GenericDAO<T, PK> genericDAO;

    public GenericServiceImpl(GenericDAO<T, PK> genericDAO, SessionFactory sessionFactory) {
        this.genericDAO = genericDAO;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(T entity) {
        inTransaction(() -> genericDAO.create(entity));
    }

    @Override
    public T get(PK id) {
        return inTransaction(() -> genericDAO.get(id));
    }

    @Override
    public List<T> getAll() {
        return inTransaction(() -> genericDAO.getAll());
    }

    @Override
    public void update(T entity) {
        inTransaction(() -> genericDAO.update(entity));
    }

    @Override
    public void delete(T entity) {
        inTransaction(() -> genericDAO.delete(entity));
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
        Transaction transaction = sessionFactory.getCurrentSession().getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
            return Optional.of(transaction);
        }
        return Optional.empty();
    }
}
