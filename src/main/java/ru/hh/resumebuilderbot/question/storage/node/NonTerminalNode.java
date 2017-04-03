package ru.hh.resumebuilderbot.question.storage.node;

import ru.hh.resumebuilderbot.question.Question;

public class NonTerminalNode extends Node{
    private Question question;
    private Node next;

    public NonTerminalNode(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
}
