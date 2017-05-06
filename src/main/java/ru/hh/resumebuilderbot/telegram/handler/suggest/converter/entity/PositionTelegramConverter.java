package ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import ru.hh.resumebuilderbot.http.response.entity.Position;

public class PositionTelegramConverter implements EntityTelegramConverter<Position> {
    @Override
    public InlineQueryResult convert(Position position) {
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(position.getText());
        return new InlineQueryResultArticle()
                .setId(position.getId())
                .setTitle(position.getText())
                .setInputMessageContent(messageContent);
    }
}
