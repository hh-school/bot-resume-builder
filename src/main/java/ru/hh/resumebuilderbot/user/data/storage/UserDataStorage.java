package ru.hh.resumebuilderbot.user.data.storage;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.TelegramUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.ServiceAggregator;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.database.model.education.Education;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;


@Singleton
public class UserDataStorage {
    public static final Logger log = LoggerFactory.getLogger(UserDataStorage.class);
    private final ServiceAggregator serviceAggregator;
    private final Provider<Graph> graphProvider;

    @Inject
    private UserDataStorage(ServiceAggregator serviceAggregator, Provider<Graph> graphProvider) {
        this.serviceAggregator = serviceAggregator;
        this.graphProvider = graphProvider;
    }

    public boolean contains(TelegramUser telegramUser) {
        User user = getUser(telegramUser);
        if (user == null) {
            return false;
        }
        return true;
    }

    private User getUser(TelegramUser telegramUser){
        return serviceAggregator.getUserService().getUserByTelegramId(telegramUser.getId());
    }

    public void clear(TelegramUser telegramUser) {
        User user = getUser(telegramUser);
        serviceAggregator.getUserService().delete(user);
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
        User user = getUser(telegramUser);
        QuestionNode questionNode =  graphProvider.get().getRoot();
        return questionNode;
    }

    public Question getCurrentQuestion(TelegramUser telegramUser) {
        QuestionNode questionNode =  getCurrentQuestionNode(telegramUser);
        return questionNode.getQuestion();
    }

    public void moveForward(TelegramUser telegramUser){
        QuestionNode currentQuestionNode = getCurrentQuestionNode(telegramUser);
        currentQuestionNode = currentQuestionNode.getNext();
        User user = getUser(telegramUser);
        //user.setNodeId(currentQuestionNode.getId());
        serviceAggregator.getUserService().update(user);
    }
}
