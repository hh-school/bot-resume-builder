package ru.hh.resumebuilderbot.user.data.storage;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.TelegramUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.database.ServiceAggregator;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ru.hh.resumebuilderbot.question.storage.builder.Graph;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;
import ru.hh.resumebuilderbot.question.storage.QuestionsStorage;
import ru.hh.resumebuilderbot.question.storage.graph.node.QuestionNode;

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

    public boolean contains(TelegramUser telegramUser) {
        User user = serviceAggregator.getUserService().getUserByTelegramId(telegramUser.getId());
        if (user == null) {
            return false;
        }
        return true;
    }

    public void clear(TelegramUser telegramUser) {
        User user = serviceAggregator.getUserService().getUserByTelegramId(telegramUser.getId());
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

    private void addNewUser(TelegramUser telegramUser){
        User user = new User();
        user.setTelegramId(telegramUser.getId());
        serviceAggregator.getUserService().create(user);
    }

    public void startNewChat(TelegramUser telegramUser) {
        if (contains(telegramUser)) {
            clear(telegramUser);
        }
        addNewUser(telegramUser);
    }

    public QuestionNode getCurrentQuestionNode(TelegramUser telegramUser){
        User user = serviceAggregator.getUserService().getUserByTelegramId(telegramUser.getId());
        QuestionNode questionNode =  QuestionsStorage.getClonedRoot();
        return questionNode;
    }

    public Question getCurrentQuestion(TelegramUser telegramUser) {
        QuestionNode questionNode =  getCurrentQuestionNode(telegramUser);
        return questionNode.getQuestion();
    }
}
