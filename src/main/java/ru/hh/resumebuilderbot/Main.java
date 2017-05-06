package ru.hh.resumebuilderbot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.hh.resumebuilderbot.di.GuiceCommonModule;
import ru.hh.resumebuilderbot.di.GuiceProdModule;
import ru.hh.resumebuilderbot.telegram.adapter.BotImpl;
import ru.hh.resumebuilderbot.telegram.adapter.TelegramAdapter;
import ru.hh.resumebuilderbot.telegram.handler.suggest.SuggestHandler;

public class Main {
    private static final String TOKEN_ENV_NAME = "TOKEN";
    private static final String BOT_USERNAME_ENV_NAME = "BOT_USERNAME";

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new GuiceCommonModule(), new GuiceProdModule());
        ApiContextInitializer.init();

        BotBody botBody = injector.getInstance(BotBody.class);

        BotImpl bot = new BotImpl(
                System.getenv(TOKEN_ENV_NAME),
                System.getenv(BOT_USERNAME_ENV_NAME),
                botBody,
                injector.getInstance(SuggestHandler.class)
        );

        MessengerAdapter messengerAdapter = new TelegramAdapter(bot);

        botBody.setMessenger(messengerAdapter);

        messengerAdapter.start();
    }
}
