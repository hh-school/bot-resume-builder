package ru.hh.resumebuilderbot.question.storage.node;

public abstract class Node {

    public synchronized boolean isTerminal() {
        return (this instanceof TerminalNode);
    }

    public synchronized boolean isLinear() {
        return (this instanceof LinearNode);
    }

    public synchronized boolean isForking() {
        return (this instanceof ForkingNode);
    }
}
