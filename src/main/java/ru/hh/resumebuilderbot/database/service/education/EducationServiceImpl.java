package ru.hh.resumebuilderbot.database.service.education;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.education.EducationDAO;
import ru.hh.resumebuilderbot.database.model.education.Education;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EducationServiceImpl extends GenericServiceImpl<Education, Integer, EducationDAO>
        implements EducationService {
    @Inject
    public EducationServiceImpl(EducationDAO educationDAO, SessionFactory sessionFactory) {
        super(educationDAO, sessionFactory);
    }

    @Override
    public Education getCurrentByTelegramId(Long telegramId) {
        return inTransaction(() -> dao.getCurrentByTelegramId(telegramId));
    }

    @Override
    public void setCurrentEducationFaculty(Long telegramId, Integer facultyId, String facultyName) {
        inTransaction(() -> {
            Education education = getCurrentByTelegramId(telegramId);
            education.setFacultyId(facultyId);
            education.setFacultyName(facultyName);
            update(education);
        });
    }

    @Override
    public void setCurrentEducationSpeciality(Long telegramId, Integer specialityId, String specialityName) {
        inTransaction(() -> {
            Education education = getCurrentByTelegramId(telegramId);
            education.setSpecialityId(specialityId);
            education.setSpecialityName(specialityName);
            update(education);
        });
    }

    @Override
    public void setCurrentEducationYear(Long telegramId, Integer educationYear) {
        inTransaction(() -> {
            Education education = getCurrentByTelegramId(telegramId);
            education.setYear(educationYear);
            update(education);
        });
    }

    @Override
    public void setCurrentEducationInstitute(Long telegramId, Integer instituteHHId, String instituteName) {
        inTransaction(() -> {
            Education education = getCurrentByTelegramId(telegramId);
            education.setInstitutionId(instituteHHId);
            education.setInstitutionName(instituteName);
            update(education);
        });
    }
}
