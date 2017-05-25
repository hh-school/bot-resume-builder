package ru.hh.resumebuilderbot.cv.builder;

import org.joda.time.Interval;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.database.model.education.Education;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.database.model.experience.Experience;
import ru.hh.resumebuilderbot.database.model.gender.Gender;

import java.util.Date;
import java.util.stream.Collectors;

public class PlainTextCVBuilder implements CVBuilder {
    private DBProcessor dbProcessor;

    public PlainTextCVBuilder(DBProcessor dbProcessor) {
        this.dbProcessor = dbProcessor;
    }

    @Override
    public String build(Long telegramId) {
        User user = dbProcessor.getUser(telegramId);
        StringBuilder resumeText = new StringBuilder();
        resumeText.append("Резюме: \n");
        resumeText.append(String.format("%s %s\n", user.getFirstName(), user.getLastName()));
        String gender = user.getGender() == Gender.MALE ? "Мужчина" : "Женщина";
        resumeText.append(String.format("%s, %d лет, дата рождения: %s\n", gender,
                getAgeInYears(user.getBirthDate()), user.getBirthDate()));
        resumeText.append(String.format("Телефон: %s\n", user.getPhone()));
        resumeText.append(String.format("Электронная почта: %s\n\n", user.getEmail()));
        resumeText.append(String.format("Город проживания: %s\n\n", user.getArea().getName()));
        resumeText.append(user.getCareerObjective() + "\n\n");
        resumeText.append("Занятость: полная занятость\n" +
                "График работы: полный день, гибкий график \n\n" +
                "Опыт работы:\n");
        for (Experience experience : user.getExperiences()) {
            resumeText.append(String.format("%s\n", experience.getCompany().getName()));
            resumeText.append(String.format("%s - %s\n", experience.getStartDate(),
                    experience.getEndDate() == null ? "По настоящее время" : experience.getEndDate()));
            resumeText.append(String.format("%s\n", experience.getPosition()));
            resumeText.append(String.format("%s\n\n", experience.getDescription()));
        }
        resumeText.append("Образование: ");
        for (Education education : user.getEducations()) {
            if (education.getLevel() == EducationLevel.SECONDARY) {
                resumeText.append("среднее\n");
                resumeText.append(String.format("%s\n", education.getInstitutionName()));
            } else {
                resumeText.append("высшее\n");
                resumeText.append(String.format("%s\n", education.getInstitutionName()));
                resumeText.append(String.format("%s\n", education.getFacultyName()));
                resumeText.append(String.format("Специальность: %s\n", education.getSpecialityName()));
                resumeText.append(String.format("Год окончания: %s\n\n", education.getYear()));
            }
        }
        resumeText.append("Ключевые навыки:\n");
        String skills = String.join(", ", user.getSkills().stream()
                .map(skill -> skill.getName())
                .collect(Collectors.toList()));
        resumeText.append(String.format("%s.", skills));

        return resumeText.toString();
    }

    private int getAgeInYears(Date date) {
        Interval interval = new Interval(date.getTime(), System.currentTimeMillis());
        return interval.toPeriod().getYears();
    }
}
