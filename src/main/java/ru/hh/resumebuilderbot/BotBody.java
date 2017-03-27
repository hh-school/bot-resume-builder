package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.message.handler.MessageHandler;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;

import java.util.Map;

public class BotBody implements AbstractBotBody {
    private MessengerAdapter messengerAdapter;
    private Map<ChatId, UserData> userData;

    @Override
    public void answer(Answer answer, int timeoutMs) {
        MessageHandler messageHandler = Selector.select(answer);
        QuestionGenerator questionGenerator = messageHandler.handle(answer);
        messengerAdapter.ask(questionGenerator.generateNext(answer.getChatId()));
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
}
