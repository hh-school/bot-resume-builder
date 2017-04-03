package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.question.Question;

public class NonTerminalNode extends Node {
    private Question question;
    private Node next;

    public NonTerminalNode(Question question) {
        this.question = question;
    }

    public synchronized Question getQuestion() {
        return question;
    }

    public synchronized Node getNext() {
        return next;
    }

    public synchronized void setNext(Node next) {
        this.next = next;
    }

    @Override
    public synchronized boolean isTerminal() {
        return false;
    }
}
