package ru.hh.resumebuilderbot.telegram.adapter.answer;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class ContactAnswer extends ProtoTelegramAnswer {

    ContactAnswer(Update update) {
        Message message = update.getMessage();
        answerText = message.getContact().getPhoneNumber();
        chatId = message.getChatId();
    }

    static boolean matches(Update update) {
        return update.hasMessage() && update.getMessage().getContact() != null;
    }
}
