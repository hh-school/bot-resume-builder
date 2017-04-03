package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.message.handler.MessageHandler;
import ru.hh.resumebuilderbot.message.handler.QuestionGeneratorsQueue;

public class BotBody implements AbstractBotBody {
    private MessengerAdapter messengerAdapter;

    @Override
    public void answer(Answer answer, int timeoutMs) {
        MessageHandler messageHandler = Selector.select(answer);
        QuestionGeneratorsQueue questionGeneratorQueue = messageHandler.handle(answer);
        questionGeneratorQueue.forEach((x) -> messengerAdapter.ask(x.generateNext(answer.getChatId())));
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
}
