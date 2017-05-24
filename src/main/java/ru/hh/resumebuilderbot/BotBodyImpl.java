package ru.hh.resumebuilderbot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import ru.hh.resumebuilderbot.telegram.handler.edit.MessageUpdateHandler;
import ru.hh.resumebuilderbot.telegram.handler.edit.NegotiationHandler;
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
    private static final Logger log = LoggerFactory.getLogger(BotBodyImpl.class);
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
        messageHandler.handle(telegramId, answer).forEach(question -> messengerAdapter.ask(telegramId, question));
    }

    @Override
    public void provideSuggests(Long telegramId, String queryText, String queryId) {
        List<InlineQueryResult> queryResults;
        SuggestHandler suggestHandler = handlerDispatcher.getSuggestHandler();
        boolean isStrongSuggest = suggestHandler.isStrongSuggest(telegramId);
        try {
            List<?> suggests = suggestHandler.getSuggestResults(telegramId, queryText);
            queryResults = telegramConverter.convertList(suggests);
            if (queryResults.size() < 3 && !isStrongSuggest) {
                queryResults.add(NotificationInlineQueryResults.getUserInputNotFoundResult(queryText).get(0));
            }
        } catch (NonFacultiesFoundException e) {
            queryResults = NotificationInlineQueryResults.getNonFacultiesInstituteResult();
        } catch (NoSuggestsFoundException e) {
            if (isStrongSuggest) {
                queryResults = NotificationInlineQueryResults.getNotFoundErrorResult(e.getTextForSearch());
            } else {
                queryResults = NotificationInlineQueryResults.getUserInputNotFoundResult(e.getTextForSearch());
            }
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
    public void updateMessage(Long telegramId, Integer messageId, String callbackData) {
        MessageUpdateHandler handler = handlerDispatcher.getMessageUpdateHandler();
        messengerAdapter.editMessage(handler.handle(telegramId, messageId, callbackData));
    }

    @Override
    public void performNegotiation(Long telegramId, Integer messageId, String callbackData) {
        NegotiationHandler handler = handlerDispatcher.getNegotiationHandler();
        messengerAdapter.editMessage(handler.handle(telegramId, messageId, callbackData));
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
}
