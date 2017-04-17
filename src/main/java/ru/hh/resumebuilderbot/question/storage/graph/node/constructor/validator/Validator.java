package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import ru.hh.resumebuilderbot.Answer;

public abstract class Validator {
    public abstract boolean answerIsValid(Answer answer);

    public abstract Validator clone();

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(this.getClass());
    }
}
