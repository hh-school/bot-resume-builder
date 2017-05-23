package ru.hh.resumebuilderbot.telegram.handler.suggest;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;

import java.util.ArrayList;
import java.util.List;

public class NotificationInlineQueryResults {
    public static List<InlineQueryResult> getShortQueryErrorResult() {
        List<InlineQueryResult> inlineQueryResults = new ArrayList<>(1);
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(TextsStorage.getText(TextId.NOTHING_SELECTED));
        InlineQueryResultArticle inlineQueryResult = new InlineQueryResultArticle()
                .setId("51")
                .setInputMessageContent(messageContent)
                .setDescription(TextsStorage.getText(TextId.NEED_MORE_ONE_SYMBOL))
                .setTitle(TextsStorage.getText(TextId.CONTINUE_INPUT));
        inlineQueryResults.add(inlineQueryResult);
        return inlineQueryResults;
    }

    public static List<InlineQueryResult> getNotFoundErrorResult(String query) {
        List<InlineQueryResult> inlineQueryResults = new ArrayList<>(1);
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(query);
        InlineQueryResultArticle inlineQueryResult = new InlineQueryResultArticle()
                .setId("52")
                .setInputMessageContent(messageContent)
                .setDescription(String.format("Это всё, что нам удалось найти по запросу '%s'. Будет лучше, если " +
                        "вы постараетесь выбрать вариант из списка, но если вы уверены, что ввели всё правильно, " +
                        "выбирайте этот вариант", query))
                .setTitle(String.format("По запросу '%s' ничего не найдено", query));
        inlineQueryResults.add(inlineQueryResult);
        return inlineQueryResults;
    }

    public static List<InlineQueryResult> getNonFacultiesInstituteResult() {
        List<InlineQueryResult> inlineQueryResults = new ArrayList<>(1);
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(TextsStorage.getText(TextId.NO_FACULTIES_FOUND));
        InlineQueryResultArticle inlineQueryResult = new InlineQueryResultArticle()
                .setId("53")
                .setInputMessageContent(messageContent)
                .setDescription(TextsStorage.getText(TextId.NO_FACULTIES_FOUND_DESCRIPTION))
                .setTitle(TextsStorage.getText(TextId.NO_FACULTIES_FOUND));
        inlineQueryResults.add(inlineQueryResult);
        return inlineQueryResults;
    }
}
