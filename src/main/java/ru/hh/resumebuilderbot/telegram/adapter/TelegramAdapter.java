package ru.hh.resumebuilderbot.telegram.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.hh.resumebuilderbot.MessengerAdapter;
import ru.hh.resumebuilderbot.question.Question;

import java.util.ArrayList;
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

        message.setReplyMarkup(getKeyboard(question));
        try {
            bot.sendMessage(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private ReplyKeyboard getKeyboard(Question question) {
        ReplyKeyboard keyboard;
        switch (question.getReplyKeyboard()) {
            case PHONE_NUMBER:
                keyboard = generatePhoneNumberKeyboard();
                break;
            case VARIANTS_OF_ANSWER:
                keyboard = generateVariantsOfAnswerKeyboard(question.getVariantsOfAnswer());
                break;
            case SUGGEST:
                keyboard = generateSuggestInlineKeyboard();
                break;
            default:
                keyboard = new ReplyKeyboardRemove();
                break;
        }
        return keyboard;
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

    private ReplyKeyboardMarkup generateVariantsOfAnswerKeyboard(List<String> variantsOfAnswer) {
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

    private ReplyKeyboardMarkup generatePhoneNumberKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        KeyboardButton currentPhone = new KeyboardButton("Ввести текущий номер телефона");
        currentPhone.setRequestContact(true);
        firstRow.add(currentPhone);
        keyboardRows.add(firstRow);

        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add("Ввести другой номер телефона");
        keyboardRows.add(secondRow);

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
}
