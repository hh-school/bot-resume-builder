package ru.hh.resumebuilderbot.telegram.adapter.answer;

import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;

public class CustomKeyboardAnswer extends ProtoTelegramAnswer {

    public CustomKeyboardAnswer(Update update) {
        CallbackQuery querry = update.getCallbackQuery();
        innerChatId = querry.getMessage().getChatId();
        answerText = querry.getData();
    }
}
