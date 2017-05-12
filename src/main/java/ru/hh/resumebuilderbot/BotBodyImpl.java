package ru.hh.resumebuilderbot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import ru.hh.resumebuilderbot.telegram.handler.message.MessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.suggest.SuggestHandler;

import java.util.List;

@Singleton
public class BotBodyImpl implements BotBody {
    private final Selector selector;
    private MessengerAdapter messengerAdapter;

    @Inject
    public BotBodyImpl(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void askNextQuestions(Long telegramId, Answer answer) {
        MessageHandler messageHandler = selector.select(answer);
        messageHandler.handle(telegramId, answer).forEach((question) -> messengerAdapter.ask(telegramId, question));
    }

    @Override
    public void provideSuggests(Long telegramId, String queryText, String queryId) {
        SuggestHandler suggestHandler = selector.getSuggestHandler();
        List<InlineQueryResult> suggests = suggestHandler.getSuggestResults(telegramId, queryText);
        messengerAdapter.provideSuggests(queryId, suggests);
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
}
