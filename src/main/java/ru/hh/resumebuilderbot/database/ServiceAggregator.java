package ru.hh.resumebuilderbot.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.database.service.AreaService;
import ru.hh.resumebuilderbot.database.service.SpecializationService;
import ru.hh.resumebuilderbot.database.service.UserService;
import ru.hh.resumebuilderbot.database.service.contact.ContactService;
import ru.hh.resumebuilderbot.database.service.contact.ContactTypeService;
import ru.hh.resumebuilderbot.database.service.education.EducationService;
import ru.hh.resumebuilderbot.database.service.experience.CompanyService;
import ru.hh.resumebuilderbot.database.service.experience.ExperienceService;
import ru.hh.resumebuilderbot.database.service.experience.IndustryService;

@Singleton
public class ServiceAggregator {
    private final ContactService contactService;
    private final ContactTypeService contactTypeService;
    private final EducationService educationService;
    private final CompanyService companyService;
    private final ExperienceService experienceService;
    private final IndustryService industryService;
    private final AreaService areaService;
    private final SpecializationService specializationService;
    private final UserService userService;

    @Inject
    public ServiceAggregator(ContactService contactService,
                             ContactTypeService contactTypeService,
                             EducationService educationService,
                             CompanyService companyService,
                             ExperienceService experienceService,
                             IndustryService industryService,
                             AreaService areaService,
                             SpecializationService specializationService,
                             UserService userService) {
        this.contactService = contactService;
        this.contactTypeService = contactTypeService;
        this.educationService = educationService;
        this.companyService = companyService;
        this.experienceService = experienceService;
        this.industryService = industryService;
        this.areaService = areaService;
        this.specializationService = specializationService;
        this.userService = userService;
    }

    public ContactService getContactService() {
        return contactService;
    }

    public ContactTypeService getContactTypeService() {
        return contactTypeService;
    }

    public EducationService getEducationService() {
        return educationService;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }

    public ExperienceService getExperienceService() {
        return experienceService;
    }

    public IndustryService getIndustryService() {
        return industryService;
    }

    public AreaService getAreaService() {
        return areaService;
    }

    public SpecializationService getSpecializationService() {
        return specializationService;
    }

    public UserService getUserService() {
        return userService;
    }
}
