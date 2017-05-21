package ru.hh.resumebuilderbot.database.service.experience;

import ru.hh.resumebuilderbot.database.model.experience.Experience;
import ru.hh.resumebuilderbot.database.service.base.GenericService;

import java.util.Date;

public interface ExperienceService extends GenericService<Experience, Integer> {
    void setCurrentExperienceStartDate(Long telegramId, Date startDate);

    void setCurrentExperienceEndDate(Long telegramId, Date endDate);

    void setCurrentExperiencePosition(Long telegramId, String position);

    void setCurrentExperienceDescription(Long telegramId, String description);

    void saveCurrentExperienceCompany(Long telegramId, String companyName, Integer companyHHId);
}
