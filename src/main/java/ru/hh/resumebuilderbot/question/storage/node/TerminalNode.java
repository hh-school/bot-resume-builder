package ru.hh.resumebuilderbot.question.storage.node;

public class TerminalNode extends Node {
    @Override
    public synchronized boolean isTerminal() {
        return true;
    }
}
