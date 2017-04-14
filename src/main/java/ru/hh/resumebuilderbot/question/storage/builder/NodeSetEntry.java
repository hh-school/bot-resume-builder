package ru.hh.resumebuilderbot.question.storage.builder;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.builder.xml.parser.XMLEntry;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeForking;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeLinear;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeTerminal;

import java.util.Map;

public class NodeSetEntry {
    private QuestionNode node;
    private int nextIndex;
    private int nextIndexYes;
    private int nextIndexNo;

    private NodeSetEntry(QuestionNode node, int nextIndex) {
        this.node = node;
        this.nextIndex = nextIndex;
    }

    private NodeSetEntry(QuestionNode node, int nextIndexYes, int nextIndexNo) {
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

    static NodeSetEntry fromXMLEntry(XMLEntry xmlEntry) {
        Map<String, String> classData = xmlEntry.getClassData();
        if (xmlEntry.getType().equals("terminal")) {
            int index = xmlEntry.getIndex();
            QuestionNode terminalNode = new QuestionNodeTerminal();
            return new NodeSetEntry(terminalNode, index);
        }

        boolean isSkippable = Boolean.parseBoolean(classData.get("skippable"));
        Question question = xmlEntry.getQuestion();
        if (xmlEntry.getType().equals("linear")) {
            QuestionNodeLinear linearNode = new QuestionNodeLinear(question, isSkippable);
            int nextIndex = xmlEntry.getNextIndex();
            return new NodeSetEntry(linearNode, nextIndex);
        }
        String pattern = classData.get("pattern");
        int nextIndexYes = xmlEntry.getNextYes();
        int nextIndexNo = xmlEntry.getNextNo();
        QuestionNodeForking forkingNode = new QuestionNodeForking(question, pattern, isSkippable);
        return new NodeSetEntry(forkingNode, nextIndexYes, nextIndexNo);
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
