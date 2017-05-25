package ru.hh.resumebuilderbot.telegram.handler.message;

import retrofit2.Response;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.Config;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.http.HHHTTPService;
import ru.hh.resumebuilderbot.http.HHUtils;
import ru.hh.resumebuilderbot.http.response.entity.Vacancy;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.ReplyKeyboardEnum;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PushMessageHandler extends MessageHandler {
    private final HHHTTPService hhHTTPService;

    public PushMessageHandler(DBProcessor dbProcessor, Graph graph, HHHTTPService hhHTTPService) {
        super(dbProcessor, graph, PushMessageHandler.class);
        this.hhHTTPService = hhHTTPService;
    }

    @Override
    public List<Question> handle(Long telegramId, Answer answer) {
        List<Question> questions = new ArrayList<>(5);
        int currentNodeId = dbProcessor.getUser(telegramId).getNodeId();
        if (currentNodeId > 11) {
            handleResumePush(telegramId);
            questions.add(new Question("Резюме успешно опубликовано. Мы нашли для вас несколько подходящих вакансий:"));
            questions.addAll(getSimilarVacancies(telegramId));
            questions.add(new Question("Спасибо, что воспользовались нашим ботом! Надеюсь, заполнение резюме " +
                    "не вызвало у вас трудностей."));
        } else {
            questions.add(new Question("Резюме не готово к отправке. Пожалуйста, ответье на все вопросы, " +
                    "прежде чем отправлять резюме"));
            questions.add(graph.getNode(currentNodeId).getQuestion());
        }
        return questions;
    }

    private void handleResumePush(Long telegramId) {
        User user = dbProcessor.getUser(telegramId);
        String authorizationHeader = HHUtils.buildAuthorizationHeader(
                Config.ACCESS_TOKEN_TYPE,
                Config.ACCESS_TOKEN
        );
        try {
            Response<Void> createResponse = hhHTTPService.createResume(user, authorizationHeader).execute();
            if (createResponse.code() != 201) {
                log.error("Error at resume push {}", createResponse.errorBody().string());
                return;
            }
            String hhResumeId = HHUtils.getResumeId(createResponse.headers().get("Location"));
            dbProcessor.setHHResumeId(telegramId, hhResumeId);
            Response<Void> publishResponse = hhHTTPService.publishResume(hhResumeId, authorizationHeader).execute();
            if (publishResponse.code() != 204) {
                log.error("Error at resume publish {}", publishResponse.errorBody().string());
            }
        } catch (IOException e) {
            log.error("Error at resume push", e);
        }
    }

    private List<Question> getSimilarVacancies(Long telegramId) {
        User user = dbProcessor.getUser(telegramId);
        String authorizationHeader = HHUtils.buildAuthorizationHeader(
                Config.ACCESS_TOKEN_TYPE,
                Config.ACCESS_TOKEN
        );
        List<Question> questions = new ArrayList<>();
        try {
            Response<List<Vacancy>> vacancyResponse = hhHTTPService.listResumeSimilarVacancies(user.getHhResumeId(),
                    authorizationHeader).execute();
            if (vacancyResponse.code() != 200) {
                log.error("Error at vacancy get {}", vacancyResponse.errorBody().string());
                return questions;
            }
            List<Vacancy> vacancies = vacancyResponse.body();
            if (vacancies.size() > 3) {
                vacancies = vacancies.subList(0, 3);
            }
            for (Vacancy vacancy : vacancies) {
                Question question = new Question(vacancy.getUrl(), Question.DEFAULT_SUGGEST_TYPE,
                        ReplyKeyboardEnum.NEGOTIATION);
                question.setCallbackData("negotiation:" + vacancy.getId());
                questions.add(question);
            }
        } catch (IOException e) {
            log.error("Error at vacancies get", e);
        }
        return questions;
    }
}
