package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import java.util.regex.Pattern;

public class EMailValidator extends PatternValidator {

    public EMailValidator() {
        pattern = Pattern.compile(".+@.+");
    }

    @Override
    public Validator clone() {
        return new EMailValidator();
    }
}
