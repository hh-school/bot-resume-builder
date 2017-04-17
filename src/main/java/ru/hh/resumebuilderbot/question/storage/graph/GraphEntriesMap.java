package ru.hh.resumebuilderbot.question.storage.graph;

import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLEntry;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLRawData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GraphEntriesMap {
    private Map<Integer, GraphEntry> entriesMap;

    private GraphEntriesMap(Map<Integer, GraphEntry> entriesMap) {
        this.entriesMap = entriesMap;
    }

    static GraphEntriesMap fromXMLRawData(XMLRawData rawData) throws IOException {

        Map<Integer, GraphEntry> entriesMap = new HashMap<>();

        for (XMLEntry xmlEntry : rawData.getEntriesList()) {
            try {
                entriesMap.put(xmlEntry.getIndex(), GraphEntry.fromXMLEntry(xmlEntry));
            } catch (IOException e) {
                throw new IOException("Failed to make graph entry with index = " + xmlEntry.getIndex(), e);
            }
        }

        return new GraphEntriesMap(entriesMap);
    }

    void linkNodes() {
        Map<Integer, QuestionNode> nodesMap = new HashMap<>();
        entriesMap.forEach((key, value) -> nodesMap.put(key, value.getNode()));
        entriesMap.values().forEach(x -> x.setLinks(nodesMap));
    }

    protected GraphEntriesMap clone() {

        return new GraphEntriesMap(entriesMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry<Integer, GraphEntry>::getKey,
                        entry -> entry.getValue().cloneContent()
                )));
    }

    QuestionNode getNode(int index) {
        return entriesMap.get(index).getNode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GraphEntriesMap that = (GraphEntriesMap) o;
        return Objects.equals(entriesMap, that.entriesMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entriesMap);
    }
}
