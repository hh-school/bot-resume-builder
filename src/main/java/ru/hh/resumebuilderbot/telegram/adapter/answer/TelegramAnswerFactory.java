package ru.hh.resumebuilderbot.telegram.adapter.answer;

import org.telegram.telegrambots.api.objects.Update;

public class TelegramAnswerFactory {
    public static TelegramAnswer create(Update update) {
        if (updateIsTextAnswer(update)) {
            return new StandardStringAnswer(update);
        } else {
            return new CustomKeyboardAnswer(update);
        }
    }

    public static boolean canHandleUpdate(Update update) {
        return updateIsTextAnswer(update)
                || updateIsCustomKeyboardAnswer(update);
    }

    private static boolean updateIsTextAnswer(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    private static boolean updateIsCustomKeyboardAnswer(Update update) {
        return update.hasCallbackQuery();
    }
}
