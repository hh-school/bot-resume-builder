package ru.hh.resumebuilderbot.database.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.Skill;

import javax.validation.constraints.NotNull;

@Singleton
public class SkillDAOImpl extends GenericDAOImpl<Skill, Integer> implements SkillDAO {
    @Inject
    public SkillDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
