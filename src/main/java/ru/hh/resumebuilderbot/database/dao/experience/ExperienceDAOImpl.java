package ru.hh.resumebuilderbot.database.dao.experience;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.experience.Experience;

import javax.validation.constraints.NotNull;

@Singleton
public class ExperienceDAOImpl extends GenericDAOImpl<Experience, Integer> implements ExperienceDAO {
    @Inject
    public ExperienceDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
