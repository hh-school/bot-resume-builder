package ru.hh.resumebuilderbot;

class Question {
    private final ChatId chatId;
    private final String text;
//    private ru.hh.resumebuilderbot.AnswerType answerType;
//    private List<String> allowedAnswers;

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

