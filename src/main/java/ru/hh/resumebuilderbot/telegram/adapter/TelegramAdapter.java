package ru.hh.resumebuilderbot.telegram.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.hh.resumebuilderbot.MessageUpdate;
import ru.hh.resumebuilderbot.MessengerAdapter;
import ru.hh.resumebuilderbot.question.Question;

import java.util.List;

public class TelegramAdapter implements MessengerAdapter {
    private static final Integer SUGGEST_CACHE_TIME = 1;
    private static final Logger log = LoggerFactory.getLogger(TelegramAdapter.class);
    private final TelegramLongPollingBot bot;

    public TelegramAdapter(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    @Override
    public void ask(Long telegramId, Question question) {
        SendMessage message = new SendMessage()
                .setChatId(telegramId)
                .setText(question.getText());

        message.setReplyMarkup(KeyboardGenerator.getReplyKeyboard(question));
        try {
            bot.sendMessage(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public void start() {
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void provideSuggests(String queryId, List<InlineQueryResult> inlineQueryResults) {
        try {
            AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
            answerInlineQuery.setCacheTime(SUGGEST_CACHE_TIME);
            answerInlineQuery.setInlineQueryId(queryId);
            answerInlineQuery.setResults(inlineQueryResults);
            bot.answerInlineQuery(answerInlineQuery);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void editMessage(MessageUpdate messageUpdate) {
        try {
            if (messageUpdate.isValid()) {
                InlineKeyboardMarkup keyboard = null;
                if (messageUpdate.hasKeyboard()) {
                    keyboard = KeyboardGenerator.getUpdatedKeyboard(messageUpdate.getReplyKeyboardEnum(),
                            messageUpdate.getKeyboardData());
                }
                if (messageUpdate.getUpdatedText() != null) {
                    EditMessageText editMessageText = new EditMessageText()
                            .setMessageId(messageUpdate.getMessageId())
                            .setChatId(messageUpdate.getTelegramId())
                            .setText(messageUpdate.getUpdatedText());
                    if (keyboard != null) {
                        editMessageText.setReplyMarkup(keyboard);
                    }
                    bot.editMessageText(editMessageText);
                } else {
                    EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup()
                            .setMessageId(messageUpdate.getMessageId())
                            .setChatId(messageUpdate.getTelegramId())
                            .setReplyMarkup(keyboard);
                    bot.editMessageReplyMarkup(editMessageReplyMarkup);
                }
            } else {
                log.error("Failed to update message {} for telegramUser {} cause no new Text or Keyboard",
                        messageUpdate.getMessageId(), messageUpdate.getTelegramId());
            }
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
