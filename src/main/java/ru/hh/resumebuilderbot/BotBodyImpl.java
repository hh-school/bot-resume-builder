package ru.hh.resumebuilderbot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.telegram.handler.message.MessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.suggest.SuggestHandler;
import ru.hh.resumebuilderbot.telegram.handler.suggest.converter.TelegramConverter;

import java.util.List;

@Singleton
public class BotBodyImpl implements BotBody {
    private final HandlersDispatcher handlersDispatcher;
    private final TelegramConverter telegramConverter;
    private MessengerAdapter messengerAdapter;

    @Inject
    public BotBodyImpl(HandlersDispatcher handlersDispatcher, TelegramConverter telegramConverter) {
        this.handlersDispatcher = handlersDispatcher;
        this.telegramConverter = telegramConverter;
    }

    @Override
    public void askNextQuestions(Long telegramId, Answer answer) {
        MessageHandler messageHandler = handlersDispatcher.getMessageHandler(answer);
        messageHandler.handle(telegramId, answer).forEach((question) -> messengerAdapter.ask(telegramId, question));
    }

    @Override
    public void provideSuggests(Long telegramId, String queryText, String queryId) {
        SuggestHandler suggestHandler = handlersDispatcher.getSuggestHandler();
        List<?> suggests = suggestHandler.getSuggestResults(telegramId, queryText);
        messengerAdapter.provideSuggests(queryId, telegramConverter.convertList(suggests));
    }

    @Override
    public void saveChosenSuggest(Long telegramId, Integer resultId, String queryText) {
        handlersDispatcher.getChosenSuggestHandler().saveChosenSuggest(telegramId, resultId, queryText);
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
}
