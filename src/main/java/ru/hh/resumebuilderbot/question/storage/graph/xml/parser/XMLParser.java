package ru.hh.resumebuilderbot.question.storage.graph.xml.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    private final static Logger log = LoggerFactory.getLogger(XMLParser.class);

    public static XMLRawData parse(String filename) {
        DocumentBuilder documentBuilder = createDocumentBuilder();
        Document document = buildDocument(documentBuilder, filename);

        Node root = document.getDocumentElement();

        int rootIndex = extractRootIndex(root);

        List<XMLEntry> entriesList = new ArrayList<>();

        XMLNodeListStream.fromParentNode(root).forEach((x) -> entriesList.add(XMLEntry.fromGraphNode(x)));

        return new XMLRawData(rootIndex, entriesList);
    }

    private static int extractRootIndex(Node rootNode) {
        try {
            return Integer.parseInt(rootNode.getAttributes().getNamedItem("root").getNodeValue());
        } catch (NullPointerException e) {
            log.error("Error: wrong XML schema - no rootIndex attribute");
            throw new RuntimeException("Error: wrong XML schema - no rootIndex attribute");
        }
    }

    private static DocumentBuilder createDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error("Error instantiating DocumentBuilder");
            throw new RuntimeException("Error instantiating DocumentBuilder");
        }
    }

    private static Document buildDocument(DocumentBuilder builder, String filename) {
        try {
            return builder.parse(filename);
        } catch (SAXException e) {
            log.error("Error parsing XML. Maybe XML is not valid");
            throw new RuntimeException("Error parsing XML. Maybe XML is not valid");
        } catch (IOException e) {
            log.error("Error: can't read XML file");
            throw new RuntimeException("Error: can't read XML file");
        }
    }
}