package ru.hh.resumebuilderbot.database.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.Specialization;

import javax.validation.constraints.NotNull;

@Singleton
public class SpecializationDAOImpl extends GenericDAOImpl<Specialization, Integer> implements SpecializationDAO {
    @Inject
    public SpecializationDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
