package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import ru.hh.resumebuilderbot.Answer;

import java.util.regex.Pattern;

public class PhoneNumberValidator extends Validator {
    @Override
    public boolean answerIsValid(Answer answer) {
        String phoneNumber = (String) answer.getAnswerBody();
        return Pattern.compile("\\+?[\\d-)(]+").matcher(phoneNumber).matches();
    }

    @Override
    public Validator clone() {
        return new PhoneNumberValidator();
    }
}
