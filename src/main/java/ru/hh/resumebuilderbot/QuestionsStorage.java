package ru.hh.resumebuilderbot;

import java.util.*;

public class QuestionsStorage {
    private static final QuestionsStorage instance = new QuestionsStorage();

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

    private List<String> questionTexts = Collections.synchronizedList(new ArrayList<String>());
    private Map<Integer, List<String>> allowedAnswers = Collections.synchronizedMap(new HashMap<>());

    private QuestionsStorage() {
    }

    public static String getQuestion(int questionId) {
        return instance.questionTexts.get(questionId);
    }

    public static List<String> getAllowedAnswers(int questionId) {
        return instance.allowedAnswers.get(questionId);
    }

    public static boolean allowedAnswersPresent(int questionId) {
        return instance.allowedAnswers.containsKey(questionId);
    }

    public static boolean finished(int questionId) {
        int currentSize = instance.questionTexts.size();
        return currentSize <= questionId;
    }
    //end hardcode

    private static void registerQuestion(String text, List<String> allowedAnswers) {
        instance.questionTexts.add(text);
        if (allowedAnswers != null) {
            int currentSize = instance.questionTexts.size();
            instance.allowedAnswers.put(currentSize - 1, allowedAnswers);
        }
    }

    private static void registerQuestion(String text) {
        registerQuestion(text, null);
    }

}
