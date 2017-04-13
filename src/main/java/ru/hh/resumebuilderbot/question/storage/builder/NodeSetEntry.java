package ru.hh.resumebuilderbot.question.storage.builder;

import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;

public class NodeSetEntry {
    private QuestionNode node;
    private int nextIndex;
    private int nextIndexYes;
    private int nextIndexNo;

    NodeSetEntry(QuestionNode node, int nextIndex) {
        this.node = node;
        this.nextIndex = nextIndex;
    }

    NodeSetEntry(QuestionNode node, int nextIndexYes, int nextIndexNo) {
        this.node = node;
        this.nextIndexYes = nextIndexYes;
        this.nextIndexNo = nextIndexNo;
    }

    private NodeSetEntry(QuestionNode node, int nextIndex, int nextIndexYes, int nextIndexNo) {
        this.node = node;
        this.nextIndex = nextIndex;
        this.nextIndexYes = nextIndexYes;
        this.nextIndexNo = nextIndexNo;
    }

    NodeSetEntry cloneContent() {
        return new NodeSetEntry(node.cloneContent(), nextIndex, nextIndexYes, nextIndexNo);
    }

    public QuestionNode getNode() {
        return node;
    }

    int getNextIndex() {
        return nextIndex;
    }

    int getNextIndexYes() {
        return nextIndexYes;
    }

    int getNextIndexNo() {
        return nextIndexNo;
    }


}
