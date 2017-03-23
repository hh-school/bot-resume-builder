package ru.hh.resumebuilderbot;

import java.util.List;

public class Question {
    private final ChatId chatId;
    private final String text;
// todo: add answerType and allowedAnswers fields
//    private ru.hh.resumebuilderbot.AnswerType answerType;

    public List<String> allowedAnswers;

    public Question(ChatId chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }

    public ChatId getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }
}
