package ru.hh.resumebuilderbot.database.service;

import ru.hh.resumebuilderbot.database.model.SalaryCurrency;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.database.model.gender.Gender;
import ru.hh.resumebuilderbot.database.service.base.GenericService;
import ru.hh.resumebuilderbot.http.response.entity.Position;

import java.util.Date;

public interface UserService extends GenericService<User, Integer> {
    User getUserByTelegramId(long telegramId);

    void saveUserArea(Long telegramId, String areaName, Integer areaHHId);

    void saveUserSkill(Long telegramId, String skillName, Integer skillHHId);

    void setSalary(Long telegramId, Integer salaryAmount, SalaryCurrency salaryCurrency);

    void setNodeId(Long telegramId, Integer nodeId);

    void setBirthDate(Long telegramId, Date birthDate);

    void setFirstName(Long telegramId, String firstName);

    void setLastName(Long telegramId, String lastName);

    void setPhoneNumber(Long telegramId, String phoneNumber);

    void setEmail(Long telegramId, String email);

    void setGender(Long telegramId, Gender gender);

    void setCareerObjective(Long telegramId, String careerObjective);

    void setCareerObjectiveWithSpecializations(Long telegramId, Position position);

    void delete(Long telegramId);

    void createUserEducation(Long telegramId, EducationLevel educationLevel);

    void createUserExperience(Long telegramId);

    void setHHResumeId(Long telegramId, String hhResumeId);

    void setStudentUserSpecialization(Long telegramId);
}
