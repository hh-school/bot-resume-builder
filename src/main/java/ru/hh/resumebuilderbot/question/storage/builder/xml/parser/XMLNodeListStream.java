package ru.hh.resumebuilderbot.question.storage.builder.xml.parser;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.stream.IntStream;
import java.util.stream.Stream;

class XMLNodeListStream {
    static Stream<Node> fromNodeList(NodeList nodeList) {
        return IntStream.range(0, nodeList.getLength())
                .mapToObj(nodeList::item)
                .filter((x) -> (x.getNodeType() != Node.TEXT_NODE));
    }
}
