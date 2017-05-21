package ru.hh.resumebuilderbot.database.service.education;

import ru.hh.resumebuilderbot.database.model.education.Education;
import ru.hh.resumebuilderbot.database.service.base.GenericService;

public interface EducationService extends GenericService<Education, Integer> {
    Education getCurrentByTelegramId(Long telegramId);

    void setCurrentEducationFaculty(Long telegramId, Integer facultyId, String facultyName);

    void setCurrentEducationSpeciality(Long telegramId, Integer specialityId, String specialityName);

    void setCurrentEducationYear(Long telegramId, Integer educationYear);

    void setCurrentEducationInstitute(Long telegramId, Integer instituteHHId, String instituteName);
}
