package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;

public abstract class Validator {
    protected String notification = TextsStorage.getText(TextId.INVALID_ANSWER);

    public abstract boolean answerIsValid(Answer answer);

    public String getNotification() {
        return this.notification;
    }

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
