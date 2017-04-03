package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.question.Question;

public abstract class NonTerminalNode extends Node {
    protected Question question;

    public synchronized Question getQuestion() {
        return question;
    }

    public abstract Node getNext();

    @Override
    public synchronized boolean isTerminal() {
        return false;
    }

    public abstract boolean isLinear();
}
