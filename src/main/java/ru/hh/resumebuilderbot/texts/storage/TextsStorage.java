package ru.hh.resumebuilderbot.texts.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TextsStorage {
    private static final String instructions = "Для очистки своей истории введите /clear." +
            " Чтобы просмотреть Ваше резюме, введите /show";

    private static Map<TextId, String> textsMap = new ConcurrentHashMap<>();

    //hardcode
    static {
        textsMap.put(TextId.ALREADY_STARTED, "Опрос уже запущен. Для удаления сохраненных результатов восполь" +
                "зуйтесь /clear");
        textsMap.put(TextId.UNKNOWN, "Ответ не понятен");
        textsMap.put(TextId.OOPS_TRY_RESTART, "УПС! Что-то пошло не так. Начните заново с команды /start");
        textsMap.put(TextId.HELLO,
                "Добро пожаловать в Resume Builder Bot. " + instructions);
        textsMap.put(TextId.CLEARED,
                "Ваше резюме было очищено. Опрос начнется сначала");
        textsMap.put(TextId.FINISHED,
                "Опрос закончен. " + instructions);
        textsMap.put(TextId.EMPTY,
                "Ваше резюме пока пусто. ");
        textsMap.put(TextId.INVALID_ANSWER,
                "Ответ не понятен");
    }

    //end hardcode

    private TextsStorage() {
    }

    public static String getText(TextId textId) {
        return textsMap.get(textId);
    }
}
