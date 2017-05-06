package ru.hh.resumebuilderbot.telegram.handler.suggest.converter.entity;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import ru.hh.resumebuilderbot.http.response.entity.Skill;

public class SkillTelegramConverter implements EntityTelegramConverter<Skill> {
    @Override
    public InlineQueryResult convert(Skill skill) {
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(skill.getText());
        return new InlineQueryResultArticle()
                .setId(skill.getId())
                .setTitle(skill.getText())
                .setInputMessageContent(messageContent);
    }
}
