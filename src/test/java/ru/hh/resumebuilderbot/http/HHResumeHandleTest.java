package ru.hh.resumebuilderbot.http;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import retrofit2.Response;
import ru.hh.resumebuilderbot.database.model.Area;
import ru.hh.resumebuilderbot.database.model.User;
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
        Response<Void> deleteResponse = hhHTTPService.deleteResume(resumeId, AUTHORIZATION_HEADER).execute();
        resumeId = null;
        assertEquals(deleteResponse.code(), 204);
    }

    private User prepareUser() throws Exception {
        DateFormat hhDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        User user = new User();
        user.setLastName("Testlastname");
        user.setFirstName("Testfirstname");
        user.setBirthDate(hhDateFormat.parse("1999-12-11"));
        user.setCareerObjective("Test career objective");
        user.setGender(Gender.MALE);

        Area area = new Area();
        area.setHhId(88);
        user.setArea(area);

        Company company = new Company();
        company.setHHId(1740);
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
        return user;
    }

    @Test
    public void testCreateResume() throws Exception {
        Response<Void> createResponse = hhHTTPService.createResume(prepareUser(), AUTHORIZATION_HEADER).execute();
        assertEquals(createResponse.code(), 201);

        String locationHeader = createResponse.headers().get("Location");
        Pattern pattern = Pattern.compile("^/resumes/(.*?)$");
        Matcher matcher = pattern.matcher(locationHeader);
        if (matcher.find()) {
            resumeId = matcher.group(1);
        } else {
            throw new RuntimeException("Invalid regexp");
        }
//        Response<Void> statusResponse = hhHTTPService.resumeStatus(resumeId, AUTHORIZATION_HEADER).execute();
//        assertEquals(statusResponse.code(), 200);

//        Response<Void> publishResponse = hhHTTPService.publishResume(resumeId, AUTHORIZATION_HEADER).execute();
//        assertEquals(publishResponse.code(), 200);
    }
}
