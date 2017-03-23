package ru.hh.resumebuilderbot;

import java.util.List;

class Question {
    private final ChatId chatId;
    private final String text;
// todo: add answerType and allowedAnswers fields
//    private ru.hh.resumebuilderbot.AnswerType answerType;

    public List<String> allowedAnswers;

    Question(ChatId chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }

    ChatId getChatId() {
        return chatId;
    }

    String getText() {
        return text;
    }
}
