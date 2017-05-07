package ru.hh.resumebuilderbot.question.storage.graph.xml.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XMLEntry {

    private static final boolean isSkippableByDefault = true;

    private static final Logger log = LoggerFactory.getLogger(XMLEntry.class);

    private int index;
    private Map<String, Map<String, Object>> constructorData;

    private Map<String, Integer> links;

    private XMLEntry(int index, Map<String, Integer> links, Map<String, Map<String, Object>> constructorData) {
        this.index = index;
        this.links = links;
        this.constructorData = constructorData;
    }

    static XMLEntry fromGraphNode(Node graphNode) throws IOException {
        try {
            int id = Integer.parseInt(getRequiredAttribute(graphNode, "id"));

            Map<String, Map<String, Object>> nodeConstructorData = new HashMap<>();

            Map<String, Integer> links = XMLAsStream.getFirstChildByName(graphNode, "links")
                    .map(XMLEntry::parseLinks).orElse(Collections.EMPTY_MAP);

            Node nodeConstructorNode = getRequiredChild(graphNode, "nodeConstructor");

            Node baseNode = getRequiredChild(nodeConstructorNode, "base");

            nodeConstructorData.put("base", buildConstructorDataElement(baseNode));

            Map<String, Object> validatorData = parseOptionalConstructorElement(nodeConstructorNode,
                    "validator");
            if (validatorData != null) {
                nodeConstructorData.put("validator", validatorData);
            }

            Map<String, Object> saverData = parseOptionalConstructorElement(nodeConstructorNode,
                    "saver");
            if (saverData != null) {
                nodeConstructorData.put("saver", saverData);
            }
            XMLEntry entry = new XMLEntry(id, links, nodeConstructorData);
            return entry;
        } catch (IOException e) {
            throw new IOException("Faied to parse graph node", e);
        }
    }

    private static Map<String, Object> parseOptionalConstructorElement(Node parentNode, String elementName)
            throws IOException {
        Optional<Node> optionalConstructorElementNode =
                XMLAsStream.getFirstChildByName(parentNode, elementName);
        if (!optionalConstructorElementNode.isPresent()) {
            return null;
        }
        Node constructorElementNode = optionalConstructorElementNode.get();

        return buildConstructorDataElement(constructorElementNode);
    }

    private static Map<String, Object> buildConstructorDataElement(Node constructorElementNode) throws IOException {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("classpath", getRequiredAttribute(constructorElementNode, "classpath"));
            result.putAll(XMLClassDataParser.parseClassData(constructorElementNode));

            return result;
        } catch (IOException e) {
            throw new IOException("Cannot build constructor data element: " +
                    constructorElementNode.getNodeName(), e);
        }
    }

    private static Node getRequiredChild(Node parent, String name) throws IOException {
        Optional<Node> optionalChild = XMLAsStream
                .getFirstChildByName(parent, name);
        return optionalChild.orElseThrow(() -> new IOException("No <" + name + "> tag found"));
    }

    private static String getRequiredAttribute(Node node, String attrName) throws IOException {

        try {
            return node.getAttributes().getNamedItem(attrName).getNodeValue();
        } catch (NullPointerException e) {
            throw new IOException("No " + attrName + " tag found in " + node.getNodeName() + " node");
        }
    }

    private static Map<String, Integer> parseLinks(Node linksNode) {
        if (linksNode == null) {
            return Collections.EMPTY_MAP;
        }
        Stream<Node> linksStream = XMLAsStream.fromParentNode(linksNode);
        return linksStream.map(Node::getAttributes)
                .collect(Collectors.toMap(
                        x -> x.getNamedItem("name").getNodeValue(),
                        x -> Integer.parseInt(x.getNamedItem("value").getNodeValue())));
    }

    public Map<String, Integer> getLinks() {
        return links;
    }

    public int getIndex() {
        return index;
    }

    public Map<String, Map<String, Object>> getConstructorData() {
        return constructorData;
    }
}
