package ru.hh.resumebuilderbot.question.storage.builder;

import java.io.IOException;

public class QuestionsLoader {

    public NodeSet load(String filename) throws IOException {
        return NodeSet.fromXMLFile(filename);
    }
}
