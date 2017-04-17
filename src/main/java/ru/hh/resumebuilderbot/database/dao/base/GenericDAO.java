package ru.hh.resumebuilderbot.database.dao.base;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {
    void create(T entity);

    T get(PK id);

    List<T> getAll();

    void update(T entity);

    void delete(T entity);
}
