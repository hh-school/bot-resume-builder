package ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity;

import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;

public interface EntityTelegramConverter<T> {
    InlineQueryResult convert(T suggestEntity, Integer id);
}
