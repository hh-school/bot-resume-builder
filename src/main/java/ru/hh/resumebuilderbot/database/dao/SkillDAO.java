package ru.hh.resumebuilderbot.database.dao;

import ru.hh.resumebuilderbot.database.dao.base.GenericDAO;
import ru.hh.resumebuilderbot.database.model.Skill;

public interface SkillDAO extends GenericDAO<Skill, Integer> {
    Skill getSkillByName(String name);

    Skill getSkillByHHId(Integer hhId);

    Skill getSkillByHHIdOrName(Integer hhId, String name);
}
