package ru.hh.resumebuilderbot.telegram.adapter;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.BotBody;
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.telegram.adapter.answer.TelegramAnswer;
import ru.hh.resumebuilderbot.telegram.adapter.answer.TelegramAnswerFactory;

/**
 * Created by Sergey on 20.04.2017.
 */
public class BotImpl extends TelegramLongPollingBot {
    private final String token;
    private final String botUsername;
    private BotBody botBody;

    public BotImpl(String token, String botUsername, BotBody botBody){
        this.token = token;
        this.botUsername = botUsername;
        this.botBody = botBody;
    }

    @Override
    public void onUpdateReceived(Update update) {
        TelegramAnswer telegramAnswer = TelegramAnswerFactory.create(update);
        if (telegramAnswer != null) {
            long chatId = telegramAnswer.getChatId();
            User user = new User(chatId);
            String answerText = telegramAnswer.getAnswerText();
            Answer answer = new Answer(answerText);
            this.botBody.answer(user, answer);
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
