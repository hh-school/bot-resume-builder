package ru.hh.resumebuilderbot.telegram.adapter;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.BotBody;
import ru.hh.resumebuilderbot.MessengerAdapter;
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.telegram.adapter.answer.TelegramAnswer;
import ru.hh.resumebuilderbot.telegram.adapter.answer.TelegramAnswerFactory;

import java.util.ArrayList;
import java.util.List;

public class TelegramAdapter implements MessengerAdapter {
    private final String token;
    private final String botUsername;
    private final BotImpl bot;

    private BotBody botBody;

    public TelegramAdapter(String token, String botUsername) {
        this.token = token;
        this.botUsername = botUsername;
        this.bot = new BotImpl();
    }

    @Override
    public void setBotBody(BotBody botBody) {
        this.botBody = botBody;
    }

    @Override
    public void ask(User user, Question question) {
        SendMessage msg = new SendMessage()
                .setChatId(user.getIndex())
                .setText(question.getText());

        List<String> allowedAnswers = question.getAllowedAnswers();

        if (!allowedAnswers.isEmpty()) {
            msg.setReplyMarkup(generateMarkup(allowedAnswers));
        }
        try {
            bot.sendMsg(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup generateMarkup(List<String> allowedAnswers) {
        InlineKeyboardMarkup result = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (String allowedAnswer : allowedAnswers) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton()
                    .setText(allowedAnswer)
                    .setCallbackData(allowedAnswer));
            rowsInline.add(rowInline);
        }
        result.setKeyboard(rowsInline);
        return result;
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

    private class BotImpl extends TelegramLongPollingBot {
        void sendMsg(SendMessage msg) throws TelegramApiException {
            sendMessage(msg);
        }

        @Override
        public void onUpdateReceived(Update update) {
            TelegramAnswer telegramAnswer = TelegramAnswerFactory.create(update);
            if (telegramAnswer != null) {


                long chatId = telegramAnswer.getChatId();
                User user = new User(chatId);
                String answerText = telegramAnswer.getAnswerText();
                Answer answer = new Answer(answerText);
                botBody.answer(user, answer);
            }
        }

        @Override
        public String getBotUsername() {
            return botUsername;
        }

        @Override
        public String getBotToken() {
            return token;
        }
    }
}
