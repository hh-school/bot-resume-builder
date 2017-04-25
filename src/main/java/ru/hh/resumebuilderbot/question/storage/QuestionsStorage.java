package ru.hh.resumebuilderbot.question.storage;

import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.question.storage.graph.node.QuestionNode;

import java.io.IOException;

public class QuestionsStorage {

    private static final String XMLFilename = "src/main/resources/questions.xml";

    private static Graph graphSample;

    static {
        // нужны конфиги - из них читать путь к файлу XML
        try {
            graphSample = Graph.fromXMLFile(XMLFilename);
        } catch (IOException e) {
            throw new RuntimeException("Error building graph", e);
        }
    }

    private QuestionsStorage() {
    }

    public static QuestionNode getClonedRoot() {
        return graphSample.cloneContent().getRoot();
    }

}
