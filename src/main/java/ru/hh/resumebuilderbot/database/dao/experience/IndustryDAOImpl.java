package ru.hh.resumebuilderbot.database.dao.experience;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.experience.Industry;

import javax.validation.constraints.NotNull;

@Singleton
public class IndustryDAOImpl extends GenericDAOImpl<Industry, Integer> implements IndustryDAO {
    @Inject
    public IndustryDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
