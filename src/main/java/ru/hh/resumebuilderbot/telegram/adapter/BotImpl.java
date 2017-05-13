package ru.hh.resumebuilderbot.telegram.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.BotBody;
import ru.hh.resumebuilderbot.telegram.adapter.answer.TelegramAnswer;
import ru.hh.resumebuilderbot.telegram.adapter.answer.TelegramAnswerFactory;

public class BotImpl extends TelegramLongPollingBot {
    private static final Logger log = LoggerFactory.getLogger(BotImpl.class);
    private final String token;
    private final String botUsername;
    private BotBody botBody;

    public BotImpl(String token, String botUsername, BotBody botBody) {
        this.token = token;
        this.botUsername = botUsername;
        this.botBody = botBody;
    }

    @Override
    public void onUpdateReceived(Update update) {
        TelegramAnswer telegramAnswer = TelegramAnswerFactory.create(update);
        if (telegramAnswer != null) {
            long telegramId = telegramAnswer.getChatId();
            String answerText = telegramAnswer.getAnswerText();
            Answer answer = new Answer(answerText);
            this.botBody.askNextQuestions(telegramId, answer);
        } else if (update.getInlineQuery() != null) {
            InlineQuery inlineQuery = update.getInlineQuery();
            long telegramId = inlineQuery.getFrom().getId();
            String query = inlineQuery.getQuery();
            String queryId = inlineQuery.getId();
            this.botBody.provideSuggests(telegramId, query, queryId);
        } else if (update.getChosenInlineQuery() != null) {
            ChosenInlineQuery chosenInlineQuery = update.getChosenInlineQuery();
            long telegramId = chosenInlineQuery.getFrom().getId();
            String queryText = chosenInlineQuery.getQuery();
            Integer resultId = Integer.valueOf(chosenInlineQuery.getResultId());
            this.botBody.saveChosenSuggest(telegramId, resultId, queryText);
        }
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }
}
