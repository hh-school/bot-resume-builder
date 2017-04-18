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

    public List<XMLEntry> parse(String filename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(filename);
        Node root = document.getDocumentElement();

        List<XMLEntry> result = new ArrayList<>();

        XMLNodeListStream.fromParentNode(root).forEach((x) -> {
            try {
                result.add(XMLEntry.fromGraphNode(x));
            } catch (IOException e) {
                log.error("Error parsing XML");
            }
        });

        return result;
    }
}
