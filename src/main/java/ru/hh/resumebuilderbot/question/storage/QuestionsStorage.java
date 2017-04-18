package ru.hh.resumebuilderbot.question.storage;

import ru.hh.resumebuilderbot.question.storage.graph.Graph;

import java.io.IOException;

import static ru.hh.resumebuilderbot.question.storage.graph.Graph.fromXMLFile;

public class QuestionsStorage {

    private static final String XMLFilename = "src/main/resources/questions.xml";

    private static Graph graphSample;

    static {
        // нужны конфиги - из них читать путь к файлу XML
        try {
            graphSample = fromXMLFile(XMLFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private QuestionsStorage() {
    }

    public static Graph cloneSampleGraph() {
        return graphSample.cloneContent();
    }

}
