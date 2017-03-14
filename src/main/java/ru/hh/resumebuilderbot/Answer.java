package ru.hh.resumebuilderbot;

class Answer {
    private ChatId chatId;
    private Object answerBody;

    Answer(ChatId chatId, Object answerBody) {
        this.chatId = chatId;
        this.answerBody = answerBody;
    }

    ChatId getChatId() {
        return chatId;
    }

    Object getAnswerBody() {
        return answerBody;
    }
}
