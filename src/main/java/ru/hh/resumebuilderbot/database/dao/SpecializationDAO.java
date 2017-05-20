package ru.hh.resumebuilderbot.database.dao;

import ru.hh.resumebuilderbot.database.dao.base.GenericDAO;
import ru.hh.resumebuilderbot.database.model.Specialization;

public interface SpecializationDAO extends GenericDAO<Specialization, Integer> {
    Specialization getSpecializationByName(String name);

    Specialization getSpecializationByHHId(String hhId);
}
