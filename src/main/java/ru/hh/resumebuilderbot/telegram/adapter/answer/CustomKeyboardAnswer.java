package ru.hh.resumebuilderbot.telegram.adapter.answer;

import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;

class CustomKeyboardAnswer extends ProtoTelegramAnswer {

    CustomKeyboardAnswer(Update update) {
        CallbackQuery query = update.getCallbackQuery();
        chatId = query.getMessage().getChatId();
        answerText = query.getData();
    }

    static boolean matches(Update update) {
        return update.hasCallbackQuery();
    }
}
