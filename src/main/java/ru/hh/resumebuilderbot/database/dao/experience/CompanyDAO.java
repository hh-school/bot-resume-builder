package ru.hh.resumebuilderbot.database.dao.experience;

import ru.hh.resumebuilderbot.database.dao.base.GenericDAO;
import ru.hh.resumebuilderbot.database.model.experience.Company;

public interface CompanyDAO extends GenericDAO<Company, Integer> {
    public Company getCompanyByHHId(Integer hhId);

    public Company getCompanyByName(String name);
}
