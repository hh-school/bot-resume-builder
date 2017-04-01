package ru.hh.resumebuilderbot;

import org.telegram.telegrambots.ApiContextInitializer;
import ru.hh.resumebuilderbot.telegram.adapter.TelegramAdapter;

public class Main {
    private static final String TOKEN_ENV_NAME = "TOKEN";
    private static final String BOT_USERNAME_ENV_NAME = "BOT_USERNAME";
    private static final int TIMEOUT = 1000;
    public static void main(String[] args) {
        // connect to database
        // ...

        ApiContextInitializer.init();
        MessengerAdapter messengerAdapter = new TelegramAdapter(
                System.getenv(TOKEN_ENV_NAME),
                System.getenv(BOT_USERNAME_ENV_NAME),
                TIMEOUT);
				
        // connect to telegram server
        BotBody bot = new BotBody();

        messengerAdapter.setHandler(bot);
        bot.setMessenger(messengerAdapter);

        // configure connection to job site
        // ...

        messengerAdapter.start();
    }
}
