package ru.hh.resumebuilderbot;

class TelegramAdapter implements MessengerAdapter {
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
        listener.onAnswer(answer, 0);
    }

    // Method stub
    @Override
    public void start() {
        ChatId chatId = new ChatId(0);
        listener.onStartChat(chatId);
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
    }
}
