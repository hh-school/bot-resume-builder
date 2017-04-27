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
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.suggester.QueryResultsGenerator;
import ru.hh.resumebuilderbot.telegram.adapter.answer.TelegramAnswer;
import ru.hh.resumebuilderbot.telegram.adapter.answer.TelegramAnswerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BotImpl extends TelegramLongPollingBot {
    protected final static Logger log = LoggerFactory.getLogger(BotImpl.class);
    private final String token;
    private final String botUsername;
    private BotBody botBody;

    private Map<String, Question> currentQuestions = new HashMap<>();

    public BotImpl(String token, String botUsername, BotBody botBody) {
        this.token = token;
        this.botUsername = botUsername;
        this.botBody = botBody;
    }

    public void saveCurrentQuestion(long chatId, Question question){
        currentQuestions.put(chatId + "", question);
    }

    @Override
    public void onUpdateReceived(Update update) {
        TelegramAnswer telegramAnswer = TelegramAnswerFactory.create(update);
        if (telegramAnswer != null) {
            long chatId = telegramAnswer.getChatId();
            User user = new User(chatId);
            String answerText = telegramAnswer.getAnswerText();
            Answer answer = new Answer(answerText);
            this.botBody.askNextQuestions(user, answer);
        } else if (update.getInlineQuery() != null) {
            InlineQuery query = update.getInlineQuery();
            String chatId = query.getFrom().getId() + "";
            List<InlineQueryResult> queryResults = QueryResultsGenerator.getResults(query, currentQuestions.get(chatId));
            AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
            answerInlineQuery.setCacheTime(1);
            answerInlineQuery.setInlineQueryId(query.getId());
            answerInlineQuery.setResults(queryResults);
            try {
                answerInlineQuery(answerInlineQuery);
            } catch (TelegramApiException e) {
                log.error("Error");
            }
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
