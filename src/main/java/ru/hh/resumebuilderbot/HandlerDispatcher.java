package ru.hh.resumebuilderbot;

import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.http.HHHTTPService;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.edit.MessageUpdateHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.MessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.UnknownMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.factory.AnswerMessageHandlerFactory;
import ru.hh.resumebuilderbot.telegram.handler.message.factory.ClearMessageHandlerFactory;
import ru.hh.resumebuilderbot.telegram.handler.message.factory.MessageHandlerFactory;
import ru.hh.resumebuilderbot.telegram.handler.message.factory.ShowMessageHandlerFactory;
import ru.hh.resumebuilderbot.telegram.handler.message.factory.SkipMessageHandlerFactory;
import ru.hh.resumebuilderbot.telegram.handler.message.factory.StartMessageHandlerFactory;
import ru.hh.resumebuilderbot.telegram.handler.suggest.ChosenSuggestHandler;
import ru.hh.resumebuilderbot.telegram.handler.suggest.SuggestHandler;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Singleton
public class HandlerDispatcher {
    private static final Logger log = LoggerFactory.getLogger(HandlerDispatcher.class);
    private final Map<Pattern, MessageHandlerFactory> messageHandlerFactories = Collections.synchronizedMap(
            new LinkedHashMap<>()
    );
    private MessageUpdateHandler messageUpdateHandler;
    private SuggestHandler suggestHandler;
    private ChosenSuggestHandler chosenSuggestHandler;
    private MessageHandler unknownMessageHandler;

    public static HandlerDispatcher buildWithHandlers(DBProcessor dbProcessor, Graph graph,
                                                      SuggestService suggestService, HHHTTPService hhHTTPService) {
        return new HandlerDispatcher()
                .registerMessageHandlerFactory("/start", new StartMessageHandlerFactory(dbProcessor, graph))
                .registerMessageHandlerFactory("/show", new ShowMessageHandlerFactory(dbProcessor, graph))
                .registerMessageHandlerFactory("/clear", new ClearMessageHandlerFactory(dbProcessor, graph))
                .registerMessageHandlerFactory("/skip", new SkipMessageHandlerFactory(dbProcessor, graph))
                .registerMessageHandlerFactory(".*", new AnswerMessageHandlerFactory(dbProcessor, graph, hhHTTPService))

                .registerUnknownMessageHandler(new UnknownMessageHandler(dbProcessor, graph))
                .registerChosenSuggestHandler(new ChosenSuggestHandler(dbProcessor, graph, suggestService))
                .registerSuggestHandler(new SuggestHandler(dbProcessor, graph, suggestService))
                .registerMessageUpdateHandler(new MessageUpdateHandler(dbProcessor, graph));
    }

    public HandlerDispatcher registerUnknownMessageHandler(MessageHandler messageHandler) {
        unknownMessageHandler = messageHandler;
        return this;
    }

    public HandlerDispatcher registerMessageHandlerFactory(String regex, MessageHandlerFactory messageHandlerFactory) {
        messageHandlerFactories.put(Pattern.compile(regex), messageHandlerFactory);
        return this;
    }

    public HandlerDispatcher registerChosenSuggestHandler(ChosenSuggestHandler chosenSuggestHandler) {
        this.chosenSuggestHandler = chosenSuggestHandler;
        return this;
    }

    public HandlerDispatcher registerSuggestHandler(SuggestHandler suggestHandler) {
        this.suggestHandler = suggestHandler;
        return this;
    }

    public HandlerDispatcher registerMessageUpdateHandler(MessageUpdateHandler messageUpdateHandler) {
        this.messageUpdateHandler = messageUpdateHandler;
        return this;
    }

    public MessageHandler getMessageHandler(Answer answer) {
        String answerText = answer.getAnswerBody().toString();
        for (Map.Entry<Pattern, MessageHandlerFactory> entry : messageHandlerFactories.entrySet()) {
            if (entry.getKey().matcher(answerText).matches()) {
                return entry.getValue().get();
            }
        }
        log.error("Unknown message handler for answer: {}", answer.getAnswerBody());
        if (unknownMessageHandler == null) {
            throw new RuntimeException("Unknown message handler");
        }
        return unknownMessageHandler;
    }

    public SuggestHandler getSuggestHandler() {
        return this.suggestHandler;
    }

    public ChosenSuggestHandler getChosenSuggestHandler() {
        return this.chosenSuggestHandler;
    }

    public MessageUpdateHandler getMessageUpdateHandler() {
        return messageUpdateHandler;
    }
}
