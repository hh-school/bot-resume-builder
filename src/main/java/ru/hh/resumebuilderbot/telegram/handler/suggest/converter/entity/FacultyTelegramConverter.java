package ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import ru.hh.resumebuilderbot.http.response.entity.Faculty;

public class FacultyTelegramConverter implements EntityTelegramConverter<Faculty> {
    @Override
    public InlineQueryResult convert(Faculty faculty) {
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(faculty.getName());
        return new InlineQueryResultArticle()
                .setId(faculty.getId())
                .setTitle(faculty.getName())
                .setInputMessageContent(messageContent);
    }
}
