package ru.hh.resumebuilderbot;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private final ChatId chatId;
    private final String text;

    private List<String> allowedAnswers = new ArrayList<>();

    public Question(ChatId chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }

    public List<String> getAllowedAnswers() {
        return allowedAnswers;
    }

    public void setAllowedAnswers(List<String> allowedAnswers) {
        this.allowedAnswers = allowedAnswers;
    }

    public ChatId getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }
}
