package ru.hh.resumebuilderbot.question.storage.graph;

import ru.hh.resumebuilderbot.question.storage.graph.node.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLParser;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLRawData;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLValidator;

import java.io.IOException;
import java.util.Objects;

public class Graph {
    private GraphEntriesMap entriesMap;

    private int rootIndex;

    private Graph(XMLRawData rawData) throws IOException {
        try {
            XMLValidator.validate(rawData);
        } catch (IOException e) {
            throw new IOException("XML data validation error", e);
        }
        entriesMap = GraphEntriesMap.fromXMLRawData(rawData);
        setRoot(rawData);
        entriesMap.linkNodes();
    }

    private Graph(int rootIndex, GraphEntriesMap entriesMap) {

        this.rootIndex = rootIndex;
        this.entriesMap = entriesMap;
        entriesMap.linkNodes();
    }

    public static Graph fromXMLFile(String filename) throws IOException {
        return new Graph(XMLParser.parse(filename));
    }

    public QuestionNode getRoot() {
        return entriesMap.getNode(rootIndex);
    }

    private void setRoot(XMLRawData rawData) {
        rootIndex = rawData.getRootIndex();
    }

    public Graph cloneContent() {
        Graph result = new Graph(rootIndex, entriesMap.clone());
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
