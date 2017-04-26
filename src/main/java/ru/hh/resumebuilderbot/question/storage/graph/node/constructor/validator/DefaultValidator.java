package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import ru.hh.resumebuilderbot.Answer;

public class DefaultValidator extends Validator {
    @Override
    public boolean answerIsValid(Answer answer) {
        return true;
    }

    @Override
    public Validator clone() {
        return new DefaultValidator();
    }
}
