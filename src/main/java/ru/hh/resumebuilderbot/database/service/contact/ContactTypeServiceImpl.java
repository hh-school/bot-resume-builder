package ru.hh.resumebuilderbot.database.service.contact;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.contact.ContactTypeDAO;
import ru.hh.resumebuilderbot.database.model.contact.ContactType;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ContactTypeServiceImpl extends GenericServiceImpl<ContactType, Integer, ContactTypeDAO>
        implements ContactTypeService {
    @Inject
    public ContactTypeServiceImpl(ContactTypeDAO contactTypeDAO, SessionFactory sessionFactory) {
        super(contactTypeDAO, sessionFactory);
    }
}
