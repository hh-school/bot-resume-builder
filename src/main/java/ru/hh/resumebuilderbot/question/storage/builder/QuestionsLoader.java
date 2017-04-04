package ru.hh.resumebuilderbot.question.storage.builder;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.node.Node;

import java.util.ArrayList;
import java.util.List;

public class QuestionsLoader {
    private List<Question> questions = new ArrayList<>();

    public Node load(String filename) {
        List<XMLParser.Entry> rawData = new XMLParser().parse(filename);
        NodeSet nodeSet = new NodeSet(rawData);
        nodeSet.build();
        if (nodeSet.isValid()) {
            return nodeSet.getRoot();
        }
        return null; //throw exception?
    }
}
