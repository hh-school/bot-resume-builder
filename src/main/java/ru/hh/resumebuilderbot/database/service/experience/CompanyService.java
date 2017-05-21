package ru.hh.resumebuilderbot.database.service.experience;

import ru.hh.resumebuilderbot.database.model.experience.Company;
import ru.hh.resumebuilderbot.database.service.base.GenericService;

public interface CompanyService extends GenericService<Company, Integer> {
    Company getCompanyByHHId(Integer hhId);

    Company getCompanyByName(String name);

    Company getOrCreateCompany(String companyName, Integer companyHHId);
}
