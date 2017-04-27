package ru.hh.resumebuilderbot.question.storage.graph.xml.parser;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class XMLAsStream {
    private static Stream<Node> fromNodeList(NodeList nodeList) {
        return IntStream.range(0, nodeList.getLength())
                .mapToObj(nodeList::item)
                .filter((x) -> (x.getNodeType() != Node.TEXT_NODE));
    }

    public static Stream<Node> fromParentNode(Node node) {
        return fromNodeList(node.getChildNodes());
    }

    public static Optional<Node> getFirstChildByName(Node node, String name) {
        return XMLAsStream.fromParentNode(node)
                .filter(x -> x.getNodeName().equals(name))
                .findFirst();
    }
}
