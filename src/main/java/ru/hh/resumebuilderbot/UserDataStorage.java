package ru.hh.resumebuilderbot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataStorage {
    private static UserDataStorage instance = new UserDataStorage();

    private UserDataStorage(){};

    private Map<ChatId, UserData> userDataMap = new ConcurrentHashMap<>();

    public static boolean contains(ChatId chatId)
    {
        return instance.userDataMap.containsKey(chatId);
    }

    public static void clear(ChatId chatId)
    {
        instance.userDataMap.put(chatId, new UserData());
    }

    public static void registerAnswer(Answer answer)
    {
        ChatId chatId = answer.getChatId();
        String answerText = answer.getAnswerBody().toString();
        UserData userData = instance.userDataMap.get(chatId);
        synchronized (userData)
        {
            int currentQuestionNumber = userData.getCurrentState().getCurrentQuestion();
            String currentQuestionText = QuestionsStorage.getQuestion(currentQuestionNumber);
            userData.registerAnswer(currentQuestionText, answerText);
            userData.incrementCurrentQuestion();
        }
    }

    public static CurrentUserState getCurrentState(ChatId chatId)
    {
        return instance.userDataMap.get(chatId).getCurrentState();
    }
}
