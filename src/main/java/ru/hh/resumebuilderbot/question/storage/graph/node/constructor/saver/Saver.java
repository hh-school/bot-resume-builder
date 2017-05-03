package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.saver;

import ru.hh.resumebuilderbot.Answer;

public abstract class Saver {
    public abstract String getDatabaseField();

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
