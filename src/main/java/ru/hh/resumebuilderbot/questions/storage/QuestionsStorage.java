package ru.hh.resumebuilderbot.questions.storage;

import ru.hh.resumebuilderbot.Question;

import java.util.*;

public class QuestionsStorage {

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
	
    private static List<Question> questions = Collections.synchronizedList(new ArrayList<>());

    private QuestionsStorage() {
    }
	
    public static Question getQuestion(int questionId) {
        return questions.get(questionId);
    }

    public static boolean finished(int questionId) {
        int currentSize = questions.size();
        return currentSize <= questionId;
    }

    private static void registerQuestion(String text, List<String> allowedAnswers) {
        questionTexts.add(text);
        int currentSize = questionTexts.size();
        QuestionsStorage.allowedAnswers.put(currentSize - 1, allowedAnswers);
        questions.add(new Question(text, allowedAnswers));
    }

    private static void registerQuestion(String text) {
        questionTexts.add(text);
    }

}
