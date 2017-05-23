package ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import ru.hh.resumebuilderbot.http.response.entity.Position;
import ru.hh.resumebuilderbot.http.response.entity.Specialization;

import java.util.stream.Collectors;

public class PositionTelegramConverter implements EntityTelegramConverter<Position> {
    @Override
    public InlineQueryResultArticle convert(Position position) {
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(position.getName());
        String description = String.join(
                ", ",
                position.getSpecializations().stream()
                        .map(Specialization::getName)
                        .collect(Collectors.toList())
        );
        return new InlineQueryResultArticle()
                .setTitle(position.getName())
                .setInputMessageContent(messageContent)
                .setDescription(description);
    }
}
