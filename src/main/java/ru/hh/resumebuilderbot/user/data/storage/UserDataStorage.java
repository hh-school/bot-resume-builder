package ru.hh.resumebuilderbot.user.data.storage;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.database.ServiceAggregator;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class UserDataStorage {
    public static final Logger log = LoggerFactory.getLogger(UserDataStorage.class);
    private final ServiceAggregator serviceAggregator;
    private final Provider<Graph> graphProvider;
    private Map<User, UserData> userDataMap = new ConcurrentHashMap<>();

    @Inject
    private UserDataStorage(ServiceAggregator serviceAggregator, Provider<Graph> graphProvider) {
        this.serviceAggregator = serviceAggregator;
        this.graphProvider = graphProvider;
    }

    public boolean contains(User user) {
        return userDataMap.containsKey(user);
    }

    private UserData createNewUserData(User user) {
        log.info("Create new user data for user {}", user.getIndex());
        UserData userData = new UserData(graphProvider.get().getRoot());
        userDataMap.put(user, userData);
        return userData;
    }

    public void clear(User user) {
        createNewUserData(user);
    }

    public void startNewChat(User user) {
        createNewUserData(user);
    }

    public void registerAnswer(User user, Answer answer) {
        UserData userData = getUserData(user);
        userData.registerAnswer(answer);
    }

    public Object getMutex(User user) {
        if (!contains(user)) {
            return createNewUserData(user);
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
