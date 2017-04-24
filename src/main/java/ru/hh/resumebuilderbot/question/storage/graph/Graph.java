package ru.hh.resumebuilderbot.question.storage.graph;

import ru.hh.resumebuilderbot.question.storage.graph.node.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLEntry;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLParser;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLRawData;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Graph {
    private Map<Integer, GraphEntry> entriesMap;

    private int rootIndex;

    private Graph(XMLRawData rawData) {
        XMLValidator.validate(rawData);
        entriesMap = makeEntriesMap(rawData.getEntriesList());
        setRoot(rawData);
        linkNodes();
    }

    private Graph(int rootIndex, Map<Integer, GraphEntry> entriesMap) {
        this.entriesMap = new HashMap<>();
        this.rootIndex = rootIndex;
        for (Map.Entry<Integer, GraphEntry> entry : entriesMap.entrySet()) {
            GraphEntry newEntry = entry.getValue().cloneContent();
            this.entriesMap.put(entry.getKey(), newEntry);
        }
    }

    public static Graph fromXMLFile(String filename) {
        return new Graph(XMLParser.parse(filename));
    }

    public QuestionNode getRoot() {
        return entriesMap.get(rootIndex).getNode();
    }

    private void setRoot(XMLRawData rawData) {
        rootIndex = rawData.getRootIndex();
    }

    private Map<Integer, GraphEntry> makeEntriesMap(List<XMLEntry> rawData) {
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
        Graph result = new Graph(rootIndex, entriesMap);
        result.linkNodes();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Graph that = (Graph) o;
        boolean result = rootIndex == that.rootIndex &&
                entriesMap.equals(that.entriesMap);
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entriesMap, rootIndex);
    }
}