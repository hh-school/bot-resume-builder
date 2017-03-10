package ru.hh.resumebuilderbot;

// Реализация мессенджера, отображающего диалог в консоли
class ConsoleMessengerAdapter implements MessengerAdapter {
    private AbstractBotBody listener;

    @Override
    public void setListener(AbstractBotBody bot) {
        listener = bot;
    }

    @Override
    public void ask(Question question, int timeoutMs) {
        System.out.println("question: " + question.getText());
    }

    private void onAnswer(Answer answer) {
        System.out.println("answer: " + answer.getAnswerBody());
        listener.onAnswer(answer, 0);
    }

    // Messenger stub
    @Override
    public void start() {
        ChatId chatId = new ChatId(0);
        listener.onStartChat(chatId);
        Thread thread = new Thread(() -> {
            while (true) {
                // Simulate user answer
                Answer answer = new Answer(chatId, "answer text");
                onAnswer(answer);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        thread.start();
    }
}
