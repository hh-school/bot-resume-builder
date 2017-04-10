package ru.hh.resumebuilderbot.user.data.storage;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.CurrentUserState;
import ru.hh.resumebuilderbot.QuestionsStorage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataStorage {
    private static final UserDataStorage instance = new UserDataStorage();
    private Map<ChatId, UserData> userDataMap = new ConcurrentHashMap<>();

    private UserDataStorage() {
    }

    public static boolean contains(ChatId chatId) {
        return instance.userDataMap.containsKey(chatId);
    }

    public static void clear(ChatId chatId) {
        instance.userDataMap.put(chatId, new UserData());
    }

    public static void startNewChat(ChatId chatId) {
        instance.userDataMap.put(chatId, new UserData());
    }

    public static CurrentUserState registerAnswer(Answer answer) {
        ChatId chatId = answer.getChatId();
        String answerText = answer.getAnswerBody().toString();
        CurrentUserState currentUserState;
        UserData userData = instance.userDataMap.get(chatId);
        synchronized (userData) {
            int currentQuestionNumber = userData.getCurrentState().getCurrentQuestion();
            String currentQuestionText = QuestionsStorage.getQuestion(currentQuestionNumber);
            userData.registerAnswer(currentQuestionText, answerText);
            userData.incrementCurrentQuestion();
            currentUserState = userData.getCurrentState();
        }
        return currentUserState;
    }

    public static List<UserAnswer> getHistory(ChatId chatId) {
        return instance.userDataMap.get(chatId).getAnswers();
    }
}