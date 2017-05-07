package ru.hh.resumebuilderbot.question.storage.graph;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLGlobalParser;
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
        try {
            entriesMap = GraphEntriesMap.fromXMLRawData(rawData);
        } catch (IOException e) {
            throw new IOException("Failed to build entries map", e);
        }
        setRoot(rawData);
        entriesMap.linkNodes();
    }

    private Graph(int rootIndex, GraphEntriesMap entriesMap) {
        this.rootIndex = rootIndex;
        this.entriesMap = entriesMap;
        entriesMap.linkNodes();
    }

    public static Graph fromXMLFile(String filename) throws IOException {
        return new Graph(XMLGlobalParser.parse(filename));
    }

    public QuestionNode getRoot() {
        return entriesMap.getNode(rootIndex);
    }

    private void setRoot(XMLRawData rawData) {
        rootIndex = rawData.getRootIndex();
    }

    public int getRootIndex() {
        return rootIndex;
    }

    public QuestionNode getNode(int index) {
        return entriesMap.getNode(index);
    }

    public int getNextNodeIndex(int index, Answer answer) {
        QuestionNode questionNode = getNode(index);
        return questionNode.getNextIndex(answer);
    }

    public Graph cloneContent() {
        return new Graph(rootIndex, entriesMap.clone());
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
        return rootIndex == that.rootIndex &&
                Objects.equals(entriesMap, that.entriesMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entriesMap, rootIndex);
    }
}
