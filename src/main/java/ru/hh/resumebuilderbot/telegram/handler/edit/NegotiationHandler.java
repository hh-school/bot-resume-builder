package ru.hh.resumebuilderbot.telegram.handler.edit;

import retrofit2.Response;
import ru.hh.resumebuilderbot.Config;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.MessageUpdate;
import ru.hh.resumebuilderbot.http.HHHTTPService;
import ru.hh.resumebuilderbot.http.HHUtils;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.Handler;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NegotiationHandler extends Handler {
    private final HHHTTPService hhHTTPService;

    public NegotiationHandler(DBProcessor dbProcessor, Graph graph, HHHTTPService hhHTTPService) {
        super(dbProcessor, graph, NegotiationHandler.class);
        this.hhHTTPService = hhHTTPService;
    }

    public MessageUpdate handle(Long telegramId, Integer messageId, String callbackData) {
        MessageUpdate messageUpdate = new MessageUpdate(telegramId, messageId);

        Map<String, String> callbackInfo = new HashMap<>();
        try {
            Arrays.asList(callbackData.split(","))
                    .forEach(elem -> callbackInfo.put(elem.split(":")[0], elem.split(":")[1]));

            if (callbackInfo.containsKey("negotiation")) {
                String resumeId = dbProcessor.getUser(telegramId).getHhResumeId();
                negotiationPerform(resumeId, callbackInfo.get("negotiation"));
                messageUpdate.setUpdatedText("Вы успешно отклинулись на вакансию, вам вот-вот перезвонят!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            log.warn("User select incorrect button");
        }
        return messageUpdate;
    }

    private void negotiationPerform(String resumeId, String vacancyId) {
        String authorizationHeader = HHUtils.buildAuthorizationHeader(
                Config.ACCESS_TOKEN_TYPE,
                Config.ACCESS_TOKEN
        );
        try {
            Response<Void> createResponse = hhHTTPService
                    .createNegotiation(resumeId, vacancyId, "Отправлено при помощи HHResumeBot", authorizationHeader)
                    .execute();
            if (createResponse.code() != 201) {
                log.error("Error at resume push {}", createResponse.errorBody().string());
            }
        } catch (IOException e) {
            log.error("Error while negotiation creating", e);
        }
    }
}
