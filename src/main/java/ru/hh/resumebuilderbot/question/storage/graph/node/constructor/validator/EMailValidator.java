package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import java.util.regex.Pattern;

public class EMailValidator extends PatternValidator {

    public EMailValidator() {
        pattern = Pattern.compile(".+@.+");
        notification = "Хм, это точно имейл? А как же получать увлекательный спам и дополнительную информацию " +
                "о приглашениях от работодателей? Давайте попробуем еще раз!";
    }

    @Override
    public Validator clone() {
        return new EMailValidator();
    }
}
