package ru.hh.resumebuilderbot.database.service;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.SkillDAO;
import ru.hh.resumebuilderbot.database.model.Skill;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SkillServiceImpl extends GenericServiceImpl<Skill, Integer, SkillDAO>
        implements SkillService {
    @Inject
    public SkillServiceImpl(SkillDAO skillDAO, SessionFactory sessionFactory) {
        super(skillDAO, sessionFactory);
    }

    @Override
    public Skill getSkillByHHId(Integer hhId) {
        return inTransaction(() -> dao.getSkillByHHId(hhId));
    }

    @Override
    public Skill getSkillByName(String name) {
        return inTransaction(() -> dao.getSkillByName(name));
    }
}
