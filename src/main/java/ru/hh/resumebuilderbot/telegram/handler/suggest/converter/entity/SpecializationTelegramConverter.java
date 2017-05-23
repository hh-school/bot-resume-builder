package ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import ru.hh.resumebuilderbot.http.response.entity.StudyField;

public class SpecializationTelegramConverter implements EntityTelegramConverter<StudyField> {
    @Override
    public InlineQueryResultArticle convert(StudyField studyField) {
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(studyField.getText());
        return new InlineQueryResultArticle()
                .setTitle(studyField.getText())
                .setInputMessageContent(messageContent);
    }
}
