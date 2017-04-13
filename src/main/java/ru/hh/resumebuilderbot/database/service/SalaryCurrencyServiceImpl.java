package ru.hh.resumebuilderbot.database.service;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.SalaryCurrencyDAO;
import ru.hh.resumebuilderbot.database.model.SalaryCurrency;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SalaryCurrencyServiceImpl extends GenericServiceImpl<SalaryCurrency, Integer>
        implements SalaryCurrencyService {
    @Inject
    public SalaryCurrencyServiceImpl(SalaryCurrencyDAO salaryCurrencyDAO, SessionFactory sessionFactory) {
        super(salaryCurrencyDAO, sessionFactory);
    }
}
