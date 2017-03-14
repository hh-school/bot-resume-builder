package ru.hh.resumebuilderbot;

public class Main {
    public static void main(String[] args) {
        // connect to database
        // ...

        MessengerAdapter messengerAdapter = new ConsoleMessengerAdapter();
        // connect to telegram server
        BotBody bot = new BotBody(messengerAdapter);

        // configure connection to job site
        // ...

        messengerAdapter.start();
    }
}
