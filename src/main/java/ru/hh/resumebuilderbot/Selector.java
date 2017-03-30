package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.message.handler.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Selector {
    private static final List<Parser> parsers;

    static {
        parsers = Collections.synchronizedList(new ArrayList<>());
        registerParser("/start", StartMessageHandler.class);
        registerParser("/show", ShowMessageHandler.class);
        registerParser("/clear", ClearMessageHandler.class);
        registerParser(".*", AnswerMessageHandler.class);
    }

    public static MessageHandler select(Answer answer) {
        String answerText = answer.getAnswerBody().toString();
        for (Parser parser : parsers) {
            if (parser.matches(answerText)) {
                try {
                    return (MessageHandler) parser.getHandlerClass().newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        return new UnknownMessageHandler();
    }

    private static void registerParser(String regExp, Class handlerClass) {
        parsers.add(new Parser(regExp, handlerClass));
    }

    private static class Parser {
        private Pattern pattern;
        private Class handlerClass;

        public Parser(String regExp, Class handlerClass) {
            pattern = Pattern.compile(regExp);
            this.handlerClass = handlerClass;
        }

        public Class getHandlerClass() {
            return handlerClass;
        }

        public boolean matches(String text) {
            return pattern.matcher(text).matches();
        }

    }
}
