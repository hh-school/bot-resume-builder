package ru.hh.resumebuilderbot.question.storage.builder;

import java.io.IOException;
import java.util.List;

public class QuestionsLoader {

    public NodeSet load(String filename) throws IOException {
        List<XMLParser.Entry> rawData = new XMLParser().parse(filename);
        NodeSet result = new NodeSet(rawData);
        return result;
    }
}
