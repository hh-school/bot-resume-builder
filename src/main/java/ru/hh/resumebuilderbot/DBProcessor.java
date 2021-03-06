package ru.hh.resumebuilderbot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.model.SalaryCurrency;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.database.model.gender.Gender;
import ru.hh.resumebuilderbot.database.service.UserService;
import ru.hh.resumebuilderbot.database.service.education.EducationService;
import ru.hh.resumebuilderbot.database.service.experience.ExperienceService;
import ru.hh.resumebuilderbot.http.response.entity.Position;

import java.util.Date;


@Singleton
public class DBProcessor {
    private static final Logger log = LoggerFactory.getLogger(DBProcessor.class);
    private final EducationService educationService;
    private final ExperienceService experienceService;
    private final UserService userService;

    @Inject
    public DBProcessor(EducationService educationService,
                       ExperienceService experienceService,
                       UserService userService) {
        this.educationService = educationService;
        this.experienceService = experienceService;
        this.userService = userService;
    }

    public boolean contains(Long telegramId) {
        User user = getUser(telegramId);
        return user != null;
    }

    public User getUser(Long telegramId) {
        return userService.getUserByTelegramId(telegramId);
    }

    private void addNewUser(Long telegramId, Integer rootNodeId) {
        userService.create(new User(telegramId, rootNodeId));
        // TODO move to experience choosing node
        setStudentUserSpecialization(telegramId);
    }

    public void startNewChat(Long telegramId, Integer rootNodeId) {
        if (contains(telegramId)) {
            clear(telegramId);
        }
        addNewUser(telegramId, rootNodeId);
    }

    public Integer getNodeId(Long telegramId) {
        return getUser(telegramId).getNodeId();
    }

    public void setCurrentEducationInstitute(Long telegramId, Integer instituteHHId, String instituteName) {
        educationService.setCurrentEducationInstitute(telegramId, instituteHHId, instituteName);
    }

    public void setCurrentEducationInstituteName(Long telegramId, String instituteName) {
        educationService.setCurrentEducationInstituteName(telegramId, instituteName);
    }

    public Integer getInstituteHHId(Long telegramId) {
        return educationService.getCurrentByTelegramId(telegramId).getInstitutionId();
    }

    public void setCurrentEducationFaculty(Long telegramId, Integer facultyId, String facultyName) {
        educationService.setCurrentEducationFaculty(telegramId, facultyId, facultyName);
    }

    public void setCurrentEducationFacultyName(Long telegramId, String facultyName) {
        educationService.setCurrentEducationFacultyName(telegramId, facultyName);
    }

    public void setCurrentEducationSpeciality(Long telegramId, Integer specialityId, String specialityName) {
        educationService.setCurrentEducationSpeciality(telegramId, specialityId, specialityName);
    }

    public void setCurrentEducationSpecialityName(Long telegramId, String specialityName) {
        educationService.setCurrentEducationSpecialityName(telegramId, specialityName);
    }

    public void setCurrentEducationYear(Long telegramId, Integer educationYear) {
        educationService.setCurrentEducationYear(telegramId, educationYear);
    }

    public void saveUserArea(Long telegramId, String areaName, Integer areaHHId) {
        userService.saveUserArea(telegramId, areaName, areaHHId);
    }

    public void saveUserSkill(Long telegramId, String skillName, Integer skillHHId) {
        userService.saveUserSkill(telegramId, skillName, skillHHId);
    }

    public void saveUserSkill(Long telegramId, String skillName) {
        saveUserSkill(telegramId, skillName, null);
    }

    public void saveSalary(Long telegramId, Integer salaryAmount) {
        userService.setSalary(telegramId, salaryAmount, SalaryCurrency.RUB);
    }

    public void setNodeId(Long telegramId, Integer nodeId) {
        userService.setNodeId(telegramId, nodeId);
    }

    public void setBirthDate(Long telegramId, Date birthDate) {
        userService.setBirthDate(telegramId, birthDate);
    }

    public void setFirstName(Long telegramId, String firstName) {
        userService.setFirstName(telegramId, firstName);
    }

    public void setLastName(Long telegramId, String lastName) {
        userService.setLastName(telegramId, lastName);
    }

    public void setPhoneNumber(Long telegramId, String phoneNumber) {
        userService.setPhoneNumber(telegramId, phoneNumber);
    }

    public void setEmail(Long telegramId, String email) {
        userService.setEmail(telegramId, email);
    }

    public void setGender(Long telegramId, Gender gender) {
        userService.setGender(telegramId, gender);
    }

    public void setCareerObjective(Long telegramId, String careerObjective) {
        userService.setCareerObjective(telegramId, careerObjective);
    }

    public void setCareerObjectiveWithSpecializations(Long telegramId, Position position) {
        userService.setCareerObjectiveWithSpecializations(telegramId, position);
    }

    private void clear(Long telegramId) {
        userService.delete(telegramId);
    }

    public void createUserEducation(Long telegramId, EducationLevel educationLevel) {
        userService.createUserEducation(telegramId, educationLevel);
    }

    public void createUserExperience(Long telegramId) {
        userService.createUserExperience(telegramId);
    }

    public void setCurrentExperienceStartDate(Long telegramId, Date startDate) {
        experienceService.setCurrentExperienceStartDate(telegramId, startDate);
    }

    public void setCurrentExperienceEndDate(Long telegramId, Date endDate) {
        experienceService.setCurrentExperienceEndDate(telegramId, endDate);
    }

    public void setCurrentExperiencePosition(Long telegramId, String position) {
        experienceService.setCurrentExperiencePosition(telegramId, position);
    }

    public void setCurrentExperienceDescription(Long telegramId, String description) {
        experienceService.setCurrentExperienceDescription(telegramId, description);
    }

    public void saveCurrentExperienceCompany(Long telegramId, String companyName, Integer companyHHId) {
        experienceService.saveCurrentExperienceCompany(telegramId, companyName, companyHHId);
    }

    public void saveCurrentExperienceCompany(Long telegramId, String companyName) {
        saveCurrentExperienceCompany(telegramId, companyName, null);
    }

    public void setStudentUserSpecialization(Long telegramId) {
        userService.setStudentUserSpecialization(telegramId);
    }

    public void setHHResumeId(Long telegramId, String hhResumeId) {
        userService.setHHResumeId(telegramId, hhResumeId);
    }
}
