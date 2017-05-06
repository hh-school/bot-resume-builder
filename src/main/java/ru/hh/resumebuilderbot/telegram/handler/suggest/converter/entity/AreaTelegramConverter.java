package ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import ru.hh.resumebuilderbot.http.response.entity.Area;

public class AreaTelegramConverter implements EntityTelegramConverter<Area> {
    @Override
    public InlineQueryResult convert(Area area) {
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(area.getText());
        return new InlineQueryResultArticle()
                .setId(area.getId())
                .setTitle(area.getText())
                .setInputMessageContent(messageContent);
    }
}
