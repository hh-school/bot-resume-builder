package ru.hh.resumebuilderbot.http;

import ru.hh.resumebuilderbot.database.model.SalaryCurrency;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.database.model.gender.Gender;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HHUtils {
    private static final Pattern createResponsePattern = Pattern.compile("^/resumes/(.*?)$");
    private static final Map<SalaryCurrency, String> currencyStringMap = new HashMap<>();

    static {
        currencyStringMap.put(SalaryCurrency.EUR, "EUR");
        currencyStringMap.put(SalaryCurrency.RUB, "RUR");
        currencyStringMap.put(SalaryCurrency.USD, "USD");
    }

    public static String convertCurrency(SalaryCurrency salaryCurrency) {
        return currencyStringMap.get(salaryCurrency);
    }

    public static String convertGender(Gender gender) {
        if (gender == null) {
            return null;
        }
        switch (gender) {
            case MALE:
                return "male";
            case FEMALE:
                return "female";
            default:
                return null;
        }
    }

    public static String convertEducationLevel(EducationLevel educationLevel) {
        switch (educationLevel) {
            case HIGHER:
                return "higher";
            case UNFINISHED_HIGHER:
                return "unfinished_higher";
            case SECONDARY:
                return "secondary";
            case SPECIAL_SECONDARY:
                return "special_secondary";
            default:
                throw new UnsupportedOperationException("Invalid educationLevel " + educationLevel.name());
        }
    }

    public enum EducationType {
        ELEMENTARY("elementary"),
        PRIMARY("primary");

        private final String typeName;

        EducationType(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeName() {
            return typeName;
        }
    }

    public static String buildAuthorizationHeader(String tokenType, String accessToken) {
        return String.format("%s %s", tokenType, accessToken);
    }

    public static String getResumeId(String locationHeader) {
        Matcher matcher = createResponsePattern.matcher(locationHeader);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new RuntimeException("Invalid location");
        }
    }
}
