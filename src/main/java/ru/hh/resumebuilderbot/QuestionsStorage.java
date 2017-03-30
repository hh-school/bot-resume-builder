package ru.hh.resumebuilderbot;

import java.util.*;

public class QuestionsStorage {
    private static final QuestionsStorage instance = new QuestionsStorage();

    public static Map<Integer,String> dictionary = new HashMap<>();

    private QuestionsStorage() {};

    private List<String> questionTexts = Collections.synchronizedList(new ArrayList<String>());

    public static String getQuestion(int questionId)
    {
        int currentSize = instance.questionTexts.size();
        if (currentSize <= questionId)
        {
            return instance.questionTexts.get(currentSize - 1);
        }
        return instance.questionTexts.get(questionId);
    }

    //hardcode
    static
    {
        instance.questionTexts.add("Введите ваше имя");
        dictionary.put(0, "Имя");
        instance.questionTexts.add("Введите вашу фамилию");
        dictionary.put(1, "Фамилия");
        instance.questionTexts.add("Введите ваш телефон");
        dictionary.put(2, "Телефон");
        instance.questionTexts.add("Введите вашу электронную почту");
        dictionary.put(3, "Электронная почта");
        instance.questionTexts.add("Введите желаемую должность");
        dictionary.put(4, "Должность");
        instance.questionTexts.add("Ваше резюме готово! Чтобы посмотреть результат, воспользуйтесь командой /show ." +
                "Спасибо за то, что пользуетесь нашим ботом.");
    }
    //end hardcode
}
