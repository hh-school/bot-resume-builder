package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.message.handler.MessageHandler;
import ru.hh.resumebuilderbot.message.handler.QuestionGeneratorsQueue;

import java.util.Queue;

public class BotBody implements AbstractBotBody {
    private MessengerAdapter messengerAdapter;

    @Override
    public void answer(Answer answer, int timeoutMs) {
        MessageHandler messageHandler = Selector.select(answer);
        QuestionGeneratorsQueue questionGeneratorQueue = messageHandler.handle(answer);
        ChatId chatId = answer.getChatId();
        Queue<Question> questions = questionGeneratorQueue.generateQuestions(chatId);
        questions.forEach(messengerAdapter::ask);
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
}
