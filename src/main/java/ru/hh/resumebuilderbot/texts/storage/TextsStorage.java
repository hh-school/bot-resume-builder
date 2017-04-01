package ru.hh.resumebuilderbot.texts.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TextsStorage {
    private static final TextsStorage instance = new TextsStorage();

    private static final String instructions = "Для очистки своей истории введите /clear." +
            " Чтобы просмотреть Ваше резюме, введите /show";

    //hardcode
    static {
        instance.textsMap.put(TextId.ALREADY_STARTED, "Опрос уже запущен. Для удаления сохраненных результатов восполь" +
                "зуйтесь /clear");
        instance.textsMap.put(TextId.UNKNOWN, "Ответ не понятен");
        instance.textsMap.put(TextId.OOPS_TRY_RESTART, "УПС! Что-то пошло не так. Начните заново с команды /start");
        instance.textsMap.put(TextId.HELLO,
                "Добро пожаловать в Resume Builder Bot. " + instructions);
        instance.textsMap.put(TextId.CLEARED,
                "Ваше резюме было очищено. Опрос начнется сначала");
        instance.textsMap.put(TextId.FINISHED,
                "Опрос закончен. " + instructions);
        instance.textsMap.put(TextId.EMPTY,
                "Ваше резюме пока пусто. ");
    }

    //end hardcode

    private Map<TextId, String> textsMap = new ConcurrentHashMap<>();

    private TextsStorage() {
    }

    public static String getText(TextId textId) {
        return instance.textsMap.get(textId);
    }
}
