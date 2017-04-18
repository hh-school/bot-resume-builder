package ru.hh.resumebuilderbot.question.storage.graph;

import org.xml.sax.SAXException;
import ru.hh.resumebuilderbot.question.storage.graph.node.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLEntry;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLParser;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLValidator;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Integer, GraphEntry> entriesMap;

    private QuestionNode root;

    private Graph(List<XMLEntry> rawData) throws IOException {
        XMLValidator.validate(rawData);
        entriesMap = makeEntriesMap(rawData);
        setRoot(rawData);
        linkNodes();
    }

    private Graph(QuestionNode root, Map<Integer, GraphEntry> entriesMap) {
        this.entriesMap = new HashMap<>();
        for (Map.Entry<Integer, GraphEntry> entry : entriesMap.entrySet()) {
            GraphEntry newEntry = entry.getValue().cloneContent();
            if (entry.getValue().getNode() == root) {
                this.root = newEntry.getNode();
            }
            this.entriesMap.put(entry.getKey(), newEntry);
        }
    }

    public static Graph fromXMLFile(String filename) throws IOException {
        try {
            List<XMLEntry> rawData = new XMLParser().parse(filename);
            return new Graph(rawData);
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Error parsing XML: internal error");
        }
    }

    public QuestionNode getRoot() {
        return root;
    }

    private void setRoot(List<XMLEntry> rawData) {
        int rootIndex = rawData.stream()
                .filter(XMLEntry::isRoot)
                .findFirst().get().getIndex();
        root = entriesMap.get(rootIndex).getNode();
    }

    private Map<Integer, GraphEntry> makeEntriesMap(List<XMLEntry> rawData) throws IOException {
        Map<Integer, GraphEntry> result = new HashMap<>();
        for (XMLEntry xmlEntry : rawData) {
            result.put(xmlEntry.getIndex(), GraphEntry.fromXMLEntry(xmlEntry));
        }
        return result;
    }

    private void linkNodes() {
        Map<Integer, QuestionNode> nodesMap = new HashMap<>();
        entriesMap.forEach((key, value) -> nodesMap.put(key, value.getNode()));
        entriesMap.values().forEach(x -> x.setLinks(nodesMap));
    }

    public Graph cloneContent() {
        Graph result = new Graph(root, entriesMap);
        result.linkNodes();
        return result;
    }

}
