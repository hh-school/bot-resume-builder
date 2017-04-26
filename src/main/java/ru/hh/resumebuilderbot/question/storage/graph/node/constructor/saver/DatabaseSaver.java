package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.saver;

import ru.hh.resumebuilderbot.Answer;

import java.util.Objects;

public class DatabaseSaver extends Saver {

    private String databaseField;

    public DatabaseSaver(String databaseField) {
        this.databaseField = databaseField;
    }

    public DatabaseSaver() {
    }

    @Override
    public void saveAnswer(Answer answer) {

    }

    @Override
    public Saver clone() {
        return new DatabaseSaver(databaseField);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DatabaseSaver that = (DatabaseSaver) o;
        return Objects.equals(databaseField, that.databaseField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), databaseField);
    }
}
