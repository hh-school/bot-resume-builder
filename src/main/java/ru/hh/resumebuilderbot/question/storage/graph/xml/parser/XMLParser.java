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

    public static List<XMLEntry> parse(String filename) {
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error("Error instantiating DocumentBuilder");
            throw new RuntimeException("Error instantiating DocumentBuilder");
        }
        Document document = null;
        try {
            document = documentBuilder.parse(filename);
        } catch (SAXException e) {
            log.error("Error parsing XML. Maybe XML is not valid");
            throw new RuntimeException("Error parsing XML. Maybe XML is not valid");
        } catch (IOException e) {
            log.error("Error: can't read XML file");
            throw new RuntimeException("Error: can't read XML file");
        }
        Node root = document.getDocumentElement();

        List<XMLEntry> result = new ArrayList<>();

        XMLNodeListStream.fromParentNode(root).forEach((x) -> result.add(XMLEntry.fromGraphNode(x)));

        return result;
    }
}
