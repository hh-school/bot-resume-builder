package ru.hh.resumebuilderbot.database.dao.experience;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.experience.Company;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class CompanyDAOImpl extends GenericDAOImpl<Company, Integer> implements CompanyDAO {
    private static final Logger log = LoggerFactory.getLogger(CompanyDAOImpl.class);
    private static final String COMPANY_BY_HH_ID = "FROM Company WHERE hh_id = :hh_id";
    private static final String COMPANY_BY_NAME = "FROM Company WHERE name = :name";

    @Inject
    public CompanyDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Company getCompanyByHHId(Integer hhId) {
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

    @Override
    public Company getCompanyByName(String name) {
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

    @Override
    public Company getCompanyByHHIdOrName(Integer hhId, String name) {
        if (hhId == null && name == null) {
            return null;
        }

        Session session = getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> areas = criteriaQuery.from(Company.class);
        List<Predicate> predicates = new ArrayList<>(2);
        if (hhId != null) {
            predicates.add(criteriaBuilder.equal(areas.get("hhId"), hhId));
        }
        if (name != null) {
            predicates.add(criteriaBuilder.equal(areas.get("name"), name));
        }
        criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));

        try {
            return session.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            log.info(e.getMessage());
            return null;
        }
    }
}
