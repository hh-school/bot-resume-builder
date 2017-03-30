package ru.hh.resumebuilderbot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataStorage {
    private static final UserDataStorage instance = new UserDataStorage();

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

    public static String showAll(ChatId chatId)
    {
        return instance.userDataMap.get(chatId).buildResume();
    }

    public static void startNewChat(ChatId chatId)
    {
        instance.userDataMap.put(chatId, new UserData());
    }

    public static CurrentUserState registerAnswer(Answer answer)
    {
        ChatId chatId = answer.getChatId();
        String answerText = answer.getAnswerBody().toString();
        CurrentUserState currentUserState;
        UserData userData = instance.userDataMap.get(chatId);
        synchronized (userData)
        {
            int currentQuestionNumber = userData.getCurrentState().getCurrentQuestion();
            userData.registerAnswer(currentQuestionNumber, answerText);
            userData.incrementCurrentQuestion();
            currentUserState = userData.getCurrentState();
        }
        return currentUserState;
    }
}
