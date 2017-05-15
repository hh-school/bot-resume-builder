package ru.hh.resumebuilderbot.database.model.education;

import java.util.Arrays;
import java.util.Comparator;

public enum EducationLevel {
    SECONDARY,
    SPECIAL_SECONDARY,
    UNFINISHED_HIGHER,
    HIGHER;

    private static final EducationLevel lowest = Arrays
            .stream(values())
            .min(Comparator.comparing(EducationLevel::ordinal))
            .orElse(null);

    public static EducationLevel getLowest() {
        return lowest;
    }

    public static EducationLevel fromCode(String code) {
        if (code.equals("Высшее")) {
            return HIGHER;
        }
        if (code.equals("Неоконченное высшее")) {
            return UNFINISHED_HIGHER;
        }
        if (code.equals("Среднее специальное")) {
            return SPECIAL_SECONDARY;
        }
        if (code.equals("Среднее")) {
            return SECONDARY;
        }
        throw new UnsupportedOperationException("The code " + code + " is not supported!");
    }
}
