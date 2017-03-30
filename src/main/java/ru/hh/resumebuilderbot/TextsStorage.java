package ru.hh.resumebuilderbot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TextsStorage {
    private static final TextsStorage instance = new TextsStorage();

    private static final String instructions = "Для очистки своей истории введите /clear." +
            " Чтобы просмотреть Ваше резюме, введите /show";

    private Map<String, String> textsMap = new ConcurrentHashMap<>();

    //hardcode
    static {
        instance.textsMap.put("AlreadyStarted", "Опрос уже запущен. Для удаления сохраненных результатов восполь" +
                "зуйтесь /clear");
        instance.textsMap.put("Unknown", "Ответ не понятен");
        instance.textsMap.put("OOPSTryRestart", "УПС! Что-то пошло не так. Начните заново с команды /start");
        instance.textsMap.put("Hello",
                "Добро пожаловать в Resume Builder Bot. " + instructions);
        instance.textsMap.put("Cleared",
                "Ваше резюме было очищено. Опрос начнется сначала");
        instance.textsMap.put("Finished",
                "Опрос закончен. " + instructions);
    }
    //end hardcode

    private Map<String, String> textsMap = new ConcurrentHashMap<>();

    private TextsStorage() {
    }

    public static String getText(String textId) {
        return instance.textsMap.get(textId);
    }
}
