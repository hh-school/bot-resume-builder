package ru.hh.resumebuilderbot.database.service.base;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T, PK extends Serializable> {
    void create(T entity);

    T get(PK id);

    List<T> getAll();

    void update(T entity);

    void delete(T entity);
}
