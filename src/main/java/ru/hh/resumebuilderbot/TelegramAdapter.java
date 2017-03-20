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
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class TelegramAdapter implements MessengerAdapter {
    private final String _token;
    private final String _bot_username;
    private final Map<Long, Queue<Question>> _questions;
    private final BotImpl _bot;
    private int _timeout;

    private AbstractBotBody _handler;

    TelegramAdapter(String token, String botUsername, AbstractBotBody handler, int timeoutMs) {
        _token = token;
        _bot_username = botUsername;
        _handler = handler;
        _questions = new ConcurrentHashMap<>();
        _bot = new BotImpl();
        _timeout = timeoutMs;
    }

    public void processNextQuestion(long _chat_id) {
        Queue<Question> queue = _questions.get(_chat_id);

        if (queue == null || queue.isEmpty()) {
            return;
        }

        Question question = queue.poll();
        SendMessage msg = new SendMessage()
                .setChatId(_chat_id)
                .setText(question.getText());

        if (!question.allowedAnswers.isEmpty()) {
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            
            for (int i=0; i < question.allowedAnswers.size(); ++i) {
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                rowInline.add(new InlineKeyboardButton()
                        .setText(question.allowedAnswers.get(i))
                        .setCallbackData(question.allowedAnswers.get(i)));
                rowsInline.add(rowInline);
            }

            markupInline.setKeyboard(rowsInline);
            msg.setReplyMarkup(markupInline);
        }
        try {
            _bot.sendMsg(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ask(Question question, int timeoutMs) {
        long _chat_id = question.getChatId().getId();
        Queue<Question> queue = _questions.get(_chat_id);

        if (queue == null) {
            queue = new LinkedBlockingQueue<>();
        }

        queue.add(question);
        _questions.put(_chat_id, queue);

        processNextQuestion(_chat_id);
    }

    private class BotImpl extends TelegramLongPollingBot {
        public void sendMsg(SendMessage msg) throws TelegramApiException {
            sendMessage(msg);
        }

        @Override
        public void onUpdateReceived(Update update) {
            if (update.hasMessage() && update.getMessage().hasText()) {
                long _chat_id = update.getMessage().getChatId();

                if (update.getMessage().getText().equals("/start")) {
                    _handler.onStartChat(new ChatId(_chat_id));
                } else {
                    _handler.onAnswer(
                            new Answer(
                                    new ChatId(_chat_id),
                                    update.getMessage().getText()),
                            _timeout);
                }
            } else if (update.hasCallbackQuery()) {
                String _call_data = update.getCallbackQuery().getData();
                long _chat_id = update.getCallbackQuery().getMessage().getChatId();

                _handler.onAnswer(
                        new Answer(
                                new ChatId(_chat_id),
                                _call_data),
                        _timeout);
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
    public void setHandler(AbstractBotBody bot) {
        _handler = bot;
    }

    @Override
    public void start() {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(_bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
