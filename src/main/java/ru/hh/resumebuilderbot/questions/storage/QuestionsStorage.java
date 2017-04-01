package ru.hh.resumebuilderbot.questions.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionsStorage {

    private static List<String> questionTexts = Collections.synchronizedList(new ArrayList<String>());
    private static Map<Integer, List<String>> allowedAnswers = Collections.synchronizedMap(new HashMap<>());

    //hardcode
    static {
        registerQuestion("Это первый вопрос");
        registerQuestion("Это второй вопрос");

        List<String> answers = new ArrayList<>();
        answers.add("Первый вариант");
        answers.add("Второй вариант");

        registerQuestion("Это третий вопрос. На него есть 2 варианта ответа", answers);
        registerQuestion("Это последний вопрос");
    }

    //end hardcode

    private QuestionsStorage() {
    }

    public static String getQuestion(int questionId) {
        return questionTexts.get(questionId);
    }

    public static List<String> getAllowedAnswers(int questionId) {
        return allowedAnswers.get(questionId);
    }

    public static boolean allowedAnswersPresent(int questionId) {
        return allowedAnswers.containsKey(questionId);
    }

    public static boolean finished(int questionId) {
        int currentSize = questionTexts.size();
        return currentSize <= questionId;
    }

    private static void registerQuestion(String text, List<String> allowedAnswers) {
        questionTexts.add(text);
        int currentSize = questionTexts.size();
        QuestionsStorage.allowedAnswers.put(currentSize - 1, allowedAnswers);
    }

    private static void registerQuestion(String text) {
        questionTexts.add(text);
    }

}
