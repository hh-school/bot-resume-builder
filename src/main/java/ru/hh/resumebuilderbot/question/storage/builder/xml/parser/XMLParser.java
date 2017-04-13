package ru.hh.resumebuilderbot.question.storage.builder.xml.parser;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class XMLParser {
    public List<XMLEntry> parse(String filename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(filename);
        Node root = document.getDocumentElement();
        NodeList questions = root.getChildNodes();

        List<XMLEntry> result = new ArrayList<>();

        XMLNodeListStream.fromNodeList(questions).forEach((x) -> result.add(makeEntry(x)));

        return result;
    }

    private XMLEntry makeEntry(Node question) {
        NamedNodeMap attributes = question.getAttributes();
        int id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());
        Optional<Node> attributeRoot = Optional.ofNullable(attributes.getNamedItem("root"));
        boolean isRoot = attributeRoot.isPresent() ?
                Boolean.parseBoolean(attributeRoot.get().getNodeValue()) : false;
        String type = attributes.getNamedItem("type").getNodeValue();

        if (type.equals("terminal")) {
            XMLEntry entry = new XMLEntry(id);
            entry.setRoot(isRoot);
            return entry;
        }

        String text = attributes.getNamedItem("text").getNodeValue();

        List<String> allowedAnswers = new ArrayList<>();
        NodeList allowedAnswerNodes = question.getChildNodes();

        XMLNodeListStream.fromNodeList(allowedAnswerNodes).forEach((x) ->
                allowedAnswers.add(x.getAttributes().getNamedItem("text").getNodeValue()));

        if (type.equals("linear")) {
            int nextId = Integer.parseInt(attributes.getNamedItem("next").getNodeValue());
            XMLEntry entry = new XMLEntry(id, nextId, text, allowedAnswers);
            entry.setRoot(isRoot);
            return entry;
        }
        String pattern = attributes.getNamedItem("pattern").getNodeValue();
        int nextYes = 0;
        int nextNo = 0;
        if (type.equals("forking")) {
            nextYes = Integer.parseInt(attributes.getNamedItem("nextYes").getNodeValue());
            nextNo = Integer.parseInt(attributes.getNamedItem("nextNo").getNodeValue());
        } else {
            nextYes = Integer.parseInt(attributes.getNamedItem("nextIn").getNodeValue());
            nextNo = Integer.parseInt(attributes.getNamedItem("nextOut").getNodeValue());
        }
        XMLEntry entry = new XMLEntry(type, id, text, allowedAnswers, pattern, nextYes, nextNo);
        entry.setRoot(isRoot);
        return entry;

    }
}
