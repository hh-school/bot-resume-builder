package ru.hh.resumebuilderbot.database.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.base.GenericDAOImpl;
import ru.hh.resumebuilderbot.database.model.Node;

import javax.validation.constraints.NotNull;

@Singleton
public class NodeDAOImpl extends GenericDAOImpl<Node, Integer> implements NodeDAO {
    @Inject
    public NodeDAOImpl(@NotNull SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
