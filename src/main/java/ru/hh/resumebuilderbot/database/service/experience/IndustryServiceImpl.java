package ru.hh.resumebuilderbot.database.service.experience;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.experience.IndustryDAO;
import ru.hh.resumebuilderbot.database.model.experience.Industry;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IndustryServiceImpl extends GenericServiceImpl<Industry, Integer, IndustryDAO> implements IndustryService {
    @Inject
    public IndustryServiceImpl(IndustryDAO industryDAO, SessionFactory sessionFactory) {
        super(industryDAO, sessionFactory);
    }
}
