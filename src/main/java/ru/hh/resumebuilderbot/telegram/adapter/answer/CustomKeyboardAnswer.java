package ru.hh.resumebuilderbot.telegram.adapter.answer;

import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;

class CustomKeyboardAnswer extends ProtoTelegramAnswer {

    CustomKeyboardAnswer(Update update) {
        CallbackQuery querry = update.getCallbackQuery();
        chatId = querry.getMessage().getChatId();
        answerText = querry.getData();
    }

    static boolean matches(Update update) {
        return update.hasCallbackQuery();
    }
}
