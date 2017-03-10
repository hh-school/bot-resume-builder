package ru.hh.resumebuilderbot;

public class Main {
    public static void main(String[] args) {
        // connect to database
        // ...

        MessengerAdapter messenger = new ConsoleMessengerAdapter();
        BotBody bot = new BotBody(messenger);

        // connect to telegram server
        messenger.setListener(bot);

        // connect to job site
        // ...

        messenger.start();
        bot.start();
    }
}
