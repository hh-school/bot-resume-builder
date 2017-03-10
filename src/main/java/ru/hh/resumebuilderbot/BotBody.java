package ru.hh.resumebuilderbot;

import java.util.concurrent.LinkedBlockingQueue;

// Реализация логики бота
public class BotBody implements AbstractBotBody {
    private LinkedBlockingQueue<Answer> answersQueue;
    private MessengerAdapter messenger;

    BotBody(MessengerAdapter messenger) {
        this.messenger = messenger;
        this.answersQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void onAnswer(Answer answer, int timeoutMs) {
        answersQueue.add(answer);
    }

    @Override
    public void onStartChat(ChatId chatId) {
        // todo: run in thread of BotBody
        Question question = new Question(chatId, "question text");
        messenger.ask(question, 0);
    }

    // Bot stub
    @Override
    public void start() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Answer answer = answersQueue.take();
                    Question question = new Question(answer.getChatId(), "question text");
                    messenger.ask(question, 0);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        thread.start();
    }
}
