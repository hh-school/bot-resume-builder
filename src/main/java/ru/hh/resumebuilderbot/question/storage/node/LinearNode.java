package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.question.Question;

public class LinearNode extends NonTerminalNode {
    private Node next;

    public LinearNode(Question question) {
        this.question = question;
    }

    @Override
    public synchronized Node getNext() {
        return next;
    }

    public synchronized void setNext(Node next) {
        this.next = next;
    }
}
