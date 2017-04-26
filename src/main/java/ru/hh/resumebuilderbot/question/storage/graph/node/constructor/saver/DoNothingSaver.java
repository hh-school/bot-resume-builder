package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.saver;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.user.data.storage.UserData;

public class DoNothingSaver extends Saver {
    @Override
    public void saveAnswer(UserData dest, Answer answer) {

    }

    @Override
    public Saver clone() {
        return new DoNothingSaver();
    }
}
