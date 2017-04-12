package ru.hh.resumebuilderbot.question.storage.builder;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeCycle;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeForking;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeLinear;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeTerminal;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NodeSet {
    private Map<Integer, Entry> nodesMap;

    private QuestionNode root;

    NodeSet(List<XMLParser.Entry> rawData) throws IOException {
        if (!isValid(rawData)) {
            throw new IOException("Error parsing XML");
        }
        nodesMap = makeNodes(rawData);
    }

    private NodeSet(QuestionNode root, Map<Integer, Entry> nodesMap) {
        this.nodesMap = new HashMap<>();
        for (Map.Entry<Integer, Entry> entry : nodesMap.entrySet()) {
            Entry newEntry = entry.getValue().cloneContent();
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

    private Map<Integer, Entry> makeNodes(List<XMLParser.Entry> rawData) {
        Map<Integer, Entry> result = new HashMap<>();
        rawData.forEach((x) -> result.put(x.getIndex(), makeEntry(x)));
        return result;
    }

    private Entry makeEntry(XMLParser.Entry xmlEntry) {
        if (xmlEntry.getType().equals("terminal")) {
            int index = xmlEntry.getIndex();
            QuestionNode terminalNode = new QuestionNodeTerminal();
            if (xmlEntry.isRoot()) {
                root = terminalNode;
            }
            return new Entry(terminalNode, index);
        }
        Question question = new Question(xmlEntry.getText(), xmlEntry.getAllowedAnswers());
        if (xmlEntry.getType().equals("linear")) {
            QuestionNodeLinear linearNode = new QuestionNodeLinear(question);
            int nextIndex = xmlEntry.getNextIndex();
            if (xmlEntry.isRoot()) {
                root = linearNode;
            }
            return new Entry(linearNode, nextIndex);
        }
        String pattern = xmlEntry.getPattern();
        int nextIndexYes = xmlEntry.getNextYes();
        int nextIndexNo = xmlEntry.getNextNo();
        if (xmlEntry.getType().equals("forking")) {
            QuestionNodeForking forkingNode = new QuestionNodeForking(question, pattern);
            if (xmlEntry.isRoot()) {
                root = forkingNode;
            }
            return new Entry(forkingNode, nextIndexYes, nextIndexNo);
        }
        QuestionNodeCycle cycleNode = new QuestionNodeCycle(question, pattern);
        if (xmlEntry.isRoot()) {
            root = cycleNode;
        }
        return new Entry(cycleNode, nextIndexYes, nextIndexNo);
    }

    private void linkNodes(Map<Integer, Entry> nodesMap) {
        for (Entry entry : nodesMap.values()) {
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
            if (node instanceof QuestionNodeCycle) {
                QuestionNodeCycle cycleNode = (QuestionNodeCycle) node;
                cycleNode.setNextIn(nodesMap.get(nextIndexYes).getNode());
                cycleNode.setNextOut(nodesMap.get(nextIndexNo).getNode());

            }
        }
    }

    private boolean isValid(List<XMLParser.Entry> rawData) {
        // step 1 - check if number of roots exactly equals 17
        long numberOfRoots = rawData.stream()
                .filter((x) -> x.isRoot())
                .count();
        if (numberOfRoots != 1) {
            return false;
        }

        // step 2 - check ids' uniqueness
        Set<Integer> usedIndices = new HashSet<>();
        for (XMLParser.Entry entry : rawData) {
            int index = entry.getIndex();
            if (usedIndices.contains(index)) {
                return false;
            }
            usedIndices.add(index);
        }

        return true;
    }

    public NodeSet cloneContent() {
        return new NodeSet(root, nodesMap);
    }

    private class Entry {
        private QuestionNode node;
        private int nextIndex;
        private int nextIndexYes;
        private int nextIndexNo;

        Entry(QuestionNode node, int nextIndex) {
            this.node = node;
            this.nextIndex = nextIndex;
        }

        Entry(QuestionNode node, int nextIndexYes, int nextIndexNo) {
            this.node = node;
            this.nextIndexYes = nextIndexYes;
            this.nextIndexNo = nextIndexNo;
        }

        private Entry(QuestionNode node, int nextIndex, int nextIndexYes, int nextIndexNo) {
            this.node = node;
            this.nextIndex = nextIndex;
            this.nextIndexYes = nextIndexYes;
            this.nextIndexNo = nextIndexNo;
        }

        public Entry cloneContent() {
            return new Entry(node.cloneContent(), nextIndex, nextIndexYes, nextIndexNo);
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

}
