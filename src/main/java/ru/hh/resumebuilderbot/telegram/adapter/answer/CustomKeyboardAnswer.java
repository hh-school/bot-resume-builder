package ru.hh.resumebuilderbot.telegram.adapter.answer;

import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;

class CustomKeyboardAnswer extends ProtoTelegramAnswer {

    CustomKeyboardAnswer(Update update) {
        CallbackQuery query = update.getCallbackQuery();
        chatId = query.getMessage().getChatId();
        String answer = query.getData().split(",")[0];
        answerText = answer.replace("keyData:", "");
    }

    static boolean matches(Update update) {
        if (!update.hasCallbackQuery()) {
            return false;
        }
        String callbackQueryData = update.getCallbackQuery().getData();
        return callbackQueryData.contains("READY");
    }
}
