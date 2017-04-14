package ru.hh.resumebuilderbot.question.storage.builder;

import org.xml.sax.SAXException;
import ru.hh.resumebuilderbot.question.storage.builder.xml.parser.XMLEntry;
import ru.hh.resumebuilderbot.question.storage.builder.xml.parser.XMLParser;
import ru.hh.resumebuilderbot.question.storage.builder.xml.parser.XMLValidator;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeForking;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNodeLinear;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeSet {
    private Map<Integer, NodeSetEntry> nodesMap;

    private QuestionNode root;

    private NodeSet(List<XMLEntry> rawData) throws IOException {
        XMLValidator.validate(rawData);
        nodesMap = makeNodes(rawData);
        setRoot(rawData);
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

    static NodeSet fromXMLFile(String filename) throws IOException {
        try {
            List<XMLEntry> rawData = new XMLParser().parse(filename);
            return new NodeSet(rawData);
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Error parsing XML: internal error");
        }
    }

    public QuestionNode getRoot() {
        return root;
    }

    private void setRoot(List<XMLEntry> rawData) {
        int rootIndex = rawData.stream()
                .filter((x) -> x.isRoot())
                .findFirst().get().getIndex();
        root = nodesMap.get(rootIndex).getNode();
    }

    public void build() {
        linkNodes(nodesMap);
    }

    private Map<Integer, NodeSetEntry> makeNodes(List<XMLEntry> rawData) {
        Map<Integer, NodeSetEntry> result = new HashMap<>();
        rawData.forEach((x) -> result.put(x.getIndex(), NodeSetEntry.fromXMLEntry(x)));
        return result;
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
