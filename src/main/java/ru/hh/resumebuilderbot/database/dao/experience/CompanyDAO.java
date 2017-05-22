package ru.hh.resumebuilderbot.database.dao.experience;

import ru.hh.resumebuilderbot.database.dao.base.GenericDAO;
import ru.hh.resumebuilderbot.database.model.experience.Company;

public interface CompanyDAO extends GenericDAO<Company, Integer> {
    Company getCompanyByHHId(Integer hhId);

    Company getCompanyByName(String name);

    Company getCompanyByHHIdOrName(Integer hhId, String name);
}
