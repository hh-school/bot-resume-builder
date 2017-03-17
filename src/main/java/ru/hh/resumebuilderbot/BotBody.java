package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.MessageHandler.MessageHandler;

import java.util.Map;

public class BotBody implements AbstractBotBody {
    private MessengerAdapter messengerAdapter;
    private Map<ChatId, UserData> userData;

    BotBody(MessengerAdapter messengerAdapter) {
        this.messengerAdapter = messengerAdapter;
        messengerAdapter.setHandler(this);
    }

    @Override
    public void onAnswer(Answer answer, int timeoutMs) {
        Selector selector = new Selector(answer);
        MessageHandler messageHandler = selector.select();
        QuestionGenerator nextQuestionGenerator = messageHandler.handle(answer);
        messengerAdapter.ask(nextQuestionGenerator.generate(answer.getChatId()), 1000);
    }

    @Override
    public void onStartChat(ChatId chatId) {
        // todo: run in thread of BotBody
        Question question = new Question(chatId, "question text");
        messengerAdapter.ask(question, 0);
    }
}
