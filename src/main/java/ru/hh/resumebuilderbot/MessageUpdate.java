package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.question.ReplyKeyboardEnum;

public class MessageUpdate {
    private Long telegramId;
    private Integer messageId;
    private String updatedText = null;
    private ReplyKeyboardEnum replyKeyboardEnum = ReplyKeyboardEnum.NO_KEYBOARD_NEEDED;
    private String keyboardData = null;

    public MessageUpdate(Long telegramId, Integer messageId) {
        this.telegramId = telegramId;
        this.messageId = messageId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public ReplyKeyboardEnum getReplyKeyboardEnum() {
        return replyKeyboardEnum;
    }

    public void setReplyKeyboardEnum(ReplyKeyboardEnum replyKeyboardEnum) {
        this.replyKeyboardEnum = replyKeyboardEnum;
    }

    public String getKeyboardData() {
        return keyboardData;
    }

    public void setKeyboardData(String keyboardData) {
        this.keyboardData = keyboardData;
    }

    public String getUpdatedText() {
        return updatedText;
    }

    public void setUpdatedText(String updatedText) {
        this.updatedText = updatedText;
    }

    public boolean isValid() {
        return (hasText() || hasKeyboard());
    }

    public boolean hasKeyboard() {
        return replyKeyboardEnum != ReplyKeyboardEnum.NO_KEYBOARD_NEEDED;
    }

    public boolean hasText() {
        return updatedText != null;
    }
}
