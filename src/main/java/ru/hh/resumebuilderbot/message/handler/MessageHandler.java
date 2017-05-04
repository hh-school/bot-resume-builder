package ru.hh.resumebuilderbot.message.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.TelegramUser;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.database.model.gender.Gender;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

import java.util.List;

public abstract class MessageHandler {
    protected final static Logger log = LoggerFactory.getLogger(MessageHandler.class);
    protected UserDataStorage userDataStorage;
    protected Graph graph;

    protected MessageHandler(UserDataStorage userDataStorage, Graph graph) {
        // TODO not all handlers require userDataStorage
        this.userDataStorage = userDataStorage;
        this.graph = graph;
    }

    public abstract List<Question> handle(TelegramUser telegramUser, Answer answer);

    protected void saveValue(TelegramUser telegramUser, String field, String value) {
        switch (field) {
            case "firstName":
                userDataStorage.saveFirstname(telegramUser, value);
                break;
            case "lastName":
                userDataStorage.saveLastName(telegramUser, value);
                break;
            case "sex":
                userDataStorage.saveGender(telegramUser, Gender.fromCode(value.charAt(0)));
                break;
            case "town":
                userDataStorage.saveUserArea(telegramUser, value, null);
                break;
            case "educationType":
                userDataStorage.addNewEducation(telegramUser);
                userDataStorage.saveEducationLevel(telegramUser, EducationLevel.valueOf(value));
                break;
            case "institution":
                userDataStorage.saveInstitute(telegramUser, null, value);
                break;
            default:
                log.warn("User {}. Не найдено поле {} в базе данных. Попытка сохранить данные в невалидном поле",
                        telegramUser.getId(), field);
                break;
        }
    }
}
