package ru.hh.resumebuilderbot.user.data.storage;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.question.Question;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataStorage {
    private static final UserDataStorage instance = new UserDataStorage();
    private Map<User, UserData> userDataMap = new ConcurrentHashMap<>();

    private UserDataStorage() {
    }

    public static boolean contains(User user) {
        return instance.userDataMap.containsKey(user);
    }

    public static void clear(User user) {
        instance.userDataMap.put(user, new UserData());
    }

    public static void startNewChat(User user) {
        instance.userDataMap.put(user, new UserData());
    }

    public static void registerAnswer(User user, Answer answer) {
        String answerText = answer.getAnswerBody().toString();
        UserData userData = instance.userDataMap.get(user);
        userData.registerAnswer(getCurrentQuestion(user), answerText);
        userData.moveToNextQuestion((String) answer.getAnswerBody());
    }

    public static List<UserAnswer> getHistory(User user) {
        return instance.userDataMap.get(user).getAnswers();
    }

    public static Object getMutex(User user) {
        if (!contains(user)) {
            instance.userDataMap.put(user, new UserData());
        }
        return instance.userDataMap.get(user);
    }

    public static boolean finished(User user) {
        return instance.userDataMap.get(user).getCurrentState().isLastQuestion();
    }

    public static Question getCurrentQuestion(User user) {
        return instance.userDataMap.get(user).getCurrentState().getCurrentQuestion();
    }
}
