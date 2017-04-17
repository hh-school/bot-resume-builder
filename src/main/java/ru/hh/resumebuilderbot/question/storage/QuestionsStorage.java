package ru.hh.resumebuilderbot.question.storage;

import ru.hh.resumebuilderbot.question.storage.builder.Graph;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;
import ru.hh.resumebuilderbot.user.data.storage.UserData;

import java.io.IOException;

import static ru.hh.resumebuilderbot.question.storage.builder.Graph.fromXMLFile;

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

    public static QuestionNode getRoot() {
        Graph graphNew = graphSample.cloneContent();
        return graphNew.getRoot();
    }

}
