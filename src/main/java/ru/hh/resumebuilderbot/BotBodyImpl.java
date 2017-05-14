package ru.hh.resumebuilderbot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import ru.hh.resumebuilderbot.telegram.handler.message.MessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.suggest.NotificationInlineQueryResults;
import ru.hh.resumebuilderbot.telegram.handler.suggest.SuggestHandler;
import ru.hh.resumebuilderbot.telegram.handler.suggest.converter.TelegramConverter;
import ru.hh.resumebuilderbot.telegram.handler.suggest.exceptions.NoSuggestsFoundException;
import ru.hh.resumebuilderbot.telegram.handler.suggest.exceptions.NonFacultiesFoundException;
import ru.hh.resumebuilderbot.telegram.handler.suggest.exceptions.ShortSearchQueryException;

import java.util.List;

@Singleton
public class BotBodyImpl implements BotBody {
    private final HandlerDispatcher handlerDispatcher;
    private final TelegramConverter telegramConverter;
    private MessengerAdapter messengerAdapter;

    @Inject
    public BotBodyImpl(HandlerDispatcher handlerDispatcher, TelegramConverter telegramConverter) {
        this.handlerDispatcher = handlerDispatcher;
        this.telegramConverter = telegramConverter;
    }

    @Override
    public void askNextQuestions(Long telegramId, Answer answer) {
        MessageHandler messageHandler = handlerDispatcher.getMessageHandler(answer);
        messageHandler.handle(telegramId, answer).forEach((question) -> messengerAdapter.ask(telegramId, question));
    }

    @Override
    public void provideSuggests(Long telegramId, String queryText, String queryId) {
        List<InlineQueryResult> queryResults;
        SuggestHandler suggestHandler = handlerDispatcher.getSuggestHandler();
        try {
            List<?> suggests = suggestHandler.getSuggestResults(telegramId, queryText);
            queryResults = telegramConverter.convertList(suggests);
        } catch (NonFacultiesFoundException e) {
            queryResults = NotificationInlineQueryResults.getNonFacultiesInstituteResult();
        } catch (NoSuggestsFoundException e) {
            queryResults = NotificationInlineQueryResults.getNotFoundErrorResult(e.getTextForSearch());
        } catch (ShortSearchQueryException e) {
            queryResults = NotificationInlineQueryResults.getShortQueryErrorResult();
        }
        messengerAdapter.provideSuggests(queryId, queryResults);
    }

    @Override
    public void saveChosenSuggest(Long telegramId, Integer resultId, String queryText) {
        handlerDispatcher.getChosenSuggestHandler().saveChosenSuggest(telegramId, resultId, queryText);
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
}
