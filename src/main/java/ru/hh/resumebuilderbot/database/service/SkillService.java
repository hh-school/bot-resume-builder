package ru.hh.resumebuilderbot.database.service;

import ru.hh.resumebuilderbot.database.model.Skill;
import ru.hh.resumebuilderbot.database.service.base.GenericService;

public interface SkillService extends GenericService<Skill, Integer> {
    Skill getSkillByName(String name);

    Skill getSkillByHHId(Integer hhId);
}
