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

   public String getCode() {
        return code;
    }
}
