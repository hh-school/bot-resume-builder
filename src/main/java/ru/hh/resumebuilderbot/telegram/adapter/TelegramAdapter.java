package ru.hh.resumebuilderbot.telegram.adapter;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.hh.resumebuilderbot.BotBody;
import ru.hh.resumebuilderbot.MessengerAdapter;
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.question.Question;

import java.util.ArrayList;
import java.util.List;

public class TelegramAdapter implements MessengerAdapter {
    private final TelegramLongPollingBot bot;

    public TelegramAdapter(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    @Override
    public void ask(User user, Question question) {
        SendMessage message = new SendMessage()
                .setChatId(user.getIndex())
                .setText(question.getText());

        List<String> variantsOfAnswer = question.getVariantsOfAnswer();
        if (!variantsOfAnswer.isEmpty()) {
            message.setReplyMarkup(generateInlineKeyboard(variantsOfAnswer));
        } 
        try {
            bot.sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup generateInlineKeyboard(List<String> variantsOfAnswer) {
        InlineKeyboardMarkup result = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (String variant : variantsOfAnswer) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton()
                    .setText(variant)
                    .setCallbackData(variant));
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
}
