package ru.hh.resumebuilderbot.telegram.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.DBService;
import ru.hh.resumebuilderbot.database.model.education.EducationLevel;
import ru.hh.resumebuilderbot.database.model.gender.Gender;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;

public abstract class Handler {
    protected static final Logger log = LoggerFactory.getLogger(Handler.class);
    protected DBService dbService;
    protected Graph graph;

    protected Handler(DBService dbService, Graph graph) {
        // TODO not all handlers require DBService
        this.dbService = dbService;
        this.graph = graph;
    }

    protected QuestionNode getCurrentNode(Long telegramId){
        return graph.getNode(dbService.getNodeId(telegramId));
    }

    protected void saveValue(Long telegramId, String field, String value) {
        switch (field) {
            case "phone":
                dbService.savePhoneNumber(telegramId, value);
                break;
            case "firstName":
                dbService.saveFirstname(telegramId, value);
                break;
            case "lastName":
                dbService.saveLastName(telegramId, value);
                break;
            case "sex":
                dbService.saveGender(telegramId, Gender.fromCode(value.charAt(0)));
                break;
            case "town":
                dbService.saveUserArea(telegramId, value, null);
                break;
            case "educationType":
                dbService.addNewEducation(telegramId);
                dbService.saveEducationLevel(telegramId, EducationLevel.fromCode(value));
                break;
            case "institution":
                dbService.saveInstitute(telegramId, null, value);
                break;
            default:
                log.warn("User {}. Не найдено поле {} в базе данных. Попытка сохранить данные в невалидном поле",
                        telegramId, field);
                break;
        }
    }
}
