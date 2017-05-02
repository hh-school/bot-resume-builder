package ru.hh.resumebuilderbot.database.dao;

import ru.hh.resumebuilderbot.database.dao.base.GenericDAO;
import ru.hh.resumebuilderbot.database.model.Area;

public interface AreaDAO extends GenericDAO<Area, Integer> {
    public Area getAreaByHHId(Integer hhId);
}
