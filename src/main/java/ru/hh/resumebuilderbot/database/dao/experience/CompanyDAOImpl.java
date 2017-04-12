package ru.hh.resumebuilderbot.database.dao.experience;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.experience.Company;

import javax.validation.constraints.NotNull;

@Singleton
public class CompanyDAOImpl extends GenericDAOImpl<Company, Integer> implements CompanyDAO {
    @Inject
    public CompanyDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
