package ru.hh.resumebuilderbot.database.service;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.SpecializationDAO;
import ru.hh.resumebuilderbot.database.model.Specialization;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SpecializationServiceImpl extends GenericServiceImpl<Specialization, Integer>
        implements SpecializationService {
    @Inject
    public SpecializationServiceImpl(SpecializationDAO specializationDAO, SessionFactory sessionFactory) {
        super(specializationDAO, sessionFactory);
    }
}