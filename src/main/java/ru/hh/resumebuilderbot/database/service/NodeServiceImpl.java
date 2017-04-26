package ru.hh.resumebuilderbot.database.service;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.NodeDAO;
import ru.hh.resumebuilderbot.database.model.Node;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NodeServiceImpl extends GenericServiceImpl<Node, Integer, NodeDAO> implements NodeService {
    @Inject
    public NodeServiceImpl(NodeDAO nodeDAO, SessionFactory sessionFactory) {
        super(nodeDAO, sessionFactory);
    }
}
