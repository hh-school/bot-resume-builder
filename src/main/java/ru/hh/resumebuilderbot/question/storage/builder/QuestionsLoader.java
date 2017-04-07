package ru.hh.resumebuilderbot.question.storage.builder;

import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;

import java.io.IOException;
import java.util.List;

public class QuestionsLoader {

    public QuestionNode load(String filename) throws IOException {
        List<XMLParser.Entry> rawData = new XMLParser().parse(filename);
        NodeSet nodeSet = new NodeSet(rawData);
        nodeSet.build();
        if (nodeSet.isValid()) {
            return nodeSet.getRoot();
        }
        throw new IOException("Error building questions graph");
    }
}
