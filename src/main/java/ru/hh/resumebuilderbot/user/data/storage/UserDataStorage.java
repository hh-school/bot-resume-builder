package ru.hh.resumebuilderbot.user.data.storage;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.database.ServiceAggregator;
import ru.hh.resumebuilderbot.database.model.Node;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ru.hh.resumebuilderbot.question.storage.builder.Graph;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;

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

    public boolean contains(Integer telegramId) {
        User user = serviceAggregator.getUserService().getUserByTelegramId(telegramId);
        if (user == null) {
            return false;
        }
        return true;
    }

    public void clear(Integer telegramId) {
        User user = serviceAggregator.getUserService().getUserByTelegramId(telegramId);
        serviceAggregator.getUserService().delete(user);
    private UserData createNewUserData(User user) {
        log.info("Create new user data for user {}", user.getIndex());
        UserData userData = new UserData(graphProvider.get().getRoot());
        userDataMap.put(user, userData);
        return userData;
    }

    public void clear(User user) {
        createNewUserData(user);
    }

    public void startNewChat(Integer telegramId) {
        if (contains(telegramId)) {
            clear(telegramId);
        }
        User user = new User();
        user.setTelegramId(telegramId);
        serviceAggregator.getUserService().create(user);
    }

    public void registerAnswer(Integer telegramId, Answer answer) {
        User user = serviceAggregator.getUserService().getUserByTelegramId(telegramId);
        QuestionNode questionNode = Graph.get(user.getNode().getId());//как-то так я вижу это
        questionNode.registerAnswer(answer);
    }

    public Object getMutex(User user) {
        if (!contains(user)) {
            return createNewUserData(user);
        }
        return getUserData(user);
    }

    public Question getCurrentQuestion(Integer telegramId) {
        User user = serviceAggregator.getUserService().getUserByTelegramId(telegramId);
        QuestionNode questionNode = Graph.get(user.getNode().getId());//как-то так я вижу это
        return questionNode.getQuestion();
    }

    public void moveForward(Integer telegramId) {
        User user = serviceAggregator.getUserService().getUserByTelegramId(telegramId);
        QuestionNode questionNode = Graph.getNext(user.getNode().getId());//как-то так я вижу это
        Node node = serviceAggregator.getNodeService().get(questionNode.getId());
        user.setNode(node);
    }

    public boolean answerIsValid(User user, Answer answer) {
        return getUserData(user).answerIsValid(answer);
    }

    public boolean currentNodeIsSkippable(User user) {
        return getUserData(user).currentNodeIsSkippable();
    }

}
