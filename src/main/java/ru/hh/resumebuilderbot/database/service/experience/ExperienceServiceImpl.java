package ru.hh.resumebuilderbot.database.service.experience;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.experience.ExperienceDAO;
import ru.hh.resumebuilderbot.database.model.experience.Experience;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExperienceServiceImpl extends GenericServiceImpl<Experience, Integer> implements ExperienceService {
    @Inject
    public ExperienceServiceImpl(ExperienceDAO experienceDAO, SessionFactory sessionFactory) {
        super(experienceDAO, sessionFactory);
    }
}
