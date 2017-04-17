package ru.hh.resumebuilderbot.di;

import com.google.inject.AbstractModule;
import ru.hh.resumebuilderbot.database.dao.AreaDAO;
import ru.hh.resumebuilderbot.database.dao.AreaDAOImpl;
import ru.hh.resumebuilderbot.database.dao.NodeDAO;
import ru.hh.resumebuilderbot.database.dao.NodeDAOImpl;
import ru.hh.resumebuilderbot.database.dao.SpecializationDAO;
import ru.hh.resumebuilderbot.database.dao.SpecializationDAOImpl;
import ru.hh.resumebuilderbot.database.dao.UserDAO;
import ru.hh.resumebuilderbot.database.dao.UserDAOImpl;
import ru.hh.resumebuilderbot.database.dao.contact.ContactDAO;
import ru.hh.resumebuilderbot.database.dao.contact.ContactDAOImpl;
import ru.hh.resumebuilderbot.database.dao.contact.ContactTypeDAO;
import ru.hh.resumebuilderbot.database.dao.contact.ContactTypeDAOImpl;
import ru.hh.resumebuilderbot.database.dao.education.EducationDAO;
import ru.hh.resumebuilderbot.database.dao.education.EducationDAOImpl;
import ru.hh.resumebuilderbot.database.dao.experience.CompanyDAO;
import ru.hh.resumebuilderbot.database.dao.experience.CompanyDAOImpl;
import ru.hh.resumebuilderbot.database.dao.experience.ExperienceDAO;
import ru.hh.resumebuilderbot.database.dao.experience.ExperienceDAOImpl;
import ru.hh.resumebuilderbot.database.dao.experience.IndustryDAO;
import ru.hh.resumebuilderbot.database.dao.experience.IndustryDAOImpl;
import ru.hh.resumebuilderbot.database.service.AreaService;
import ru.hh.resumebuilderbot.database.service.AreaServiceImpl;
import ru.hh.resumebuilderbot.database.service.NodeService;
import ru.hh.resumebuilderbot.database.service.NodeServiceImpl;
import ru.hh.resumebuilderbot.database.service.SpecializationService;
import ru.hh.resumebuilderbot.database.service.SpecializationServiceImpl;
import ru.hh.resumebuilderbot.database.service.UserService;
import ru.hh.resumebuilderbot.database.service.UserServiceImpl;
import ru.hh.resumebuilderbot.database.service.contact.ContactService;
import ru.hh.resumebuilderbot.database.service.contact.ContactServiceImpl;
import ru.hh.resumebuilderbot.database.service.contact.ContactTypeService;
import ru.hh.resumebuilderbot.database.service.contact.ContactTypeServiceImpl;
import ru.hh.resumebuilderbot.database.service.education.EducationService;
import ru.hh.resumebuilderbot.database.service.education.EducationServiceImpl;
import ru.hh.resumebuilderbot.database.service.experience.CompanyService;
import ru.hh.resumebuilderbot.database.service.experience.CompanyServiceImpl;
import ru.hh.resumebuilderbot.database.service.experience.ExperienceService;
import ru.hh.resumebuilderbot.database.service.experience.ExperienceServiceImpl;
import ru.hh.resumebuilderbot.database.service.experience.IndustryService;
import ru.hh.resumebuilderbot.database.service.experience.IndustryServiceImpl;

public class GuiceProdModule extends AbstractModule {

    private void configureDAO() {
        bind(ContactDAO.class).to(ContactDAOImpl.class);
        bind(ContactTypeDAO.class).to(ContactTypeDAOImpl.class);
        bind(EducationDAO.class).to(EducationDAOImpl.class);
        bind(CompanyDAO.class).to(CompanyDAOImpl.class);
        bind(ExperienceDAO.class).to(ExperienceDAOImpl.class);
        bind(IndustryDAO.class).to(IndustryDAOImpl.class);
        bind(AreaDAO.class).to(AreaDAOImpl.class);
        bind(NodeDAO.class).to(NodeDAOImpl.class);
        bind(SpecializationDAO.class).to(SpecializationDAOImpl.class);
        bind(UserDAO.class).to(UserDAOImpl.class);
    }

    private void configureServices() {
        bind(ContactService.class).to(ContactServiceImpl.class);
        bind(ContactTypeService.class).to(ContactTypeServiceImpl.class);
        bind(EducationService.class).to(EducationServiceImpl.class);
        bind(CompanyService.class).to(CompanyServiceImpl.class);
        bind(ExperienceService.class).to(ExperienceServiceImpl.class);
        bind(IndustryService.class).to(IndustryServiceImpl.class);
        bind(AreaService.class).to(AreaServiceImpl.class);
        bind(NodeService.class).to(NodeServiceImpl.class);
        bind(SpecializationService.class).to(SpecializationServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
    }

    @Override
    protected void configure() {
        configureDAO();
        configureServices();
    }
}