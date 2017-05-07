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
import java.util.stream.Collectors;

public class XMLGlobalParser {

    private static final Logger log = LoggerFactory.getLogger(XMLGlobalParser.class);

    public static XMLRawData parse(String filename) throws IOException {

        try {
            DocumentBuilder documentBuilder = createDocumentBuilder();
            Document document = buildDocument(documentBuilder, filename);

            Node root = document.getDocumentElement();

            int rootIndex = extractRootIndex(root);

            List<XMLEntry> entriesList = new ArrayList<>();

            for (Node graphNode : XMLAsStream.fromParentNode(root).collect(Collectors.toSet())) {
                entriesList.add(XMLEntry.fromGraphNode(graphNode));
            }

            return new XMLRawData(rootIndex, entriesList);
        } catch (IOException e) {
            throw new IOException("Error parsing XML file", e);
        }
    }

    private static int extractRootIndex(Node rootNode) throws IOException {
        try {
            return Integer.parseInt(rootNode.getAttributes().getNamedItem("root").getNodeValue());
        } catch (NullPointerException e) {
            throw new IOException("No rootIndex attribute");
        }
    }

    private static DocumentBuilder createDocumentBuilder() throws IOException {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new IOException("Error instantiating DocumentBuilder");
        }
    }

    private static Document buildDocument(DocumentBuilder builder, String filename) throws IOException {
        try {
            return builder.parse(filename);
        } catch (SAXException e) {
            throw new IOException("SAX exception. Maybe XML is not valid");
        } catch (IOException e) {
            throw new IOException("Can't read XML file");
        }
    }
}
