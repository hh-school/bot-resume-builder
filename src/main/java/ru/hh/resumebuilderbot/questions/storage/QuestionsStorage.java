package ru.hh.resumebuilderbot.questions.storage;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;
import ru.hh.resumebuilderbot.UserDataStorage;

import java.util.*;

public class QuestionsStorage {

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

        instance.questionsAlgorithmRoot = builder.build();
    }

    //end hardcode


    private static Node questionsAlgorithmRoot;

    private QuestionsStorage() {
    }
	
    public static Question getFirstQuestion()
    {
        return questionsAlgorithmRoot.getQuestion();
    }

    public static Node getRoot()
    {
		return questionsAlgorithmRoot;
	}
	
	public static boolean finished(ChatId chatId) {
        return UserDataStorage.isLastNode(chatId);
	}
	
    public static Question getNextQuestion(ChatId chatId)
    {
        return UserDataStorage.getNextQuestion(chatId);
    }

}
