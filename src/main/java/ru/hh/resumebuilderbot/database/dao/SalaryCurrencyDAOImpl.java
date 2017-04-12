package ru.hh.resumebuilderbot.database.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.SalaryCurrency;

import javax.validation.constraints.NotNull;

@Singleton
public class SalaryCurrencyDAOImpl extends GenericDAOImpl<SalaryCurrency, Integer> implements SalaryCurrencyDAO {
    @Inject
    public SalaryCurrencyDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
