package ru.hh.resumebuilderbot.question.storage;

import ru.hh.resumebuilderbot.question.storage.builder.QuestionsLoader;
import ru.hh.resumebuilderbot.question.storage.node.Node;

public class QuestionsStorage {

    private static Node questionsAlgorithmRoot;

    //end hardcode

    //hardcode
    static {
        // нужны конфиги - из них читать путь к файлу XML
        QuestionsLoader questionsLoader = new QuestionsLoader();
        questionsAlgorithmRoot = questionsLoader.load("src/main/resources/questions.xml");
    }

    private QuestionsStorage() {
    }

    public static Node getRoot() {
        return questionsAlgorithmRoot;
    }

}
