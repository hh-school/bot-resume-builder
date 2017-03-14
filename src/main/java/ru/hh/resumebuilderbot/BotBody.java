package ru.hh.resumebuilderbot;

public class BotBody implements AbstractBotBody {
    private MessengerAdapter messengerAdapter;

    BotBody(MessengerAdapter messengerAdapter) {
        this.messengerAdapter = messengerAdapter;
        messengerAdapter.setHandler(this);
    }

    @Override
    public void onAnswer(Answer answer, int timeoutMs) {
        Question question = new Question(answer.getChatId(), "question text");
        messengerAdapter.ask(question, 0);
    }

    @Override
    public void onStartChat(ChatId chatId) {
        // todo: run in thread of BotBody
        Question question = new Question(chatId, "question text");
        messengerAdapter.ask(question, 0);
    }
}
