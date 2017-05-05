package ru.hh.resumebuilderbot;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.message.handler.AnswerMessageHandler;
import ru.hh.resumebuilderbot.message.handler.ClearMessageHandler;
import ru.hh.resumebuilderbot.message.handler.MessageHandler;
import ru.hh.resumebuilderbot.message.handler.ShowMessageHandler;
import ru.hh.resumebuilderbot.message.handler.SkipMessageHandler;
import ru.hh.resumebuilderbot.message.handler.StartMessageHandler;
import ru.hh.resumebuilderbot.message.handler.UnknownMessageHandler;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Singleton
class Selector {
    private final List<Parser> parsers;
    private final UserDataStorage userDataStorage;
    private final Graph graph;

    @Inject
    public Selector(UserDataStorage userDataStorage, Provider<Graph> graphProvider) {
        this.userDataStorage = userDataStorage;
        this.graph = graphProvider.get();
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
                            .getDeclaredConstructor(UserDataStorage.class, Graph.class);
                    return (MessageHandler) constructor.newInstance(userDataStorage, graph);
                } catch (IllegalAccessException | InstantiationException | NoSuchMethodException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return new UnknownMessageHandler(userDataStorage, graph);
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
