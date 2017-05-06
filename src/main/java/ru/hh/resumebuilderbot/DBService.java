package ru.hh.resumebuilderbot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.ServiceAggregator;
import ru.hh.resumebuilderbot.database.model.Area;
import ru.hh.resumebuilderbot.database.model.SalaryCurrency;
import ru.hh.resumebuilderbot.database.model.Specialization;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.database.model.education.Education;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.database.model.experience.Company;
import ru.hh.resumebuilderbot.database.model.experience.Experience;
import ru.hh.resumebuilderbot.database.model.gender.Gender;

import java.util.Date;
import java.util.Set;


@Singleton
public class DBService {
    public static final Logger log = LoggerFactory.getLogger(DBService.class);
    private final ServiceAggregator serviceAggregator;

    @Inject
    private DBService(ServiceAggregator serviceAggregator) {
        this.serviceAggregator = serviceAggregator;
    }

    public boolean contains(TelegramUser telegramUser) {
        User user = getUser(telegramUser);
        return user != null;
    }

    private User getUser(TelegramUser telegramUser) {
        return serviceAggregator.getUserService().getUserByTelegramId(telegramUser.getId());
    }

    private void clear(TelegramUser telegramUser) {
        User user = getUser(telegramUser);
        serviceAggregator.getUserService().delete(user);
    }

    private void addNewUser(TelegramUser telegramUser, Integer rootNodeId) {
        User user = new User();
        user.setTelegramId(telegramUser.getId());
        user.setNodeId(rootNodeId);
        serviceAggregator.getUserService().create(user);
    }

    public void startNewChat(TelegramUser telegramUser, Integer rootNodeId) {
        if (contains(telegramUser)) {
            clear(telegramUser);
        }
        addNewUser(telegramUser, rootNodeId);
    }

    public Integer getNodeId(TelegramUser telegramUser) {
        User user = getUser(telegramUser);
        return user.getNodeId();
    }

    public void saveNodeId(TelegramUser telegramUser, Integer nodeId) {
        User user = getUser(telegramUser);
        user.setNodeId(nodeId);
        serviceAggregator.getUserService().update(user);
    }

    public void saveBirthDate(TelegramUser telegramUser, Date birthDate) {
        User user = getUser(telegramUser);
        user.setBirthDate(birthDate);
        serviceAggregator.getUserService().update(user);
    }

    public void saveFirstname(TelegramUser telegramUser, String firstName) {
        User user = getUser(telegramUser);
        user.setFirstName(firstName);
        serviceAggregator.getUserService().update(user);
    }

    public void saveLastName(TelegramUser telegramUser, String lastName) {
        User user = getUser(telegramUser);
        user.setLastName(lastName);
        serviceAggregator.getUserService().update(user);
    }

    public void savePhoneNumber(TelegramUser telegramUser, String phoneNumber) {
        User user = getUser(telegramUser);
        user.setPhone(phoneNumber);
        serviceAggregator.getUserService().update(user);
    }

    public void saveGender(TelegramUser telegramUser, Gender gender) {
        User user = getUser(telegramUser);
        user.setGender(gender);
        serviceAggregator.getUserService().update(user);
    }

    public void saveCareerObjective(TelegramUser telegramUser, String careerObjective) {
        User user = getUser(telegramUser);
        user.setCareerObjective(careerObjective);
        serviceAggregator.getUserService().update(user);
    }

    public void saveSalaryAmount(TelegramUser telegramUser, Integer salaryAmount) {
        User user = getUser(telegramUser);
        user.setSalaryAmount(salaryAmount);
        serviceAggregator.getUserService().update(user);
    }

    public void saveSalaryCurrency(TelegramUser telegramUser, SalaryCurrency salaryCurrency) {
        User user = getUser(telegramUser);
        user.setSalaryCurrency(salaryCurrency);
        serviceAggregator.getUserService().update(user);
    }

    public void addNewEducation(TelegramUser telegramUser) {
        User user = getUser(telegramUser);
        Education education = generateNewEducationForUser(user);
        user.getEducations().add(education);
        user.setNodeRelationId(education.getId());
        serviceAggregator.getUserService().update(user);
    }

    private Education generateNewEducationForUser(User user) {
        Education education = new Education();
        education.setUser(user);
        serviceAggregator.getEducationService().create(education);
        return education;
    }

    private Education getCurrentEducation(TelegramUser telegramUser) {
        User user = getUser(telegramUser);
        for (Education education : user.getEducations()) {
            if (education.getId().equals(user.getNodeRelationId())) {
                return education;
            }
        }
        log.error("Не найдено образование для пользователя {} по id {}");
        return null;
    }

    public void saveEducationLevel(TelegramUser telegramUser, EducationLevel educationLevel) {
        Education education = getCurrentEducation(telegramUser);
        education.setLevel(educationLevel);
        serviceAggregator.getEducationService().update(education);
    }

    public void saveInstitute(TelegramUser telegramUser, Integer instituteId, String instituteName) {
        Education education = getCurrentEducation(telegramUser);
        education.setInstitutionId(instituteId);
        education.setInstitutionName(instituteName);
        serviceAggregator.getEducationService().update(education);
    }

    public void saveFaculty(TelegramUser telegramUser, Integer facultyId, String facultyName) {
        Education education = getCurrentEducation(telegramUser);
        education.setFacultyId(facultyId);
        education.setFacultyName(facultyName);
        serviceAggregator.getEducationService().update(education);
    }

    public void saveSpeciality(TelegramUser telegramUser, Integer specialityId, String specialityName) {
        Education education = getCurrentEducation(telegramUser);
        education.setSpecialityId(specialityId);
        education.setSpecialityName(specialityName);
        serviceAggregator.getEducationService().update(education);
    }

    public void saveEducationYear(TelegramUser telegramUser, Integer educationYear) {
        Education education = getCurrentEducation(telegramUser);
        education.setYear(educationYear);
        serviceAggregator.getEducationService().update(education);
    }

    public void saveUserArea(TelegramUser telegramUser, String areaName, Integer areaHHId) {
        User user = getUser(telegramUser);
        Area area = getArea(areaName, areaHHId);
        user.setArea(area);
        serviceAggregator.getUserService().update(user);
    }

    private Area getArea(String areaName, Integer areaHHId) {
        Area area;
        if (areaHHId != null) {
            area = serviceAggregator.getAreaService().getAreaByHHId(areaHHId);
        } else {
            area = serviceAggregator.getAreaService().getAreaByName(areaName);
        }
        if (area == null) {
            area = createNewArea(areaName, areaHHId);
        }
        return area;
    }

    private Area createNewArea(String areaName, Integer areaHHId) {
        Area area = new Area();
        area.setName(areaName);
        area.setHhId(areaHHId);
        serviceAggregator.getAreaService().create(area);
        return area;
    }

    public void addNewExperience(TelegramUser telegramUser) {
        User user = getUser(telegramUser);
        Experience experience = generateNewExperienceForUser(user);
        user.getExperiences().add(experience);
        user.setNodeRelationId(experience.getId());
        serviceAggregator.getUserService().update(user);
    }

    private Experience generateNewExperienceForUser(User user) {
        Experience experience = new Experience();
        experience.setUser(user);
        serviceAggregator.getExperienceService().create(experience);
        return experience;
    }

    private Experience getCurrentExperience(TelegramUser telegramUser) {
        User user = getUser(telegramUser);
        for (Experience experience : user.getExperiences()) {
            if (experience.getId().equals(user.getNodeRelationId())) {
                return experience;
            }
        }
        addNewExperience(telegramUser);
        return serviceAggregator.getExperienceService().get(user.getNodeRelationId());
    }

    public void saveExperienceStartDate(TelegramUser telegramUser, Date startDate) {
        Experience experience = getCurrentExperience(telegramUser);
        experience.setStartDate(startDate);
        serviceAggregator.getExperienceService().update(experience);
    }

    public void saveExpirienceEndDate(TelegramUser telegramUser, Date endDate) {
        Experience experience = getCurrentExperience(telegramUser);
        experience.setEndDate(endDate);
        serviceAggregator.getExperienceService().update(experience);
    }

    public void saveExperiencePosition(TelegramUser telegramUser, String position) {
        Experience experience = getCurrentExperience(telegramUser);
        experience.setPosition(position);
        serviceAggregator.getExperienceService().update(experience);
    }

    public void saveExperienceDescription(TelegramUser telegramUser, String description) {
        Experience experience = getCurrentExperience(telegramUser);
        experience.setDescription(description);
        serviceAggregator.getExperienceService().update(experience);
    }

    public void saveExperienceCompany(TelegramUser telegramUser, String companyName, Integer companyHHId) {
        Experience experience = getCurrentExperience(telegramUser);
        Company company = getCompany(companyName, companyHHId);
        experience.setCompany(company);
        serviceAggregator.getExperienceService().update(experience);
    }

    private Company getCompany(String companyName, Integer companyHHId) {
        Company company;
        if (companyHHId != null) {
            company = serviceAggregator.getCompanyService().getCompanyByHHId(companyHHId);
        } else {
            company = serviceAggregator.getCompanyService().getCompanyByName(companyName);
        }
        if (company == null) {
            company = createNewCompany(companyName, companyHHId);
        }
        return company;
    }

    private Company createNewCompany(String companyName, Integer companyHHId) {
        Company company = new Company();
        company.setName(companyName);
        company.setHHId(companyHHId);
        serviceAggregator.getCompanyService().create(company);
        return company;
    }

    public void saveCompanyArea(Integer hhId, String areaName, Integer areaHHId) {
        Company company = getCompany(null, hhId);
        Area area = getArea(areaName, areaHHId);
        company.setArea(area);
        serviceAggregator.getCompanyService().update(company);
    }

    public void addPosition(TelegramUser telegramUser, Specialization specialization) {
        User user = getUser(telegramUser);
        Set<Specialization> specializations = user.getSpecializations();
        specializations.add(specialization);
        user.setSpecializations(specializations);
        serviceAggregator.getUserService().update(user);
    }
}
