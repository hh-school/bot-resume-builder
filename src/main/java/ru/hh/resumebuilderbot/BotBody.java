package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.message.handler.MessageHandler;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;

import java.util.Queue;

public class BotBody implements AbstractBotBody {
    private MessengerAdapter messengerAdapter;

    @Override
    public void answer(Answer answer, int timeoutMs) {
        MessageHandler messageHandler = Selector.select(answer);
        Queue<QuestionGenerator> questionGenerator = messageHandler.handle(answer);
        questionGenerator.forEach((x) -> messengerAdapter.ask(x.generateNext(answer.getChatId())));
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
}
