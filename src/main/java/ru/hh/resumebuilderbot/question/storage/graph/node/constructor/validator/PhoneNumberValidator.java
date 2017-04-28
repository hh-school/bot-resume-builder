package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import java.util.regex.Pattern;

public class PhoneNumberValidator extends PatternValidator {
    public PhoneNumberValidator() {
        pattern = Pattern.compile("\\+?[\\d-)(]+");
    }

    @Override
    public Validator clone() {
        return new PhoneNumberValidator();
    }
}
