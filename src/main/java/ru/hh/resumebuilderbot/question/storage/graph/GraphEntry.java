package ru.hh.resumebuilderbot.question.storage.graph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.QuestionNodeBuilder;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.graph.xml.parser.XMLEntry;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GraphEntry {
    private static final Logger log = LoggerFactory.getLogger(GraphEntry.class);

    private QuestionNode node;
    private Map<String, Integer> indexLinks;

    private GraphEntry(QuestionNode node, Map<String, Integer> indexLinks) {
        this.node = node;
        this.indexLinks = indexLinks;
    }

    static GraphEntry fromXMLEntry(XMLEntry xmlEntry) throws IOException {
        Map<String, Integer> indexLinks = xmlEntry.getLinks();
        Map<String, Map<String, Object>> constructorData = xmlEntry.getConstructorData();

        QuestionNode questionNode = QuestionNodeBuilder.build(constructorData);

        return new GraphEntry(questionNode, indexLinks);
    }

    GraphEntry cloneContent() {
        Map<String, Integer> clonedIndexLinks = indexLinks.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry<String, Integer>::getKey, Map.Entry<String, Integer>::getValue
                ));
        return new GraphEntry(node.cloneContent(), clonedIndexLinks);
    }

    public QuestionNode getNode() {
        return node;
    }

    void setLinks(Map<Integer, QuestionNode> nodesMap) {
        Map<String, QuestionNode> objectLinks = indexLinks.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry<String, Integer>::getKey, x -> nodesMap.get(x.getValue())));
        node.setLinks(objectLinks, indexLinks);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GraphEntry that = (GraphEntry) o;
        return getNode().hasEqualContent(that.getNode()) &&
                Objects.equals(indexLinks, that.indexLinks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNode(), indexLinks);
    }
}
