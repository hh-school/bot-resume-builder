package ru.hh.resumebuilderbot.database.service.education;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.education.EducationDAO;
import ru.hh.resumebuilderbot.database.model.education.Education;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EducationServiceImpl extends GenericServiceImpl<Education, Integer>
        implements EducationService {
    @Inject
    public EducationServiceImpl(EducationDAO educationDAO, SessionFactory sessionFactory) {
        super(educationDAO, sessionFactory);
    }
}
