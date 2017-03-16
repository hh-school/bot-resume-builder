package ru.hh.resumebuilderbot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TextsStorage {
    private TextsStorage() {};

    private static TextsStorage instance = new TextsStorage();

    private Map<String, String> textsMap = new ConcurrentHashMap<>();

    //hardcode

    static
    {
        instance.textsMap.put("AlreadyStarted", "Already started");
        instance.textsMap.put("Unknown", "Cant understand your message");
    }

    //end hardcode

    public static String getText(String textId)
    {
        return instance.textsMap.get(textId);
    }
}
