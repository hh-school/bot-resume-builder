package ru.hh.resumebuilderbot.database.dao.education;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.education.Education;

import javax.validation.constraints.NotNull;

@Singleton
public class EducationDAOImpl extends GenericDAOImpl<Education, Integer> implements EducationDAO {
    @Inject
    public EducationDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
