package ru.hh.resumebuilderbot.database.service;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.UserDAO;
import ru.hh.resumebuilderbot.database.model.Area;
import ru.hh.resumebuilderbot.database.model.SalaryCurrency;
import ru.hh.resumebuilderbot.database.model.Skill;
import ru.hh.resumebuilderbot.database.model.Specialization;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.database.model.education.Education;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.database.model.experience.Experience;
import ru.hh.resumebuilderbot.database.model.gender.Gender;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;
import ru.hh.resumebuilderbot.database.service.education.EducationService;
import ru.hh.resumebuilderbot.database.service.experience.ExperienceService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

@Singleton
public class UserServiceImpl extends GenericServiceImpl<User, Integer, UserDAO> implements UserService {
    private final AreaService areaService;
    private final SkillService skillService;
    private final SpecializationService specializationService;
    private final EducationService educationService;
    private final ExperienceService experienceService;

    @Inject
    public UserServiceImpl(UserDAO userDAO, SessionFactory sessionFactory, AreaService areaService,
                           SkillService skillService, SpecializationService specializationService,
                           EducationService educationService, ExperienceService experienceService) {
        super(userDAO, sessionFactory);
        this.areaService = areaService;
        this.skillService = skillService;
        this.specializationService = specializationService;
        this.educationService = educationService;
        this.experienceService = experienceService;
    }

    @Override
    public User getUserByTelegramId(long telegramId) {
        return inTransaction(() -> dao.getByTelegramId(telegramId));
    }

    @Override
    public void saveUserArea(Long telegramId, String areaName, Integer areaHhId) {
        inTransaction(() -> {
            Area area = areaService.getOrCreateArea(areaName, areaHhId);
            User user = getUserByTelegramId(telegramId);
            user.setArea(area);
            update(user);
        });
    }

    @Override
    public void saveUserSkill(Long telegramId, String skillName, Integer skillHHId) {
        inTransaction(() -> {
            Skill skill = skillService.getOrCreateSkill(skillName, skillHHId);
            User user = getUserByTelegramId(telegramId);
            user.getSkills().add(skill);
            update(user);
        });
    }

    @Override
    public void saveUserSpecialization(Long telegramId, String specializationName, String specializationHHId) {
        inTransaction(() -> {
            Specialization specialization = specializationService
                    .getOrCreateSpecialization(specializationName, specializationHHId);
            User user = getUserByTelegramId(telegramId);
            user.getSpecializations().add(specialization);
            update(user);
        });
    }

    @Override
    public void setSalaryAmount(Long telegramId, Integer salaryAmount) {
        inTransaction(() -> {
            User user = getUserByTelegramId(telegramId);
            user.setSalaryAmount(salaryAmount);
            update(user);
        });
    }

    @Override
    public void setSalaryCurrency(Long telegramId, SalaryCurrency salaryCurrency) {
        inTransaction(() -> {
            User user = getUserByTelegramId(telegramId);
            user.setSalaryCurrency(salaryCurrency);
            update(user);
        });
    }

    @Override
    public void setNodeId(Long telegramId, Integer nodeId) {
        inTransaction(() -> {
            User user = getUserByTelegramId(telegramId);
            user.setNodeId(nodeId);
            update(user);
        });
    }

    @Override
    public void setBirthDate(Long telegramId, Date birthDate) {
        inTransaction(() -> {
            User user = getUserByTelegramId(telegramId);
            user.setBirthDate(birthDate);
            update(user);
        });
    }

    @Override
    public void setFirstName(Long telegramId, String firstName) {
        inTransaction(() -> {
            User user = getUserByTelegramId(telegramId);
            user.setFirstName(firstName);
            update(user);
        });
    }

    @Override
    public void setLastName(Long telegramId, String lastName) {
        inTransaction(() -> {
            User user = getUserByTelegramId(telegramId);
            user.setLastName(lastName);
            update(user);
        });
    }

    @Override
    public void setPhoneNumber(Long telegramId, String phoneNumber) {
        inTransaction(() -> {
            User user = getUserByTelegramId(telegramId);
            user.setPhone(phoneNumber);
            update(user);
        });
    }

    @Override
    public void setGender(Long telegramId, Gender gender) {
        inTransaction(() -> {
            User user = getUserByTelegramId(telegramId);
            user.setGender(gender);
            update(user);
        });
    }

    @Override
    public void setCareerObjective(Long telegramId, String careerObjective) {
        inTransaction(() -> {
            // TODO set in one query
            User user = getUserByTelegramId(telegramId);
            user.setCareerObjective(careerObjective);
            update(user);
        });
    }

    @Override
    public void delete(Long telegramId) {
        inTransaction(() -> {
            User user = getUserByTelegramId(telegramId);
            delete(user);
        });
    }

    @Override
    public void createUserEducation(Long telegramId, EducationLevel educationLevel) {
        inTransaction(() -> {
            User user = getUserByTelegramId(telegramId);

            Education education = new Education(user, educationLevel);
            educationService.create(education);

            // TODO replace with setting one field or set lazy
            getCurrentSession().refresh(user);
            user.setNodeRelationId(education.getId());
            update(user);
        });
    }

    @Override
    public void createUserExperience(Long telegramId) {
        inTransaction(() -> {
            User user = getUserByTelegramId(telegramId);
            Experience experience = new Experience(user);
            experienceService.create(experience);
            getCurrentSession().refresh(user);
            user.setNodeRelationId(experience.getId());
            update(user);
        });
    }
}
