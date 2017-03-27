package ru.hh.resumebuilderbot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TextsStorage {
    private TextsStorage() {};

    private static final TextsStorage instance = new TextsStorage();

    private Map<String, String> textsMap = new ConcurrentHashMap<>();

    //hardcode

    static
    {
        instance.textsMap.put("AlreadyStarted", "Опрос уже запущен. Для удаления сохраненных результатов восполь" +
                                                "зуйтесь /clear");
        instance.textsMap.put("Unknown", "Ответ не понятен");
    }

    //end hardcode

    public static String getText(String textId)
    {
        return instance.textsMap.get(textId);
    }
}
