package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.saver;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.user.data.storage.UserData;

public abstract class Saver {
    public abstract void saveAnswer(UserData dest, Answer answer);

    public abstract Saver clone();

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(this.getClass());
    }
}
