package ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import ru.hh.resumebuilderbot.telegram.handler.suggest.Notification;

public class NotificationTelegramConverter implements EntityTelegramConverter<Notification> {
    @Override
    public InlineQueryResultArticle convert(Notification notification) {
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(notification.getText());
        return new InlineQueryResultArticle()
                .setTitle(notification.getTitle())
                .setDescription(notification.getDescription())
                .setInputMessageContent(messageContent);
    }
}
