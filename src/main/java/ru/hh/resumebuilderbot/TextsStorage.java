package ru.hh.resumebuilderbot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TextsStorage {
    private static final TextsStorage instance = new TextsStorage();

    //hardcode
    static {
        instance.textsMap.put("AlreadyStarted", "Опрос уже запущен. Для удаления сохраненных результатов восполь" +
                "зуйтесь /clear");
        instance.textsMap.put("Unknown", "Ответ не понятен");
    }
    //end hardcode

    private Map<String, String> textsMap = new ConcurrentHashMap<>();

    private TextsStorage() {
    }

    public static String getText(String textId) {
        return instance.textsMap.get(textId);
    }
}
