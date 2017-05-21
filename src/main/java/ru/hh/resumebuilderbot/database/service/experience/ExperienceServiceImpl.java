package ru.hh.resumebuilderbot.database.service.experience;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.experience.ExperienceDAO;
import ru.hh.resumebuilderbot.database.model.experience.Company;
import ru.hh.resumebuilderbot.database.model.experience.Experience;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

@Singleton
public class ExperienceServiceImpl extends GenericServiceImpl<Experience, Integer, ExperienceDAO>
        implements ExperienceService {
    private final CompanyService companyService;

    @Inject
    public ExperienceServiceImpl(ExperienceDAO experienceDAO, SessionFactory sessionFactory,
                                 CompanyService companyService) {
        super(experienceDAO, sessionFactory);
        this.companyService = companyService;
    }

    private Experience getCurrentByTelegramId(Long telegramId) {
        return dao.getCurrentByTelegramId(telegramId);
    }

    @Override
    public void setCurrentExperienceStartDate(Long telegramId, Date startDate) {
        inTransaction(() -> {
            Experience experience = getCurrentByTelegramId(telegramId);
            experience.setStartDate(startDate);
            update(experience);
        });
    }

    @Override
    public void setCurrentExperienceEndDate(Long telegramId, Date endDate) {
        inTransaction(() -> {
            Experience experience = getCurrentByTelegramId(telegramId);
            experience.setEndDate(endDate);
            update(experience);
        });
    }

    @Override
    public void setCurrentExperiencePosition(Long telegramId, String position) {
        inTransaction(() -> {
            Experience experience = getCurrentByTelegramId(telegramId);
            experience.setPosition(position);
            update(experience);
        });
    }

    @Override
    public void setCurrentExperienceDescription(Long telegramId, String description) {
        inTransaction(() -> {
            Experience experience = getCurrentByTelegramId(telegramId);
            experience.setDescription(description);
            update(experience);
        });
    }

    @Override
    public void saveCurrentExperienceCompany(Long telegramId, String companyName, Integer companyHHId) {
        inTransaction(() -> {
            Experience experience = getCurrentByTelegramId(telegramId);
            Company company = companyService.getOrCreateCompany(companyName, companyHHId);
            experience.setCompany(company);
            update(experience);
        });
    }
}
