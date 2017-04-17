package ru.hh.resumebuilderbot.database.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.Area;

import javax.validation.constraints.NotNull;

@Singleton
public class AreaDAOImpl extends GenericDAOImpl<Area, Integer> implements AreaDAO {
    @Inject
    public AreaDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
