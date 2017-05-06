package ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import ru.hh.resumebuilderbot.http.response.entity.Institute;

public class InstituteTelegramConverter implements EntityTelegramConverter<Institute> {
    @Override
    public InlineQueryResult convert(Institute institute) {
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(institute.getText());
        InlineQueryResultArticle inlineQueryResult = new InlineQueryResultArticle();
        inlineQueryResult
                .setId(institute.getId())
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
