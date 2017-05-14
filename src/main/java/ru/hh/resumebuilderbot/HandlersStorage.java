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
import ru.hh.resumebuilderbot.telegram.handler.suggest.ChosenSuggestHandler;
import ru.hh.resumebuilderbot.telegram.handler.suggest.SuggestHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Singleton
class HandlersStorage {
    private final DBService dbService;
    private final Graph graph;
    private final SuggestService suggestService;
    private final Map<String, MessageHandler> messageHandlers;
    private final SuggestHandler suggestHandler;
    private final ChosenSuggestHandler chosenSuggestHandler;

    @Inject
    public HandlersStorage(DBService dbService, Provider<Graph> graphProvider, SuggestService suggestService) {
        this.dbService = dbService;
        this.graph = graphProvider.get();
        this.suggestService = suggestService;
        this.messageHandlers = Collections.synchronizedMap(new HashMap<>());
        this.suggestHandler = new SuggestHandler(dbService, graph, suggestService);
        this.chosenSuggestHandler = new ChosenSuggestHandler(dbService, graph, suggestService);
        messageHandlers.put("/start", new StartMessageHandler(dbService, graph));
        messageHandlers.put("/show", new ShowMessageHandler(dbService, graph));
        messageHandlers.put("/clear", new ClearMessageHandler(dbService, graph));
        messageHandlers.put("/skip", new SkipMessageHandler(dbService, graph));
        messageHandlers.put(".*", new AnswerMessageHandler(dbService, graph));
    }

    public MessageHandler getMessageHandler(Answer answer) {
        String answerText = answer.getAnswerBody().toString();
        for (Map.Entry<String, MessageHandler> entry : messageHandlers.entrySet()) {
            if (Pattern.compile(entry.getKey()).matcher(answerText).matches()) {
                return entry.getValue();
            }
        }
        return new UnknownMessageHandler(dbService, graph);
    }

    public SuggestHandler getSuggestHandler() {
        return this.suggestHandler;
    }

    public ChosenSuggestHandler getChosenSuggestHandler() {
        return this.chosenSuggestHandler;
    }
}
