package ru.hh.resumebuilderbot.question.storage;

import ru.hh.resumebuilderbot.question.storage.builder.NodeSet;
import ru.hh.resumebuilderbot.question.storage.builder.QuestionsLoader;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;

import java.io.IOException;

public class QuestionsStorage {

    private static NodeSet nodeSetSample;

    static {
        // нужны конфиги - из них читать путь к файлу XML
        QuestionsLoader questionsLoader = new QuestionsLoader();
        try {
            nodeSetSample = questionsLoader.load("src/main/resources/questions.xml");
            nodeSetSample.build();
            if (!nodeSetSample.isValid()) {
                throw new IOException("Error building questions graph");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private QuestionsStorage() {
    }

    public static QuestionNode getRoot() throws IOException {
        NodeSet nodeSetNew = nodeSetSample.cloneContent();
        nodeSetNew.build();
        if (nodeSetNew.isValid()) {
            return nodeSetSample.getRoot();
        }
        throw new IOException("Error building questions graph");
    }

}
