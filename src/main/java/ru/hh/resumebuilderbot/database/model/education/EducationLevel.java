package ru.hh.resumebuilderbot.database.model.education;

public enum EducationLevel {
    HIGHER,
    HIGHER_NOT_FINISHED,
    SECONDARY,
    BASE;

    public static EducationLevel fromCode(String code) {
        if (code.equals("Высшее")) {
            return HIGHER;
        }
        if (code.equals("Неоконченное высшее")) {
            return HIGHER_NOT_FINISHED;
        }
        if (code.equals("Среднее специальное")) {
            return SECONDARY;
        }
        if (code.equals("Среднее")) {
            return BASE;
        }
        throw new UnsupportedOperationException("The code " + code + " is not supported!");
    }
}
