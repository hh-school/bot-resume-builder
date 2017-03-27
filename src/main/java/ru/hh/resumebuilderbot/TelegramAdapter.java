package ru.hh.resumebuilderbot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TelegramAdapter implements MessengerAdapter {
    private final String _token;
    private final String _bot_username;
    private final BotImpl _bot;
    private int _timeout;

    private AbstractBotBody _handler;

    TelegramAdapter(String token, String botUsername, int timeoutMs) {
        _token = token;
        _bot_username = botUsername;
        _bot = new BotImpl();
        _timeout = timeoutMs;
    }

    @Override
    public void setHandler(AbstractBotBody handler) {
        _handler = handler;
    }

    @Override
    public void ask(Question question) {
        SendMessage msg = new SendMessage()
                .setChatId(question.getChatId().getIndex())
                .setText(question.getText());

        List<String> allowedAnswers = question.getAllowedAnswers();

        if (!allowedAnswers.isEmpty()) {
            msg.setReplyMarkup(generateMarkup(allowedAnswers));
        }
        try {
            _bot.sendMsg(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup generateMarkup(List<String> allowedAnswers)
    {
        InlineKeyboardMarkup result = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (int i=0; i < allowedAnswers.size(); ++i) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton()
                    .setText(allowedAnswers.get(i))
                    .setCallbackData(allowedAnswers.get(i)));
            rowsInline.add(rowInline);
        }
        result.setKeyboard(rowsInline);
        return result;
    }

    private class BotImpl extends TelegramLongPollingBot {
        public void sendMsg(SendMessage msg) throws TelegramApiException {
            sendMessage(msg);
        }

        @Override
        public void onUpdateReceived(Update update) {

            if (update.hasMessage() && update.getMessage().hasText()) {
                long chat_id = update.getMessage().getChatId();

                _handler.answer(
                        new Answer(
                                new ChatId(chat_id),
                                update.getMessage().getText()
                        ), _timeout);
            } else if (update.hasCallbackQuery()) {
                String call_data = update.getCallbackQuery().getData();
                long chat_id = update.getCallbackQuery().getMessage().getChatId();

                _handler.answer(
                        new Answer(
                                new ChatId(chat_id),
                                call_data
                        ), _timeout);
            }
        }

        @Override
        public String getBotUsername() {
            return _bot_username;
        }

        @Override
        public String getBotToken() {
            return _token;
        }
    }

    @Override
    public void start() {
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(_bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
