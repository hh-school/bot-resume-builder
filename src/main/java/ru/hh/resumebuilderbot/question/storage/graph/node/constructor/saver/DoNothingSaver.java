package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.saver;

import ru.hh.resumebuilderbot.Answer;

public class DoNothingSaver extends Saver {
    @Override
    public void saveAnswer(Answer answer) {

    }

    @Override
    public Saver clone() {
        return new DoNothingSaver();
    }
}
