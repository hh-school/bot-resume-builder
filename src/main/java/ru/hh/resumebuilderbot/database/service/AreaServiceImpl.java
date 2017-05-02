package ru.hh.resumebuilderbot.database.service;

import org.hibernate.SessionFactory;
import ru.hh.resumebuilderbot.database.dao.AreaDAO;
import ru.hh.resumebuilderbot.database.model.Area;
import ru.hh.resumebuilderbot.database.service.base.GenericServiceImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AreaServiceImpl extends GenericServiceImpl<Area, Integer, AreaDAO> implements AreaService {
    @Inject
    public AreaServiceImpl(AreaDAO areaDAO, SessionFactory sessionFactory) {
        super(areaDAO, sessionFactory);
    }

    public Area getAreaByHHId(Integer hhId){
        return inTransaction(() -> dao.getAreaByHHId(hhId));
    }
}
