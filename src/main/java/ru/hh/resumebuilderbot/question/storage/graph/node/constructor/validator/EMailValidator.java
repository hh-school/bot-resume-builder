package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import java.util.regex.Pattern;

public class EMailValidator extends PatternValidator {
    public EMailValidator() {
        pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        notification = "Хм, это точно имейл? А как же получать увлекательный спам и дополнительную информацию " +
                "о приглашениях от работодателей? Давайте попробуем еще раз!";
    }

    @Override
    public Validator clone() {
        return new EMailValidator();
    }
}
