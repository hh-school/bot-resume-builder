package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.message.handler.MessageHandler;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;

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
        MessageHandler messageHandler = Selector.select(answer);
        QuestionGenerator questionGenerator = messageHandler.handle(answer);
        messengerAdapter.ask(questionGenerator.generateNext(answer.getChatId()));
    }

    @Override
    public void onStartChat(ChatId chatId) {
        // todo: run in thread of BotBody
        Question question = new Question(chatId, "question text");
        messengerAdapter.ask(question);
    }
}
