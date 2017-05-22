package ru.hh.resumebuilderbot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.edit.MessageUpdateHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.AnswerMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.ClearMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.MessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.ShowMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.SkipMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.StartMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.UnknownMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.suggest.ChosenSuggestHandler;
import ru.hh.resumebuilderbot.telegram.handler.suggest.SuggestHandler;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Singleton
class HandlerDispatcher {
    private final DBProcessor dbProcessor;
    private final Graph graph;
    private final Map<String, MessageHandler> messageHandlers;
    private final MessageUpdateHandler messageUpdateHandler;
    private final SuggestHandler suggestHandler;
    private final ChosenSuggestHandler chosenSuggestHandler;

    @Inject
    public HandlerDispatcher(DBProcessor dbProcessor, Graph graph, SuggestService suggestService) {
        this.dbProcessor = dbProcessor;
        this.graph = graph;
        this.messageHandlers = Collections.synchronizedMap(new LinkedHashMap<>());
        this.suggestHandler = new SuggestHandler(dbProcessor, graph, suggestService);
        this.chosenSuggestHandler = new ChosenSuggestHandler(dbProcessor, graph, suggestService);
        this.messageUpdateHandler = new MessageUpdateHandler(dbProcessor, graph);
        messageHandlers.put("/start", new StartMessageHandler(dbProcessor, graph));
        messageHandlers.put("/show", new ShowMessageHandler(dbProcessor, graph));
        messageHandlers.put("/clear", new ClearMessageHandler(dbProcessor, graph));
        messageHandlers.put("/skip", new SkipMessageHandler(dbProcessor, graph));
        messageHandlers.put(".*", new AnswerMessageHandler(dbProcessor, graph));
    }

    public MessageHandler getMessageHandler(Answer answer) {
        String answerText = answer.getAnswerBody().toString();
        for (Map.Entry<String, MessageHandler> entry : messageHandlers.entrySet()) {
            if (Pattern.compile(entry.getKey()).matcher(answerText).matches()) {
                return entry.getValue();
            }
        }
        return new UnknownMessageHandler(dbProcessor, graph);
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
