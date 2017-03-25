package ru.hh.resumebuilderbot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QuestionsStorage {
    private static final QuestionsStorage instance = new QuestionsStorage();

    private QuestionsStorage() {};

    private Map<Integer, String> questionTexts = new ConcurrentHashMap<>();

    public static String getQuestion(int questionId)
    {
        return instance.questionTexts.get(questionId);
    }
}
