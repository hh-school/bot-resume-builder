package ru.hh.resumebuilderbot.telegram.handler.edit;

import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.MessageUpdate;
import ru.hh.resumebuilderbot.question.ReplyKeyboardEnum;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.Handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MessageUpdateHandler extends Handler {

    public MessageUpdateHandler(DBProcessor dbProcessor, Graph graph) {
        super(dbProcessor, graph, MessageUpdateHandler.class);
    }

    public MessageUpdate handle(Long telegramId, Integer messageId, String callbackData) {
        MessageUpdate messageUpdate = new MessageUpdate(telegramId, messageId);

        Map<String, String> callbackInfo = new HashMap<>();
        try {
            Arrays.asList(callbackData.split(","))
                    .forEach(elem -> callbackInfo.put(elem.split(":")[0], elem.split(":")[1]));

            if (callbackInfo.containsKey("status") && callbackInfo.get("status").equals("READY")) {
                String chosenData = callbackInfo.get("keyData");
                messageUpdate.setUpdatedText("Ваш ответ: " + chosenData);
                return messageUpdate;
            }
            if (callbackInfo.containsKey("keyEnum")) {
                messageUpdate.setReplyKeyboardEnum(ReplyKeyboardEnum.valueOf(callbackInfo.get("keyEnum")));
            }
            if (callbackInfo.containsKey("keyData")) {
                messageUpdate.setKeyboardData(callbackInfo.get("keyData"));
            }
            if (callbackInfo.containsKey("text")) {
                Map<String, String> texts = new HashMap<>();
                texts.put("1", String.format("Вы выбрали '%s'. Продолжайте ввод.", callbackInfo.get("keyData")));
                texts.put("2", getCurrentNode(telegramId).getQuestion().getText());
                messageUpdate.setUpdatedText(texts.get(callbackInfo.get("text")));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            log.warn("User select incorrect button");
        }
        return messageUpdate;
    }
}
