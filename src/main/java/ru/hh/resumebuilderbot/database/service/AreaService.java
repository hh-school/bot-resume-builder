package ru.hh.resumebuilderbot.database.service;

import ru.hh.resumebuilderbot.database.model.Area;
import ru.hh.resumebuilderbot.database.service.base.GenericService;

public interface AreaService extends GenericService<Area, Integer> {

    public Area getAreaByHHId(Integer hhId);

    public Area getAreaByName(String name);
}
