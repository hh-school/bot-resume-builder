package ru.hh.resumebuilderbot.database.service;

import ru.hh.resumebuilderbot.database.model.Specialization;
import ru.hh.resumebuilderbot.database.service.base.GenericService;

public interface SpecializationService extends GenericService<Specialization, Integer> {
    Specialization getSpecializationByName(String name);

    Specialization getSpecializationByHHId(String hhId);

    Specialization getOrCreateSpecialization(String specializationName, String specializationHHId);
}
