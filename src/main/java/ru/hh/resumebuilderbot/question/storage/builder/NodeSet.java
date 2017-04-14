package ru.hh.resumebuilderbot.question.storage.builder;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.builder.xml.parser.XMLEntry;
import ru.hh.resumebuilderbot.question.storage.builder.xml.parser.XMLValidator;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeForking;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeLinear;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeTerminal;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeSet {
    private Map<Integer, NodeSetEntry> nodesMap;

    private QuestionNode root;

    NodeSet(List<XMLEntry> rawData) throws IOException {
        XMLValidator.validate(rawData);
        nodesMap = makeNodes(rawData);
    }

    private NodeSet(QuestionNode root, Map<Integer, NodeSetEntry> nodesMap) {
        this.nodesMap = new HashMap<>();
        for (Map.Entry<Integer, NodeSetEntry> entry : nodesMap.entrySet()) {
            NodeSetEntry newEntry = entry.getValue().cloneContent();
            if (entry.getValue().getNode() == root) {
                this.root = newEntry.getNode();
            }
            this.nodesMap.put(entry.getKey(), newEntry);
        }
    }

    public QuestionNode getRoot() {
        return root;
    }

    public void build() {
        linkNodes(nodesMap);
    }

    private Map<Integer, NodeSetEntry> makeNodes(List<XMLEntry> rawData) {
        Map<Integer, NodeSetEntry> result = new HashMap<>();
        rawData.forEach((x) -> result.put(x.getIndex(), makeEntry(x)));
        return result;
    }

    private NodeSetEntry makeEntry(XMLEntry xmlEntry) {
        boolean isSkippable = xmlEntry.isSkippable();
        if (xmlEntry.getType().equals("terminal")) {
            int index = xmlEntry.getIndex();
            QuestionNode terminalNode = new QuestionNodeTerminal();
            if (xmlEntry.isRoot()) {
                root = terminalNode;
            }
            return new NodeSetEntry(terminalNode, index);
        }
        Question question = xmlEntry.getQuestion();
        if (xmlEntry.getType().equals("linear")) {
            QuestionNodeLinear linearNode = new QuestionNodeLinear(question, isSkippable);
            int nextIndex = xmlEntry.getNextIndex();
            if (xmlEntry.isRoot()) {
                root = linearNode;
            }
            return new NodeSetEntry(linearNode, nextIndex);
        }
        String pattern = xmlEntry.getPattern();
        int nextIndexYes = xmlEntry.getNextYes();
        int nextIndexNo = xmlEntry.getNextNo();
        QuestionNodeForking forkingNode = new QuestionNodeForking(question, pattern, isSkippable);
        if (xmlEntry.isRoot()) {
            root = forkingNode;
        }
        return new NodeSetEntry(forkingNode, nextIndexYes, nextIndexNo);
    }

    private void linkNodes(Map<Integer, NodeSetEntry> nodesMap) {
        for (NodeSetEntry entry : nodesMap.values()) {
            QuestionNode node = entry.getNode();
            if (node instanceof QuestionNodeLinear) {
                int nextIndex = entry.getNextIndex();
                QuestionNodeLinear linearNode = (QuestionNodeLinear) node;
                linearNode.setNext(nodesMap.get(nextIndex).getNode());
                continue;
            }
            int nextIndexYes = entry.getNextIndexYes();
            int nextIndexNo = entry.getNextIndexNo();
            if (node instanceof QuestionNodeForking) {
                QuestionNodeForking forkingNode = (QuestionNodeForking) node;
                forkingNode.setNextYes(nodesMap.get(nextIndexYes).getNode());
                forkingNode.setNextNo(nodesMap.get(nextIndexNo).getNode());
            }
        }
    }

    public NodeSet cloneContent() {
        return new NodeSet(root, nodesMap);
    }

}
