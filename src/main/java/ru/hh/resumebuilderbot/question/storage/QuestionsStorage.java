package ru.hh.resumebuilderbot.question.storage;

import java.util.ArrayList;
import java.util.List;

public class QuestionsStorage {

    private static Node questionsAlgorithmRoot;

    //end hardcode

    //hardcode
    static {
        QuestionsAlgorithmBuilder builder = new QuestionsAlgorithmBuilder();
        builder.registerQuestion("Это первый вопрос");
        builder.registerQuestion("Это второй вопрос");

        List<String> answers = new ArrayList<>();
        answers.add("Первый вариант");
        answers.add("Второй вариант");

        builder.registerQuestion("Это третий вопрос. На него есть 2 варианта ответа", answers);
        builder.registerQuestion("Это последний вопрос");

        questionsAlgorithmRoot = builder.build();
    }

    private QuestionsStorage() {
    }

    public static Node getRoot() {
        return questionsAlgorithmRoot;
    }

}
