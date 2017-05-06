package ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import ru.hh.resumebuilderbot.http.response.entity.Specialization;

public class SpecializationTelegramConverter implements EntityTelegramConverter<Specialization> {
    @Override
    public InlineQueryResult convert(Specialization specialization) {
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(specialization.getText());
        return new InlineQueryResultArticle()
                .setId(specialization.getId())
                .setTitle(specialization.getText())
                .setInputMessageContent(messageContent);
    }
}
