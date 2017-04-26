package ru.hh.resumebuilderbot.user.data.storage;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.TelegramUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.ServiceAggregator;
import ru.hh.resumebuilderbot.database.model.Area;
import ru.hh.resumebuilderbot.database.model.SalaryCurrency;
import ru.hh.resumebuilderbot.database.model.Specialization;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.database.model.contact.Contact;
import ru.hh.resumebuilderbot.database.model.education.Education;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.database.model.experience.Experience;
import ru.hh.resumebuilderbot.database.model.gender.Gender;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;

import java.util.Date;
import java.util.Set;


@Singleton
public class UserDataStorage {
    public static final Logger log = LoggerFactory.getLogger(UserDataStorage.class);
    private final ServiceAggregator serviceAggregator;
    private final Provider<Graph> graphProvider;

    @Inject
    private UserDataStorage(ServiceAggregator serviceAggregator, Provider<Graph> graphProvider) {
        this.serviceAggregator = serviceAggregator;
        this.graphProvider = graphProvider;
    }

    public boolean contains(TelegramUser telegramUser) {
        User user = getUser(telegramUser);
        if (user == null) {
            return false;
        }
        return true;
    }

    private User getUser(TelegramUser telegramUser){
        return serviceAggregator.getUserService().getUserByTelegramId(telegramUser.getId());
    }

    public void clear(TelegramUser telegramUser) {
        User user = getUser(telegramUser);
        serviceAggregator.getUserService().delete(user);
    }

    private void addNewUser(TelegramUser telegramUser){
        User user = new User();
        user.setTelegramId(telegramUser.getId());
        serviceAggregator.getUserService().create(user);
    }

    public void startNewChat(TelegramUser telegramUser) {
        if (contains(telegramUser)) {
            clear(telegramUser);
        }
        addNewUser(telegramUser);
    }

    public QuestionNode getCurrentQuestionNode(TelegramUser telegramUser){
        User user = getUser(telegramUser);
        QuestionNode questionNode =  graphProvider.get().getRoot();
        return questionNode;
    }

    public Question getCurrentQuestion(TelegramUser telegramUser) {
        QuestionNode questionNode =  getCurrentQuestionNode(telegramUser);
        return questionNode.getQuestion();
    }

    public void moveForward(TelegramUser telegramUser){
        QuestionNode currentQuestionNode = getCurrentQuestionNode(telegramUser);
        currentQuestionNode = currentQuestionNode.getNext();
        User user = getUser(telegramUser);
        //user.setNodeId(currentQuestionNode.getId());
        serviceAggregator.getUserService().update(user);
    }

    public void saveBirthDate(TelegramUser telegramUser, Date birthDate){
        User user = getUser(telegramUser);
        user.setBirthDate(birthDate);
        serviceAggregator.getUserService().update(user);
    }

    public void saveFirstname(TelegramUser telegramUser, String firstName){
        User user = getUser(telegramUser);
        user.setFirstName(firstName);
        serviceAggregator.getUserService().update(user);
    }

    public void saveLastName(TelegramUser telegramUser, String lastName){
        User user = getUser(telegramUser);
        user.setLastName(lastName);
        serviceAggregator.getUserService().update(user);
    }

    public void addContact(TelegramUser telegramUser, Contact contact){
        User user = getUser(telegramUser);
        Set<Contact> contacts = user.getContacts();
        contacts.add(contact);
        user.setContacts(contacts);
        serviceAggregator.getUserService().update(user);
    }

    public void addEducation(TelegramUser telegramUser, Education education){
        User user = getUser(telegramUser);
        Set<Education> educations = user.getEducations();
        educations.add(education);
        user.setEducations(educations);
        serviceAggregator.getUserService().update(user);
    }

    public void saveArea(TelegramUser telegramUser, Area area){
        User user = getUser(telegramUser);
        user.setArea(area);
        serviceAggregator.getUserService().update(user);
    }

    public void addExperience(TelegramUser telegramUser, Experience experience){
        User user = getUser(telegramUser);
        Set<Experience> experiences = user.getExperiences();
        experiences.add(experience);
        user.setExperiences(experiences);
        serviceAggregator.getUserService().update(user);
    }

    public void saveGender(TelegramUser telegramUser, Gender gender){
        User user = getUser(telegramUser);
        user.setGender(gender);
        serviceAggregator.getUserService().update(user);
    }

    public void saveCareerObjective(TelegramUser telegramUser, String careerObjective){
        User user = getUser(telegramUser);
        user.setCareerObjective(careerObjective);
        serviceAggregator.getUserService().update(user);
    }

    public void addSpecialization(TelegramUser telegramUser, Specialization specialization){
        User user = getUser(telegramUser);
        Set<Specialization> specializations = user.getSpecializations();
        specializations.add(specialization);
        user.setSpecializations(specializations);
        serviceAggregator.getUserService().update(user);
    }

    public void saveSalaryAmount(TelegramUser telegramUser, Integer salaryAmount){
        User user = getUser(telegramUser);
        user.setSalaryAmount(salaryAmount);
        serviceAggregator.getUserService().update(user);
    }

    public void saveSalaryCurrency(TelegramUser telegramUser, SalaryCurrency salaryCurrency){
        User user = getUser(telegramUser);
        user.setSalaryCurrency(salaryCurrency);
        serviceAggregator.getUserService().update(user);
    }

    private Education getCurrentEducation(TelegramUser telegramUser){
        User user = getUser(telegramUser);
        return serviceAggregator.getEducationService().get(user.getNodeRelationId());
    }

    public void saveEducationYear(TelegramUser telegramUser, Integer educationYear){
        Education education = getCurrentEducation(telegramUser);
        education.setYear(educationYear);
        serviceAggregator.getEducationService().update(education);
    }

    public void saveInstitute(TelegramUser telegramUser, Integer instituteId, String instituteName){
        Education education = getCurrentEducation(telegramUser);
        education.setInstitutionId(instituteId);
        education.setInstitutionName(instituteName);
        serviceAggregator.getEducationService().update(education);
    }

    public void saveFaculty(TelegramUser telegramUser, Integer facultyId, String facultyName){
        Education education = getCurrentEducation(telegramUser);
        education.setFacultyId(facultyId);
        education.setFacultyName(facultyName);
        serviceAggregator.getEducationService().update(education);
    }

    public void saveSpeciality(TelegramUser telegramUser, Integer specialityId, String specialityName){
        Education education = getCurrentEducation(telegramUser);
        education.setSpecialityId(specialityId);
        education.setSpecialityName(specialityName);
        serviceAggregator.getEducationService().update(education);
    }

    public void saveEducationLevel(TelegramUser telegramUser, EducationLevel educationLevel){
        Education education = getCurrentEducation(telegramUser);
        education.setLevel(educationLevel);
        serviceAggregator.getEducationService().update(education);
    }
}
