package ru.hh.resumebuilderbot.question.storage;

import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.question.storage.graph.node.QuestionNode;

public class QuestionsStorage {

    private static final String XMLFilename = "src/main/resources/questions.xml";

    private static Graph graphSample;

    static {
        // нужны конфиги - из них читать путь к файлу XML
        graphSample = Graph.fromXMLFile(XMLFilename);
    }

    private QuestionsStorage() {
    }

    public static QuestionNode getClonedRoot() {
        return graphSample.cloneContent().getRoot();
    }

}
