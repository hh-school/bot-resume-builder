package ru.hh.resumebuilderbot.telegram.adapter.answer;

import org.telegram.telegrambots.api.objects.Update;

public class TelegramAnswerFactory {

    public static TelegramAnswer create(Update update) {
        if (StandardStringAnswer.matches(update)) {
            return new StandardStringAnswer(update);
        }
        if (CustomKeyboardAnswer.matches(update)) {
            return new CustomKeyboardAnswer(update);
        }
        return null;
    }
}
