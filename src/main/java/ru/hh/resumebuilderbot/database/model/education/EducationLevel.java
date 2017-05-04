package ru.hh.resumebuilderbot.database.model.education;

public enum EducationLevel {
    HIGHER("Высшее"),
    HIGHER_NOT_FINISHED("Неоконченное высшее"),
    SECONDARY("Среднее специальное"),
    BASE("Среднее");

    private final String code;

    EducationLevel(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
