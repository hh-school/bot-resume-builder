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

    public boolean contains(Long telegramId) {
        User user = getUser(telegramId);
        return user != null;
    }

    private User getUser(Long telegramId) {
        return serviceAggregator.getUserService().getUserByTelegramId(telegramId);
    }

    private void clear(Long telegramId) {
        User user = getUser(telegramId);
        serviceAggregator.getUserService().delete(user);
    }

    private void addNewUser(Long telegramId, Integer rootNodeId) {
        User user = new User();
        user.setTelegramId(telegramId);
        user.setNodeId(rootNodeId);
        serviceAggregator.getUserService().create(user);
    }

    public void startNewChat(Long telegramId, Integer rootNodeId) {
        if (contains(telegramId)) {
            clear(telegramId);
        }
        addNewUser(telegramId, rootNodeId);
    }

    public Integer getNodeId(Long telegramId) {
        User user = getUser(telegramId);
        return user.getNodeId();
    }

    public void saveNodeId(Long telegramId, Integer nodeId) {
        User user = getUser(telegramId);
        user.setNodeId(nodeId);
        serviceAggregator.getUserService().update(user);
    }

    public void saveBirthDate(Long telegramId, Date birthDate) {
        User user = getUser(telegramId);
        user.setBirthDate(birthDate);
        serviceAggregator.getUserService().update(user);
    }

    public void saveFirstname(Long telegramId, String firstName) {
        User user = getUser(telegramId);
        user.setFirstName(firstName);
        serviceAggregator.getUserService().update(user);
    }

    public void saveLastName(Long telegramId, String lastName) {
        User user = getUser(telegramId);
        user.setLastName(lastName);
        serviceAggregator.getUserService().update(user);
    }

    public void savePhoneNumber(Long telegramId, String phoneNumber) {
        User user = getUser(telegramId);
        user.setPhone(phoneNumber);
        serviceAggregator.getUserService().update(user);
    }

    public void saveGender(Long telegramId, Gender gender) {
        User user = getUser(telegramId);
        user.setGender(gender);
        serviceAggregator.getUserService().update(user);
    }

    public void saveCareerObjective(Long telegramId, String careerObjective) {
        User user = getUser(telegramId);
        user.setCareerObjective(careerObjective);
        serviceAggregator.getUserService().update(user);
    }

    public void saveSalaryAmount(Long telegramId, Integer salaryAmount) {
        User user = getUser(telegramId);
        user.setSalaryAmount(salaryAmount);
        serviceAggregator.getUserService().update(user);
    }

    public void saveSalaryCurrency(Long telegramId, SalaryCurrency salaryCurrency) {
        User user = getUser(telegramId);
        user.setSalaryCurrency(salaryCurrency);
        serviceAggregator.getUserService().update(user);
    }

    public void addNewEducation(Long telegramId) {
        User user = getUser(telegramId);
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

    private Education getCurrentEducation(Long telegramId) {
        User user = getUser(telegramId);
        for (Education education : user.getEducations()) {
            if (education.getId().equals(user.getNodeRelationId())) {
                return education;
            }
        }
        log.error("Не найдено образование для пользователя {} по id {}",
                user.getTelegramId(), user.getNodeRelationId());
        return null;
    }

    public void saveEducationLevel(Long telegramId, EducationLevel educationLevel) {
        Education education = getCurrentEducation(telegramId);
        education.setLevel(educationLevel);
        serviceAggregator.getEducationService().update(education);
    }

    public void saveInstitute(Long telegramId, Integer instituteHHId, String instituteName) {
        Education education = getCurrentEducation(telegramId);
        education.setInstitutionId(instituteHHId);
        education.setInstitutionName(instituteName);
        serviceAggregator.getEducationService().update(education);
    }

    public Integer getInstituteHHId(Long telegramId) {
        return getCurrentEducation(telegramId).getInstitutionId();
    }

    public void saveFaculty(Long telegramId, Integer facultyId, String facultyName) {
        Education education = getCurrentEducation(telegramId);
        education.setFacultyId(facultyId);
        education.setFacultyName(facultyName);
        serviceAggregator.getEducationService().update(education);
    }

    public void saveSpeciality(Long telegramId, Integer specialityId, String specialityName) {
        Education education = getCurrentEducation(telegramId);
        education.setSpecialityId(specialityId);
        education.setSpecialityName(specialityName);
        serviceAggregator.getEducationService().update(education);
    }

    public void saveEducationYear(Long telegramId, Integer educationYear) {
        Education education = getCurrentEducation(telegramId);
        education.setYear(educationYear);
        serviceAggregator.getEducationService().update(education);
    }

    public void saveUserArea(Long telegramId, String areaName, Integer areaHHId) {
        User user = getUser(telegramId);
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

    public void addNewExperience(Long telegramId) {
        User user = getUser(telegramId);
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

    private Experience getCurrentExperience(Long telegramId) {
        User user = getUser(telegramId);
        for (Experience experience : user.getExperiences()) {
            if (experience.getId().equals(user.getNodeRelationId())) {
                return experience;
            }
        }
        addNewExperience(telegramId);
        return serviceAggregator.getExperienceService().get(user.getNodeRelationId());
    }

    public void saveExperienceStartDate(Long telegramId, Date startDate) {
        Experience experience = getCurrentExperience(telegramId);
        experience.setStartDate(startDate);
        serviceAggregator.getExperienceService().update(experience);
    }

    public void saveExpirienceEndDate(Long telegramId, Date endDate) {
        Experience experience = getCurrentExperience(telegramId);
        experience.setEndDate(endDate);
        serviceAggregator.getExperienceService().update(experience);
    }

    public void saveExperiencePosition(Long telegramId, String position) {
        Experience experience = getCurrentExperience(telegramId);
        experience.setPosition(position);
        serviceAggregator.getExperienceService().update(experience);
    }

    public void saveExperienceDescription(Long telegramId, String description) {
        Experience experience = getCurrentExperience(telegramId);
        experience.setDescription(description);
        serviceAggregator.getExperienceService().update(experience);
    }

    public void saveExperienceCompany(Long telegramId, String companyName, Integer companyHHId) {
        Experience experience = getCurrentExperience(telegramId);
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

    public void addPosition(Long telegramId, Specialization specialization) {
        User user = getUser(telegramId);
        Set<Specialization> specializations = user.getSpecializations();
        specializations.add(specialization);
        user.setSpecializations(specializations);
        serviceAggregator.getUserService().update(user);
    }
}
