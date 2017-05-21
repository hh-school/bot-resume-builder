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

    @Override
    public Company getCompanyByHHId(Integer hhId) {
        return inTransaction(() -> dao.getCompanyByHHId(hhId));
    }

    @Override
    public Company getCompanyByName(String name) {
        return inTransaction(() -> dao.getCompanyByName(name));
    }

    @Override
    public Company getOrCreateCompany(String companyName, Integer companyHHId) {
        return inTransaction(() -> {
            Company company;
            if (companyHHId != null) {
                company = getCompanyByHHId(companyHHId);
            } else {
                company = getCompanyByName(companyName);
            }
            if (company == null) {
                company = new Company(companyName, companyHHId);
                create(company);
            }
            return company;
        });
    }
}
