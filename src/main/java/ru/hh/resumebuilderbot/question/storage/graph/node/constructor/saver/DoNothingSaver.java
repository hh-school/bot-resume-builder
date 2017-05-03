package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.saver;

import ru.hh.resumebuilderbot.Answer;

public class DoNothingSaver extends Saver {
    @Override
    public String getDatabaseField() {
        return null;
    }

    @Override
    public Saver clone() {
        return new DoNothingSaver();
    }
}
