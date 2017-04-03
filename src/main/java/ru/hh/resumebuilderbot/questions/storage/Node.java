package ru.hh.resumebuilderbot.questions.storage;

import ru.hh.resumebuilderbot.Question;

public class Node {
    private NodeType type;
    private Question question;
    private Node next;

    public Node(NodeType type, Question question) {
        this.type = type;
        this.question = question;
    }

    public Node(NodeType type) {
        this.type = type;
    }

    public boolean isLast() {
        return (type == NodeType.END);
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
}
