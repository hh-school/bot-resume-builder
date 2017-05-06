package ru.hh.resumebuilderbot.telegram.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.BotBody;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.telegram.adapter.answer.TelegramAnswer;
import ru.hh.resumebuilderbot.telegram.adapter.answer.TelegramAnswerFactory;
import ru.hh.resumebuilderbot.telegram.handler.suggest.SuggestHandler;
import ru.hh.resumebuilderbot.telegram.handler.suggest.SuggestType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BotImpl extends TelegramLongPollingBot {
    private static final Logger log = LoggerFactory.getLogger(BotImpl.class);
    private static final Integer SUGGEST_CACHE_TIME = 1;
    private final String token;
    private final String botUsername;
    private BotBody botBody;
    private SuggestHandler suggestHandler;

    private Map<String, Question> currentQuestions = new HashMap<>();

    public BotImpl(String token, String botUsername, BotBody botBody, SuggestHandler suggestHandler) {
        this.token = token;
        this.botUsername = botUsername;
        this.botBody = botBody;
        this.suggestHandler = suggestHandler;
    }

    public void saveCurrentQuestion(long chatId, Question question) {
        currentQuestions.put(chatId + "", question);
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
            handleInlineMessage(update);
        }
    }

    private void handleInlineMessage(Update update) {
        InlineQuery query = update.getInlineQuery();
        String chatId = String.valueOf(query.getFrom().getId());
        // TODO NPE когда бот рестартнулся, и текущий шаг пользователя подсказка
        SuggestType suggestType = currentQuestions.get(chatId).getSuggestField();
        List<InlineQueryResult> queryResults;
        if (!suggestType.equals(SuggestType.FACULTIES_SUGGEST)) {
            queryResults = suggestHandler.getSuggestResults(query.getQuery(), suggestType);
        } else {
            queryResults = suggestHandler.getFacultiesResult("39144");
        }
        AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
        answerInlineQuery.setCacheTime(SUGGEST_CACHE_TIME);
        answerInlineQuery.setInlineQueryId(query.getId());
        answerInlineQuery.setResults(queryResults);
        try {
            answerInlineQuery(answerInlineQuery);
        } catch (TelegramApiException e) {
            log.error("Error", e);
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
