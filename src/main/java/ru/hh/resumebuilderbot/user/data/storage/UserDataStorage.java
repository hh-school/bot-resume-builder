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
        UserData userData = getUserData(user);
        userData.registerAnswer(answer);
    }

    public static List<UserAnswer> getHistory(User user) {
        return getUserData(user).getAnswers();
    }

    public static Object getMutex(User user) {
        if (!contains(user)) {
            instance.userDataMap.put(user, new UserData());
        }
        return getUserData(user);
    }

    public static Question getCurrentQuestion(User user) {
        return getUserData(user).getCurrentQuestion();
    }

    public static void moveForward(User user) {
        UserData userData = getUserData(user);
        userData.moveForward();
    }

    public static boolean answerIsValid(User user, Answer answer) {
        return getUserData(user).answerIsValid(answer);
    }

    private static UserData getUserData(User user) {
        return instance.userDataMap.get(user);
    }
}
