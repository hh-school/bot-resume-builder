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
import java.util.Objects;

public class Graph {
    private Map<Integer, GraphEntry> entriesMap;

    private int rootIndex;

    private Graph(List<XMLEntry> rawData) throws IOException {
        XMLValidator.validate(rawData);
        entriesMap = makeEntriesMap(rawData);
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

    public static Graph fromXMLFile(String filename) throws IOException {
        try {
            List<XMLEntry> rawData = new XMLParser().parse(filename);
            return new Graph(rawData);
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Error parsing XML: internal error");
        }
    }

    public QuestionNode getRoot() {
        return entriesMap.get(rootIndex).getNode();
    }

    private void setRoot(List<XMLEntry> rawData) {
        rootIndex = rawData.stream()
                .filter(XMLEntry::isRoot)
                .findFirst().get().getIndex();
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
                entriesMap.keySet().containsAll(that.entriesMap.keySet()) &&
                that.entriesMap.keySet().containsAll(entriesMap.keySet());
        for (Map.Entry<Integer, GraphEntry> mapEntry : entriesMap.entrySet()) {
            result = result && Objects.equals(mapEntry.getValue(), that.entriesMap.get(mapEntry.getKey()));
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entriesMap, rootIndex);
    }
}
