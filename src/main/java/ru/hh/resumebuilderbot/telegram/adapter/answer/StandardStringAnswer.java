package ru.hh.resumebuilderbot.telegram.adapter.answer;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class StandardStringAnswer extends ProtoTelegramAnswer {

    StandardStringAnswer(Update update) {
        Message message = update.getMessage();
        answerText = message.getText();
        chatId = message.getChatId();
    }

    static boolean matches(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }
}
