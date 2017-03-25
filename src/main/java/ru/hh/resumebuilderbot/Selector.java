package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.message.handler.*;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Selector {
    private static final Map<Pattern, Class> parsers = Collections.synchronizedMap(new LinkedHashMap<>());

    static
    {
        registerParser("/start", StartMessageHandler.class);
        registerParser("/show", ShowMessageHandler.class);
        registerParser("/clear", ClearMessageHandler.class);
        registerParser("/.*", AnswerMessageHandler.class);
    }

    public static MessageHandler select(Answer answer)
    {
        String answerText = answer.getAnswerBody().toString();
        for (Map.Entry<Pattern, Class> entry : parsers.entrySet())
        {
            if (entry.getKey().matcher(answerText).matches())
            {
                try {
                    return (MessageHandler)entry.getValue().newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return new UnknownMessageHandler();
    }

    private static void registerParser(String regExp, Class handlerClass)
    {
        parsers.put(Pattern.compile(regExp), handlerClass);
    }
}
