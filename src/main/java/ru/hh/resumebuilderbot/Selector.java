package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.message.handler.*;
import ru.hh.resumebuilderbot.message_handler.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Selector {
    private Answer answer;
    private final Map<Pattern, MessageHandler> parsers;

    public Selector(Answer answer) {
        this.answer = answer;
        parsers = new HashMap<>();
        parsers.put(Pattern.compile("/start"), new StartMessageHandler());
        parsers.put(Pattern.compile("/show"), new ShowMessageHandler());
        parsers.put(Pattern.compile("/clear"), new ClearMessageHandler());
        parsers.put(Pattern.compile(".*"), new MessageHandler.AnswerMessageHandler());
    }

    public MessageHandler select()
    {
        String answerText = answer.getAnswerBody().toString();
        for (Map.Entry<Pattern, MessageHandler> entry : parsers.entrySet())
        {
            if (entry.getKey().matcher(answerText).matches())
            {
                return entry.getValue();
            }
        }
        return new UnknownMessageHandler();
    }
}
