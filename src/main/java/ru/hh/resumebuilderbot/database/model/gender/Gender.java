package ru.hh.resumebuilderbot.database.model.gender;

public enum Gender {
    MALE('M'),
    FEMALE('F');

    private final char code;

    Gender(char code) {
        this.code = code;
    }

    public static Gender fromCode(char code) {
        if (code == 'M' || code == 'm' || code == 'М' || code == 'м') {
            return MALE;
        }
        if (code == 'F' || code == 'f' || code == 'Ж' || code == 'ж') {
            return FEMALE;
        }
        throw new UnsupportedOperationException("The code " + code + " is not supported!");
    }

    public char getCode() {
        return code;
    }
}
