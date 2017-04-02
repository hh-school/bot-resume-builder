package ru.hh.resumebuilderbot.questions.storage;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;
import ru.hh.resumebuilderbot.UserDataStorage;

import java.util.*;

public class QuestionsStorage {
    private static final int FIRST_QUESTION_INDEX = 0;

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
	
    public static Question getFirstQuestion()
    {
        return questions.get(FIRST_QUESTION_INDEX);
    }

    public static Question getNextQuestion(ChatId chatId)
    {
        int questionNumber = UserDataStorage.getCurrentQuestionNumber(chatId);
        return questions.get(questionNumber);
    }

    public static boolean finished(ChatId chatId) {
        int currentSize = questions.size();
        return currentSize <= UserDataStorage.getCurrentQuestionNumber(chatId);
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
