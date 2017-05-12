package ru.hh.resumebuilderbot;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.message.AnswerMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.ClearMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.MessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.ShowMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.SkipMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.StartMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.UnknownMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.suggest.SuggestHandler;
import ru.hh.resumebuilderbot.telegram.handler.suggest.converter.TelegramConverter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Singleton
class Selector {
    private final List<Parser> parsers;
    private final DBService dbService;
    private final Graph graph;
    private final SuggestService suggestService;
    private final TelegramConverter telegramConverter;

    @Inject
    public Selector(DBService dbService, Provider<Graph> graphProvider,
                    SuggestService suggestService, TelegramConverter telegramConverter) {
        this.dbService = dbService;
        this.graph = graphProvider.get();
        this.suggestService = suggestService;
        this.telegramConverter = telegramConverter;
        parsers = Collections.synchronizedList(new ArrayList<>());
        registerParser("/start", StartMessageHandler.class);
        registerParser("/show", ShowMessageHandler.class);
        registerParser("/clear", ClearMessageHandler.class);
        registerParser("/skip", SkipMessageHandler.class);
        registerParser(".*", AnswerMessageHandler.class);
    }

    MessageHandler select(Answer answer) {
        String answerText = answer.getAnswerBody().toString();
        for (Parser parser : parsers) {
            if (parser.matches(answerText)) {
                try {
                    Constructor<?> constructor = parser.getHandlerClass()
                            .getDeclaredConstructor(DBService.class, Graph.class);
                    return (MessageHandler) constructor.newInstance(dbService, graph);
                } catch (IllegalAccessException | InstantiationException | NoSuchMethodException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return new UnknownMessageHandler(dbService, graph);
    }

    SuggestHandler getSuggestHandler() {
        return new SuggestHandler(dbService, graph, suggestService, telegramConverter);
    }


    private void registerParser(String regExp, Class handlerClass) {
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
