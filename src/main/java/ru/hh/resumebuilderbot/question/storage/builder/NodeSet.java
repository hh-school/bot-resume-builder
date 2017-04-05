package ru.hh.resumebuilderbot.question.storage.builder;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.node.ForkingNode;
import ru.hh.resumebuilderbot.question.storage.node.LinearNode;
import ru.hh.resumebuilderbot.question.storage.node.Node;
import ru.hh.resumebuilderbot.question.storage.node.TerminalNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NodeSet {
    private List<XMLParser.Entry> rawData;

    private boolean valid;
    private Node root;

    public NodeSet(List<XMLParser.Entry> rawData) {
        this.rawData = rawData;
    }

    public boolean isValid() {
        return valid;
    }

    public Node getRoot() {
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
            return new Entry(new TerminalNode(), 0);
        }
        Question question = new Question(xmlEntry.getText(), xmlEntry.getAllowedAnswers());
        if (xmlEntry.getType().equals("linear")) {
            LinearNode linearNode = new LinearNode(question);
            int nextIndex = xmlEntry.getNextIndex();
            return new Entry(linearNode, nextIndex);
        }
        String pattern = xmlEntry.getPattern();
        ForkingNode forkingNode = new ForkingNode(question, pattern);
        int nextIndexYes = xmlEntry.getNextYes();
        int nextIndexNo = xmlEntry.getNextNo();
        return new Entry(forkingNode, nextIndexYes, nextIndexNo);
    }

    private void linkNodes(Map<Integer, Entry> nodesMap) {
        for (Entry entry : nodesMap.values()) {
            Node node = entry.getNode();
            if (node.isLinear()) {
                int nextIndex = entry.getNextIndex();
                LinearNode linearNode = (LinearNode) node;
                linearNode.setNext(nodesMap.get(nextIndex).getNode());
                continue;
            }
            if (node.isForking()) {
                int nextIndexYes = entry.getNextIndexYes();
                int nextIndexNo = entry.getNextIndexNo();
                ForkingNode forkingNode = (ForkingNode) node;
                forkingNode.setNextYes(nodesMap.get(nextIndexYes).getNode());
                forkingNode.setNextNo(nodesMap.get(nextIndexNo).getNode());

            }
        }
    }

    private void validate(Map<Integer, Entry> nodesMap) {
        // step 1 - check if number of roots exactly equals 1
        Set<Integer> nonRootEntries = new HashSet();

        for (Entry entry : nodesMap.values()) {
            if (entry.getNode().isLinear()) {
                nonRootEntries.add(entry.getNextIndex());
            }
            if (entry.getNode().isForking()) {
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

        // step 2 - check if cycles present

//        Set<Integer> visitedEntries = new HashSet<>();
//        int currentIndex = rootIndex;
//        while (currentIndex != 0 && !visitedEntries.contains(currentIndex)) {
//            visitedEntries.add(currentIndex);
//            currentIndex = nodesMap.get(currentIndex).getNextIndex();
//        }
//        if (currentIndex != 0) {
//            valid = false;
//            return;
//        }
        valid = true;
    }

    private class Entry {
        private Node node;
        private int nextIndex;
        private int nextIndexYes;
        private int nextIndexNo;

        public Entry(Node node, int nextIndex) {
            this.node = node;
            this.nextIndex = nextIndex;
        }

        public Entry(Node node, int nextIndexYes, int nextIndexNo) {
            this.node = node;
            this.nextIndexYes = nextIndexYes;
            this.nextIndexNo = nextIndexNo;
        }

        public Node getNode() {
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
