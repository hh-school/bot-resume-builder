package ru.hh.resumebuilderbot.http.type.adapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
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
import ru.hh.resumebuilderbot.http.HHUtils;

import java.lang.reflect.Type;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserSerializer implements JsonSerializer<User> {
    private static final Format formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public JsonElement serialize(User user, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject userJson = new JsonObject();
        userJson.addProperty("first_name", user.getFirstName());
        userJson.addProperty("last_name", user.getLastName());
        Date birthDate = user.getBirthDate();
        if (birthDate != null) {
            userJson.addProperty("birth_date", formatter.format(birthDate));
        }
        userJson.addProperty("title", user.getCareerObjective());

        String phone = user.getPhone();
        String email = user.getEmail();
        if (phone != null && email != null) {
            userJson.add("contact", serializeContacts(phone, email));
        }

        userJson.add("gender", serializeGender(user.getGender()));

        Integer salaryAmount = user.getSalaryAmount();
        if (salaryAmount != null) {
            userJson.add("salary", serializeSalary(salaryAmount, user.getSalaryCurrency()));
        }
        Area area = user.getArea();
        if (area != null) {
            userJson.add("area", serializeArea(area));
            userJson.add("citizenship", serializeCitizenships());
        }
        Set<Experience> experiences = user.getExperiences();
        if (experiences != null) {
            userJson.add("experience", serializeExperiences(experiences));
        }

        Set<Education> educations = user.getEducations();
        if (educations != null) {
            userJson.add("education", serializeEducations(educations));
        }

        Set<Skill> skills = user.getSkills();
        if (skills != null) {
            userJson.add("skill_set", serializeSkills(skills));
        }

        Set<Specialization> specializations = user.getSpecializations();
        if (specializations != null) {
            userJson.add("specialization", serializeSpecializations(specializations));
        }

        userJson.add("language", serializeLanguages());
        return userJson;
    }

    private JsonElement serializeLanguages() {
        JsonArray languagesJson = new JsonArray();

        JsonObject languageLevelJson = new JsonObject();
        languageLevelJson.addProperty("id", "fluent");

        JsonObject languageJson = new JsonObject();
        languageJson.addProperty("id", "rus");
        languageJson.add("level", languageLevelJson);

        languagesJson.add(languageJson);
        return languagesJson;
    }

    private JsonElement serializeCitizenships() {
        JsonArray citizenshipsJson = new JsonArray();

        JsonObject citizenshipJson = new JsonObject();
        // 113 - Россия
        citizenshipJson.addProperty("id", "113");

        citizenshipsJson.add(citizenshipJson);
        return citizenshipsJson;
    }

    private JsonElement serializeSpecializations(Set<Specialization> specializations) {
        JsonArray specializationsJson = new JsonArray();
        for (Specialization specialization : specializations) {
            specializationsJson.add(serializeSpecialization(specialization));
        }
        return specializationsJson;
    }

    private JsonObject serializeSpecialization(Specialization specialization) {
        JsonObject specializationJson = new JsonObject();
        specializationJson.addProperty("id", specialization.getHhId());
        return specializationJson;
    }

    private JsonElement serializeSkills(Set<Skill> skills) {
        JsonArray skillsJson = new JsonArray();
        for (Skill skill : skills) {
            skillsJson.add(skill.getName());
        }
        return skillsJson;
    }

    private JsonElement serializeEducations(Set<Education> educations) {
        JsonObject educationsJson = new JsonObject();
        educationsJson.add("level", serializeEducationLevel(getCommonEducationLevel(educations)));
        Map<HHUtils.EducationType, Collection<Education>> splitEducationsMap = splitEducations(educations);

        for (Map.Entry<HHUtils.EducationType, Collection<Education>> entry : splitEducationsMap.entrySet()) {
            JsonArray typedEducationJson = serializeTypedEducations(entry.getValue());
            educationsJson.add(entry.getKey().getTypeName(), typedEducationJson);
        }
        return educationsJson;
    }

    private JsonElement serializeEducationLevel(EducationLevel educationLevel) {
        JsonObject educationLevelJson = new JsonObject();
        educationLevelJson.addProperty("id", HHUtils.convertEducationLevel(educationLevel));
        return educationLevelJson;
    }

    private JsonArray serializeTypedEducations(Collection<Education> educations) {
        JsonArray typedEducationsJson = new JsonArray();
        for (Education education : educations) {
            typedEducationsJson.add(serializeEducation(education));
        }
        return typedEducationsJson;
    }

    private JsonObject serializeEducation(Education education) {
        JsonObject educationJson = new JsonObject();
        educationJson.addProperty("name", education.getInstitutionName());
        educationJson.addProperty("name_id", education.getInstitutionId());
        educationJson.addProperty("organization", education.getFacultyName());
        educationJson.addProperty("organization_id", education.getFacultyId());
        educationJson.addProperty("result", education.getSpecialityName());
        educationJson.addProperty("result_id", education.getSpecialityId());
        educationJson.addProperty("year", education.getYear());
        return educationJson;
    }

    private Map<HHUtils.EducationType, Collection<Education>> splitEducations(Set<Education> educations) {
        Collection<Education> elementaryEducations = new ArrayList<>();
        Collection<Education> primaryEducations = new ArrayList<>();
        for (Education education : educations) {
            switch (education.getLevel()) {
                case SECONDARY:
                    elementaryEducations.add(education);
                    break;
                case SPECIAL_SECONDARY:
                case HIGHER:
                case UNFINISHED_HIGHER:
                    primaryEducations.add(education);
            }
        }
        Map<HHUtils.EducationType, Collection<Education>> result = new HashMap<>();
        result.put(HHUtils.EducationType.PRIMARY, primaryEducations);
        result.put(HHUtils.EducationType.ELEMENTARY, elementaryEducations);
        return result;
    }

    private EducationLevel getCommonEducationLevel(Set<Education> educations) {
        EducationLevel maxEducationLevel = EducationLevel.getLowest();
        for (Education education : educations) {
            EducationLevel educationLevel = education.getLevel();
            if (maxEducationLevel.ordinal() < educationLevel.ordinal()) {
                maxEducationLevel = educationLevel;
            }
        }
        return maxEducationLevel;
    }

    private JsonElement serializeExperiences(Set<Experience> experiences) {
        JsonArray experiencesJson = new JsonArray();
        for (Experience experience : experiences) {
            experiencesJson.add(serializeExperience(experience));
        }
        return experiencesJson;
    }

    private JsonObject serializeExperience(Experience experience) {
        JsonObject experienceJson = new JsonObject();
        Company company = experience.getCompany();
        experienceJson.addProperty("company_id", company.getHHId());
        experienceJson.addProperty("company", company.getName());
        Area area = company.getArea();
        if (area != null) {
            experienceJson.add("area", serializeArea(area));
        }
        experienceJson.addProperty("description", experience.getDescription());
        experienceJson.addProperty("position", experience.getPosition());
        Date endDate = experience.getEndDate();
        String endDateString = null;
        if (endDate != null) {
            endDateString = formatter.format(endDate);
        }
        Date startDate = experience.getStartDate();
        if (startDate != null) {
            experienceJson.addProperty("start", formatter.format(startDate));
        }

        experienceJson.addProperty("end", endDateString);
        return experienceJson;
    }

    private JsonObject serializeArea(Area area) {
        JsonObject areaJson = new JsonObject();
        areaJson.addProperty("id", area.getHhId());
        return areaJson;
    }

    private JsonObject serializeGender(Gender gender) {
        JsonObject genderJson = new JsonObject();
        genderJson.addProperty("id", HHUtils.convertGender(gender));
        return genderJson;
    }

    private JsonObject serializeSalary(Integer salaryAmount, SalaryCurrency salaryCurrency) {
        JsonObject salaryJson = new JsonObject();
        salaryJson.addProperty("amount", salaryAmount);
        salaryJson.addProperty("currency", HHUtils.convertCurrency(salaryCurrency));
        return salaryJson;
    }

    private JsonArray serializeContacts(String phone, String email) {
        JsonArray contactsJson = new JsonArray();
        contactsJson.add(serializePhone(phone));
        contactsJson.add(serializeEmail(email));
        return contactsJson;
    }

    private JsonObject serializeContactType(String type) {
        JsonObject contactType = new JsonObject();
        contactType.addProperty("id", type);
        return contactType;
    }

    private JsonObject serializePhone(String phone) {
        JsonObject phoneContactJson = new JsonObject();
        phoneContactJson.add("type", serializeContactType("cell"));
        phoneContactJson.addProperty("preferred", true);
        JsonObject phoneJson = new JsonObject();
        phoneJson.addProperty("formatted", phone);
        phoneContactJson.add("value", phoneJson);
        return phoneContactJson;
    }

    private JsonObject serializeEmail(String email) {
        JsonObject emailContactJson = new JsonObject();
        emailContactJson.add("type", serializeContactType("email"));
        emailContactJson.addProperty("preferred", false);
        emailContactJson.addProperty("value", email);
        return emailContactJson;
    }
}
