package ru.hh.resumebuilderbot;

public class Main {
    public static void main(String[] args) {
        // connect to database
        // ...

        BotBody bot = new BotBody();

        // connect to telegram server
        MessengerAdapter messenger = new TelegramAdapter();
        messenger.setListener(bot);

        messenger.start();
        bot.start();
    }
}
