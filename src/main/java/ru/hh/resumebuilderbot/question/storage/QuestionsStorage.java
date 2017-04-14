package ru.hh.resumebuilderbot.question.storage;

import ru.hh.resumebuilderbot.question.storage.builder.NodeSet;
import ru.hh.resumebuilderbot.question.storage.builder.QuestionsLoader;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;

import java.io.IOException;

public class QuestionsStorage {

    private static final String XMLfilename = "src/main/resources/questions.xml";

    private static NodeSet nodeSetSample;

    static {
        // нужны конфиги - из них читать путь к файлу XML
        QuestionsLoader questionsLoader = new QuestionsLoader();
        try {
            nodeSetSample = questionsLoader.load(XMLfilename);
            nodeSetSample.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private QuestionsStorage() {
    }

    public static QuestionNode getRoot() throws IOException {
        NodeSet nodeSetNew = nodeSetSample.cloneContent();
        nodeSetNew.build();
        return nodeSetNew.getRoot();
    }

}
