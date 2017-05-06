package ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import ru.hh.resumebuilderbot.http.response.entity.Company;

public class CompanyTelegramConverter implements EntityTelegramConverter<Company> {
    @Override
    public InlineQueryResult convert(Company company) {
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(company.getName());
        return new InlineQueryResultArticle()
                .setId(company.getId())
                .setThumbUrl(company.getLogoUrl())
                .setTitle(company.getName())
                .setDescription(company.getUrl())
                .setInputMessageContent(messageContent);
    }
}
