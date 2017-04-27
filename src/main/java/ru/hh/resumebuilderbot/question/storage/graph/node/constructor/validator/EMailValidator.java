package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import ru.hh.resumebuilderbot.Answer;

import java.util.regex.Pattern;

public class EMailValidator extends Validator {
    @Override
    public boolean answerIsValid(Answer answer) {
        String email = (String) answer.getAnswerBody();
        return Pattern.compile(".+@.+").matcher(email).matches();
    }

    @Override
    public Validator clone() {
        return new EMailValidator();
    }
}
