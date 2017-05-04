package ru.hh.resumebuilderbot.database.service.experience;

import ru.hh.resumebuilderbot.database.model.experience.Company;
import ru.hh.resumebuilderbot.database.service.base.GenericService;

public interface CompanyService extends GenericService<Company, Integer> {
    public Company getCompanyByHHId(Integer hhId);

    public Company getCompanyByName(String name);
}
