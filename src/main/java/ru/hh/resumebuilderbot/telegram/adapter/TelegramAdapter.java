package ru.hh.resumebuilderbot.telegram.adapter;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.hh.resumebuilderbot.MessengerAdapter;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.QuestionSuggest;

import java.util.ArrayList;
import java.util.List;

public class TelegramAdapter implements MessengerAdapter {
    private final BotImpl bot;

    public TelegramAdapter(BotImpl bot) {
        this.bot = bot;
    }

    @Override
    public void ask(Long telegramId, Question question) {
        SendMessage message = new SendMessage()
                .setChatId(telegramId)
                .setText(question.getText());

        List<String> variantsOfAnswer = question.getVariantsOfAnswer();
        if (!variantsOfAnswer.isEmpty()) {
            message.setReplyMarkup(generateReplyKeyboard(variantsOfAnswer));
        } else if (question.getSuggestField().equals(QuestionSuggest.NoSuggestNeeded)) {
            message.setReplyMarkup(new ReplyKeyboardRemove());
        } else {
            message.setReplyMarkup(generateSuggestInlineKeyboard());
        }
        try {
            bot.saveCurrentQuestion(telegramId, question);
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

    private InlineKeyboardMarkup generateSuggestInlineKeyboard() {
        InlineKeyboardMarkup result = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton()
                .setSwitchInlineQueryCurrentChat("")
                .setText("Нажмите для получения вариантов-подсказки"));
        rowsInline.add(rowInline);
        result.setKeyboard(rowsInline);
        return result;
    }

    private ReplyKeyboardMarkup generateReplyKeyboard(List<String> variantsOfAnswer) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        for (String variant : variantsOfAnswer) {
            KeyboardRow keyboardRow = new KeyboardRow();
            KeyboardButton button = new KeyboardButton(variant);
            keyboardRow.add(button);
            keyboardRows.add(keyboardRow);
        }
        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setOneTimeKeyboad(true);
        return keyboardMarkup;
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
