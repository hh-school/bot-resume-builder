package ru.hh.resumebuilderbot.question.storage.builder;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.node.CycleNode;
import ru.hh.resumebuilderbot.question.storage.node.ForkingNode;
import ru.hh.resumebuilderbot.question.storage.node.LinearNode;
import ru.hh.resumebuilderbot.question.storage.node.QuestionGraphNode;
import ru.hh.resumebuilderbot.question.storage.node.TerminalNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NodeSet {
    private List<XMLParser.Entry> rawData;

    private boolean valid;
    private QuestionGraphNode root;

    public NodeSet(List<XMLParser.Entry> rawData) {
        this.rawData = rawData;
    }

    public boolean isValid() {
        return valid;
    }

    public QuestionGraphNode getRoot() {
        return root;
    }

    public void build() {
        Map<Integer, Entry> nodesMap = makeNodes();
        linkNodes(nodesMap);
        validate(nodesMap);
    }

    private Map<Integer, Entry> makeNodes() {
        Map<Integer, Entry> result = new HashMap<>();
        rawData.forEach((x) -> result.put(x.getIndex(), makeEntry(x)));
        return result;
    }

    private Entry makeEntry(XMLParser.Entry xmlEntry) {
        if (xmlEntry.getType().equals("terminal")) {
            int index = xmlEntry.getIndex();
            return new Entry(new TerminalNode(), index);
        }
        Question question = new Question(xmlEntry.getText(), xmlEntry.getAllowedAnswers());
        if (xmlEntry.getType().equals("linear")) {
            LinearNode linearNode = new LinearNode(question);
            int nextIndex = xmlEntry.getNextIndex();
            return new Entry(linearNode, nextIndex);
        }
        String pattern = xmlEntry.getPattern();
        int nextIndexYes = xmlEntry.getNextYes();
        int nextIndexNo = xmlEntry.getNextNo();
        if (xmlEntry.getType().equals("forking")) {
            ForkingNode forkingNode = new ForkingNode(question, pattern);
            return new Entry(forkingNode, nextIndexYes, nextIndexNo);
        }
        CycleNode cycleNode = new CycleNode(question, pattern);
        return new Entry(cycleNode, nextIndexYes, nextIndexNo);
    }

    private void linkNodes(Map<Integer, Entry> nodesMap) {
        for (Entry entry : nodesMap.values()) {
            QuestionGraphNode node = entry.getNode();
            if (node instanceof LinearNode) {
                int nextIndex = entry.getNextIndex();
                LinearNode linearNode = (LinearNode) node;
                linearNode.setNext(nodesMap.get(nextIndex).getNode());
                continue;
            }
            int nextIndexYes = entry.getNextIndexYes();
            int nextIndexNo = entry.getNextIndexNo();
            if (node instanceof ForkingNode) {
                ForkingNode forkingNode = (ForkingNode) node;
                forkingNode.setNextYes(nodesMap.get(nextIndexYes).getNode());
                forkingNode.setNextNo(nodesMap.get(nextIndexNo).getNode());

            }
            if (node instanceof CycleNode) {
                CycleNode cycleNode = (CycleNode) node;
                cycleNode.setNextIn(nodesMap.get(nextIndexYes).getNode());
                cycleNode.setNextOut(nodesMap.get(nextIndexNo).getNode());

            }
        }
    }

    private void validate(Map<Integer, Entry> nodesMap) {
        // step 1 - check if number of roots exactly equals 1
        Set<Integer> nonRootEntries = new HashSet();

        for (Entry entry : nodesMap.values()) {
            if (entry.getNode() instanceof LinearNode) {
                nonRootEntries.add(entry.getNextIndex());
            }
            if (entry.getNode() instanceof ForkingNode || entry.getNode() instanceof CycleNode) {
                nonRootEntries.add(entry.getNextIndexNo());
                nonRootEntries.add(entry.getNextIndexYes());
            }
        }
        Set<Integer> roots = new HashSet<>();
        roots.addAll(nodesMap.keySet());
        roots.removeAll(nonRootEntries);
        if (roots.size() != 1) {
            valid = false;
            return;
        }
        int rootIndex = 0;
        for (int x : roots) {
            rootIndex = x;
        }
        root = nodesMap.get(rootIndex).getNode();

        valid = true;
    }

    private class Entry {
        private QuestionGraphNode node;
        private int nextIndex;
        private int nextIndexYes;
        private int nextIndexNo;

        public Entry(QuestionGraphNode node, int nextIndex) {
            this.node = node;
            this.nextIndex = nextIndex;
        }

        public Entry(QuestionGraphNode node, int nextIndexYes, int nextIndexNo) {
            this.node = node;
            this.nextIndexYes = nextIndexYes;
            this.nextIndexNo = nextIndexNo;
        }

        public QuestionGraphNode getNode() {
            return node;
        }

        public int getNextIndex() {
            return nextIndex;
        }

        public int getNextIndexYes() {
            return nextIndexYes;
        }

        public int getNextIndexNo() {
            return nextIndexNo;
        }
    }

}
