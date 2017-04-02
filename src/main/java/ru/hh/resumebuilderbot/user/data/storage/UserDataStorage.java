package ru.hh.resumebuilderbot.user.data.storage;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.CurrentUserState;
import ru.hh.resumebuilderbot.QuestionsStorage;

import ru.hh.resumebuilderbot.questions.storage.QuestionsStorage;

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

    public static void registerAnswer(ChatId chatId, Answer answer) {
        String answerText = answer.getAnswerBody().toString();
        UserData userData = instance.userDataMap.get(chatId);
        String currentQuestionText = QuestionsStorage.getNextQuestion(chatId).getText();
        userData.registerAnswer(currentQuestionText, answerText);
        userData.incrementCurrentQuestion();
    }

    public static List<UserAnswer> getHistory(ChatId chatId) {
        return instance.userDataMap.get(chatId).getAnswers();
    }

    public static Object getMutex(ChatId chatId)
    {
        if (!contains(chatId))
        {
            instance.userDataMap.put(chatId, new UserData());
        }
        return instance.userDataMap.get(chatId);
    }

    public static boolean isLastNode(ChatId chatId)
    {
        return instance.userDataMap.get(chatId).getCurrentState().isLastQuestion();
    }

    public static Question getNextQuestion(ChatId chatId)
    {
        return instance.userDataMap.get(chatId).getCurrentState().getCurrentQuestion();
    }
}
