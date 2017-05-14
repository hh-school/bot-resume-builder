package ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import ru.hh.resumebuilderbot.http.response.entity.Institute;

public class InstituteTelegramConverter implements EntityTelegramConverter<Institute> {
    @Override
    public InlineQueryResultArticle convert(Institute institute) {
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(institute.getText());
        InlineQueryResultArticle inlineQueryResult = new InlineQueryResultArticle();
        inlineQueryResult
                .setInputMessageContent(messageContent);
        if (institute.getAcronym().equals("")) {
            inlineQueryResult.setTitle(institute.getText());
        } else {
            inlineQueryResult.setTitle(institute.getAcronym());
            inlineQueryResult.setDescription(institute.getText());
        }
        return inlineQueryResult;
    }
}
