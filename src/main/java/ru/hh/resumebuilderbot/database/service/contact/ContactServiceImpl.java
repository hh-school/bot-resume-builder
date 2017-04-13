package ru.hh.resumebuilderbot.database.service.contact;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.contact.ContactDAO;
import ru.hh.resumebuilderbot.database.model.contact.Contact;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ContactServiceImpl extends GenericServiceImpl<Contact, Integer> implements ContactService {
    @Inject
    public ContactServiceImpl(ContactDAO contactDAO, SessionFactory sessionFactory) {
        super(contactDAO, sessionFactory);
    }
}
