package ru.hh.resumebuilderbot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionsStorage {
    private static final QuestionsStorage instance = new QuestionsStorage();

    //hardcode
    static {
        instance.questionTexts.add("Это первый вопрос");
        instance.questionTexts.add("Это второй вопрос");
        instance.questionTexts.add("Это третий вопрос");
        instance.questionTexts.add("Вопросы закончились");
    }
    //end hardcode

    private List<String> questionTexts = Collections.synchronizedList(new ArrayList<String>());

    private QuestionsStorage() {
    }

    public static String getQuestion(int questionId) {
        int currentSize = instance.questionTexts.size();
        if (currentSize <= questionId) {
            return instance.questionTexts.get(currentSize - 1);
        }
        return instance.questionTexts.get(questionId);
    }

    //hardcode
    static
    {
        instance.questionTexts.add("Это первый вопрос");
        instance.questionTexts.add("Это второй вопрос");
        instance.questionTexts.add("Это третий вопрос");
        instance.questionTexts.add("Вопросы закончились");
    }
    //end hardcode
}
