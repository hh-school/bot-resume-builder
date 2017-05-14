package ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity;

import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;

public interface EntityTelegramConverter<T> {
    InlineQueryResultArticle convert(T suggestEntity);
}
