package ru.hh.resumebuilderbot.question.storage.builder;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class QuestionsLoader {

    public NodeSet load(String filename) throws IOException {
        try {
            List<XMLParser.Entry> rawData = new XMLParser().parse(filename);
            NodeSet result = new NodeSet(rawData);
            return result;
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Error parsing XML: internal error");
        }
    }
}
