package ru.hh.resumebuilderbot.database.dao.experience;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.experience.Company;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

@Singleton
public class CompanyDAOImpl extends GenericDAOImpl<Company, Integer> implements CompanyDAO {
    private static final Logger log = LoggerFactory.getLogger(CompanyDAOImpl.class);

    @Inject
    public CompanyDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    private static final String COMPANY_BY_HH_ID = "FROM Company WHERE hh_id = :hh_id";

    private static final String COMPANY_BY_NAME = "FROM Company WHERE name = :name";

    public Company getCompanyByHHId(Integer hhId){
        TypedQuery<Company> query = getCurrentSession().createQuery(COMPANY_BY_HH_ID, Company.class);
        query.setParameter("hh_id", hhId);
        Company company = null;
        try {
            company = query.getSingleResult();
        } catch (NoResultException e) {
            log.info(e.getMessage());
        }
        return company;
    }

    public Company getCompanyByName(String name){
        TypedQuery<Company> query = getCurrentSession().createQuery(COMPANY_BY_NAME, Company.class);
        query.setParameter("name", name);
        Company company = null;
        try {
            company = query.getSingleResult();
        } catch (NoResultException e) {
            log.info(e.getMessage());
        }
        return company;
    }
}
