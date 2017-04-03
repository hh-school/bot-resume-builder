package ru.hh.resumebuilderbot.question.storage;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

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

    public static Question getFirstQuestion() {
        return questionsAlgorithmRoot.getQuestion();
    }

    public static Node getRoot() {
        return questionsAlgorithmRoot;
    }

    public static boolean finished(ChatId chatId) {
        return UserDataStorage.isLastNode(chatId);
    }

    public static Question getNextQuestion(ChatId chatId) {
        return UserDataStorage.getNextQuestion(chatId);
    }

}
