package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.message.handler.AnswerMessageHandler;
import ru.hh.resumebuilderbot.message.handler.ClearMessageHandler;
import ru.hh.resumebuilderbot.message.handler.MessageHandler;
import ru.hh.resumebuilderbot.message.handler.ShowMessageHandler;
import ru.hh.resumebuilderbot.message.handler.SkipMessageHandler;
import ru.hh.resumebuilderbot.message.handler.StartMessageHandler;
import ru.hh.resumebuilderbot.message.handler.UnknownMessageHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

class Selector {
    private static final List<Parser> parsers;

    static {
        parsers = Collections.synchronizedList(new ArrayList<>());
        registerParser("/start", StartMessageHandler.class);
        registerParser("/show", ShowMessageHandler.class);
        registerParser("/clear", ClearMessageHandler.class);
        registerParser("/skip", SkipMessageHandler.class);
        registerParser(".*", AnswerMessageHandler.class);
    }

    static MessageHandler select(Answer answer) {
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

        Parser(String regExp, Class handlerClass) {
            pattern = Pattern.compile(regExp);
            this.handlerClass = handlerClass;
        }

        Class getHandlerClass() {
            return handlerClass;
        }

        boolean matches(String text) {
            return pattern.matcher(text).matches();
        }

    }
}
