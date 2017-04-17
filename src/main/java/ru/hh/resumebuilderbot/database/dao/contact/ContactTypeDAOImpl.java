package ru.hh.resumebuilderbot.database.dao.contact;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.contact.ContactType;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

@Singleton
public class ContactTypeDAOImpl extends GenericDAOImpl<ContactType, Integer> implements ContactTypeDAO {
    @Inject
    public ContactTypeDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
