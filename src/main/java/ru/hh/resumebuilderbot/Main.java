package ru.hh.resumebuilderbot;

import org.telegram.telegrambots.ApiContextInitializer;

public class Main {
    public static void main(String[] args) {
        // connect to database
        // ...

	ApiContextInitializer.init();
        MessengerAdapter messengerAdapter = new TelegramAdapter(
                "",
                "",
                1000);
        // connect to telegram server
        BotBody bot = new BotBody();

        messengerAdapter.setHandler(bot);
        bot.setMessenger(messengerAdapter);

        // configure connection to job site
        // ...

        messengerAdapter.start();
    }
}
