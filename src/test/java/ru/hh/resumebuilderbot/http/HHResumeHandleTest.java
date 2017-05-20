package ru.hh.resumebuilderbot.http;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import retrofit2.Response;
import ru.hh.resumebuilderbot.database.model.Area;
import ru.hh.resumebuilderbot.database.model.SalaryCurrency;
import ru.hh.resumebuilderbot.database.model.Skill;
import ru.hh.resumebuilderbot.database.model.Specialization;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.database.model.education.Education;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.database.model.experience.Company;
import ru.hh.resumebuilderbot.database.model.experience.Experience;
import ru.hh.resumebuilderbot.database.model.gender.Gender;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class HHResumeHandleTest {
    private static final String AUTHORIZATION_HEADER = "Bearer " +
            "KBM2SU7D6L6CIS44VERLJ91S242K86C5J5953Q8A6EVIHUUDUSC9N6ONNLIRL5I9";
    private static HHHTTPService hhHTTPService;
    public static final Pattern createResponsePattern = Pattern.compile("^/resumes/(.*?)$");

    private String resumeId;

    @BeforeClass
    public void setUpClass() throws Exception {
        hhHTTPService = HHHTTPServiceUtils.getService(
                HHHTTPServiceUtils.buildGson(),
                HHHTTPServiceUtils.buildHttpClient()
        );
    }

    @AfterMethod
    public void tearDown() throws IOException {
        if (resumeId != null) {
            Response<Void> deleteResponse = hhHTTPService.deleteResume(resumeId, AUTHORIZATION_HEADER).execute();
            resumeId = null;
            assertEquals(deleteResponse.code(), 204);
        }
    }

    private User prepareUser() throws Exception {
        DateFormat hhDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        User user = new User();
        user.setLastName("Testlastname");
        user.setFirstName("Testfirstname");
        user.setBirthDate(hhDateFormat.parse("1999-12-11"));
        user.setCareerObjective("Test career objective");
        user.setGender(Gender.MALE);
        user.setSalaryAmount(300000);
        user.setSalaryCurrency(SalaryCurrency.RUB);

        Area area = new Area();
        area.setHhId(88);
        user.setArea(area);

        Company company = new Company();
        company.setHHId(1740);
        company.setName("Yandex");
        Set<Experience> experiences = new HashSet<>();
        Experience experience = new Experience();
        experience.setStartDate(hhDateFormat.parse("2000-01-01"));
        experience.setEndDate(hhDateFormat.parse("2001-01-01"));
        experience.setCompany(company);
        experience.setDescription("testDescription");
        experience.setPosition("testPosition");
        experiences.add(experience);
        user.setExperiences(experiences);

        String phone = "+7(915)4567890";
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber testPhoneNumber = phoneUtil.parse(phone, "RU");
        assertTrue(phoneUtil.isValidNumber(testPhoneNumber));
        user.setPhone(phone);
        user.setEmail("applicant@example.com");

        Set<Skill> skills = new HashSet<>();
        Skill skill = new Skill();
        skill.setName("Git");
        skills.add(skill);
        user.setSkills(skills);

        Set<Specialization> specializations = new HashSet<>();
        Specialization specialization = new Specialization();
        specialization.setHhId("1.221");
        specializations.add(specialization);
        user.setSpecializations(specializations);

        Set<Education> educations = new HashSet<>();
        Education education = new Education();
        education.setInstitutionId(38921);
        education.setInstitutionName("Московский государственный технический университет им. Н.Э. Баумана, Москва");
        education.setFacultyId(24481);
        education.setFacultyName("Факультет машиностроительных технологий");
        education.setSpecialityId(550);
        education.setSpecialityName("Метрология и метрологическое обеспечение (инженер)");
        education.setLevel(EducationLevel.HIGHER);
        education.setYear(2014);
        educations.add(education);
        user.setEducations(educations);
        return user;
    }

    @Test
    public void testCreateResume() throws Exception {
        Response<Void> createResponse = hhHTTPService.createResume(prepareUser(), AUTHORIZATION_HEADER).execute();
        assertEquals(createResponse.code(), 201);

        String locationHeader = createResponse.headers().get("Location");
        Matcher matcher = createResponsePattern.matcher(locationHeader);
        if (matcher.find()) {
            resumeId = matcher.group(1);
        } else {
            throw new RuntimeException("Invalid regexp");
        }

        Response<Void> publishResponse = hhHTTPService.publishResume(resumeId, AUTHORIZATION_HEADER).execute();
        assertEquals(publishResponse.code(), 204);
    }
}
