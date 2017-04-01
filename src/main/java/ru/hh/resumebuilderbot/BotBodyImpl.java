package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.message.handler.MessageHandler;
import ru.hh.resumebuilderbot.question.generator.QuestionGeneratorsQueue;

import java.util.Queue;

public class BotBodyImpl implements BotBody {
    private MessengerAdapter messengerAdapter;

    @Override
    public void answer(ChatId chatId, Answer answer) {
        MessageHandler messageHandler = Selector.select(answer);
        QuestionGeneratorsQueue questionGeneratorQueue = messageHandler.handle(chatId, answer);
        Queue<Question> questions = questionGeneratorQueue.generateQuestions(chatId);
        questions.forEach((x) -> messengerAdapter.ask(chatId, x));
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
