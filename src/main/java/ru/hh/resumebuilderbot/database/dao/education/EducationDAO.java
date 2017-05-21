package ru.hh.resumebuilderbot.database.dao.education;

import ru.hh.resumebuilderbot.database.dao.base.GenericDAO;
import ru.hh.resumebuilderbot.database.model.education.Education;

public interface EducationDAO extends GenericDAO<Education, Integer> {
    Education getCurrentByTelegramId(Long telegramId);
}
