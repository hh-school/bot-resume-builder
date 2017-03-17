package ru.hh.resumebuilderbot;

public class Answer {
    private ChatId chatId;
    private Object answerBody;

    public Answer(ChatId chatId, Object answerBody) {
        this.chatId = chatId;
        this.answerBody = answerBody;
    }

    public ChatId getChatId() {
        return chatId;
    }

    public Object getAnswerBody() {
        return answerBody;
    }
}
