package ru.hh.resumebuilderbot.question.storage.builder;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.node.LinearNode;
import ru.hh.resumebuilderbot.question.storage.node.Node;
import ru.hh.resumebuilderbot.question.storage.node.NonTerminalNode;
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
        result.put(0, new Entry(new TerminalNode(), 0));
        return result;
    }

    private Entry makeEntry(XMLParser.Entry xmlEntry) {
        Question question = new Question(xmlEntry.getText(), xmlEntry.getAllowedAnswers());
        NonTerminalNode nonTerminalNode = new LinearNode(question);
        int nextIndex = xmlEntry.hasNextIndex() ? xmlEntry.getNextIndex() : 0;
        return new Entry(nonTerminalNode, nextIndex);
    }

    private void linkNodes(Map<Integer, Entry> nodesMap) {
        for (Entry entry : nodesMap.values()) {
            Node node = entry.getNode();
            int nextIndex = entry.getNextIndex();
            if (!node.isTerminal()) {
                NonTerminalNode nonTerminalNode = (NonTerminalNode) node;
                if (nonTerminalNode.isLinear()) {
                    LinearNode linearNode = (LinearNode) nonTerminalNode;
                    linearNode.setNext(nodesMap.get(nextIndex).getNode());
                }
            }
        }
    }

    private void validate(Map<Integer, Entry> nodesMap) {
        // step 1 - check if number of roots exactly equals 1
        Set<Integer> nonRootEntries = new HashSet();
        nodesMap.values().forEach((x) -> nonRootEntries.add(x.getNextIndex()));
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

        Set<Integer> visitedEntries = new HashSet<>();
        int currentIndex = rootIndex;
        while (currentIndex != 0 && !visitedEntries.contains(currentIndex)) {
            visitedEntries.add(currentIndex);
            currentIndex = nodesMap.get(currentIndex).getNextIndex();
        }
        if (currentIndex != 0) {
            valid = false;
            return;
        }
        valid = true;
    }

    private class Entry {
        private Node node;
        private int nextIndex;

        public Entry(Node node, int nextIndex) {
            this.node = node;
            this.nextIndex = nextIndex;
        }

        public Node getNode() {
            return node;
        }

        public int getNextIndex() {
            return nextIndex;
        }
    }

}
