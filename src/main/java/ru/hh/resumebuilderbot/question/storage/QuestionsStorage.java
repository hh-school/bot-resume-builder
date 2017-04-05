package ru.hh.resumebuilderbot.question.storage;

import ru.hh.resumebuilderbot.question.storage.builder.QuestionsLoader;
import ru.hh.resumebuilderbot.question.storage.node.Node;

import java.io.IOException;

public class QuestionsStorage {

    private static Node questionsAlgorithmRoot;

    //end hardcode

    //hardcode
    static {
        // нужны конфиги - из них читать путь к файлу XML
        QuestionsLoader questionsLoader = new QuestionsLoader();
        try {
            questionsAlgorithmRoot = questionsLoader.load("src/main/resources/questions.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private QuestionsStorage() {
    }

    public static Node getRoot() {
        return questionsAlgorithmRoot;
    }

}
