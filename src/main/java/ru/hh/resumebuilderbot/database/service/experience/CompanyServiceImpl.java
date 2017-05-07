package ru.hh.resumebuilderbot.database.service.experience;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.experience.CompanyDAO;
import ru.hh.resumebuilderbot.database.model.experience.Company;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CompanyServiceImpl extends GenericServiceImpl<Company, Integer, CompanyDAO> implements CompanyService {
    @Inject
    public CompanyServiceImpl(CompanyDAO companyDAO, SessionFactory sessionFactory) {
        super(companyDAO, sessionFactory);
    }

    public Company getCompanyByHHId(Integer hhId){
        return inTransaction(() -> dao.getCompanyByHHId(hhId));
    }

    public Company getCompanyByName(String name){
        return inTransaction(() -> dao.getCompanyByName(name));
    }
}
