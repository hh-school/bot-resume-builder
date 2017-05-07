package ru.hh.resumebuilderbot.message.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.DBService;
import ru.hh.resumebuilderbot.TelegramUser;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.database.model.gender.Gender;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;

import java.util.List;

public abstract class MessageHandler {
    protected static final Logger log = LoggerFactory.getLogger(MessageHandler.class);
    protected DBService dbService;
    protected Graph graph;

    protected MessageHandler(DBService dbService, Graph graph) {
        // TODO not all handlers require DBService
        this.dbService = dbService;
        this.graph = graph;
    }

    public abstract List<Question> handle(TelegramUser telegramUser, Answer answer);

    protected void saveValue(TelegramUser telegramUser, String field, String value) {
        switch (field) {
            case "phone":
                dbService.savePhoneNumber(telegramUser, value);
                break;
            case "firstName":
                dbService.saveFirstname(telegramUser, value);
                break;
            case "lastName":
                dbService.saveLastName(telegramUser, value);
                break;
            case "sex":
                dbService.saveGender(telegramUser, Gender.fromCode(value.charAt(0)));
                break;
            case "town":
                dbService.saveUserArea(telegramUser, value, null);
                break;
            case "educationType":
                dbService.addNewEducation(telegramUser);
                dbService.saveEducationLevel(telegramUser, EducationLevel.fromCode(value));
                break;
            case "institution":
                dbService.saveInstitute(telegramUser, null, value);
                break;
            default:
                log.warn("User {}. Не найдено поле {} в базе данных. Попытка сохранить данные в невалидном поле",
                        telegramUser.getId(), field);
                break;
        }
    }
}
