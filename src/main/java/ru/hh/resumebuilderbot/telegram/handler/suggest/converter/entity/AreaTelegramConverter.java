package ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import ru.hh.resumebuilderbot.http.response.entity.Area;

public class AreaTelegramConverter implements EntityTelegramConverter<Area> {
    @Override
    public InlineQueryResultArticle convert(Area area) {
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(area.getText());
        return new InlineQueryResultArticle()
                .setTitle(area.getText())
                .setInputMessageContent(messageContent);
    }
}
