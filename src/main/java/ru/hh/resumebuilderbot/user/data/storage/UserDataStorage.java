package ru.hh.resumebuilderbot.user.data.storage;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.database.ServiceAggregator;
import ru.hh.resumebuilderbot.question.Question;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class UserDataStorage {
    private final ServiceAggregator serviceAggregator;
    private Map<User, UserData> userDataMap = new ConcurrentHashMap<>();

    @Inject
    private UserDataStorage(ServiceAggregator serviceAggregator) {
        this.serviceAggregator = serviceAggregator;
    }

    public boolean contains(User user) {
        return userDataMap.containsKey(user);
    }

    public void clear(User user) {
        userDataMap.put(user, new UserData());
    }

    public void startNewChat(User user) {
        userDataMap.put(user, new UserData());
    }

    public void registerAnswer(User user, Answer answer) {
        UserData userData = getUserData(user);
        userData.registerAnswer(answer);
    }

    public Object getMutex(User user) {
        if (!contains(user)) {
            userDataMap.put(user, new UserData());
        }
        return getUserData(user);
    }

    public Question getCurrentQuestion(User user) {
        return getUserData(user).getCurrentQuestion();
    }

    public void moveForward(User user) {
        UserData userData = getUserData(user);
        userData.moveForward();
    }

    public boolean answerIsValid(User user, Answer answer) {
        return getUserData(user).answerIsValid(answer);
    }

    public boolean currentNodeIsSkippable(User user) {
        return getUserData(user).currentNodeIsSkippable();
    }

    private UserData getUserData(User user) {
        return userDataMap.get(user);
    }
}
