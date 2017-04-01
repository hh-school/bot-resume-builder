package ru.hh.resumebuilderbot.telegram.adapter.answer;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class StandardStringAnswer extends ProtoTelegramAnswer {

    public StandardStringAnswer(Update update) {
        Message message = update.getMessage();
        answerText = message.getText();
        innerChatId = message.getChatId();
    }
}