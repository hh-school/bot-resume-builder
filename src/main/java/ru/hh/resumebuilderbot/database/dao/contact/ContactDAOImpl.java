package ru.hh.resumebuilderbot.database.dao.contact;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.contact.Contact;

import javax.validation.constraints.NotNull;

@Singleton
public class ContactDAOImpl extends GenericDAOImpl<Contact, Integer> implements ContactDAO {
    @Inject
    public ContactDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
