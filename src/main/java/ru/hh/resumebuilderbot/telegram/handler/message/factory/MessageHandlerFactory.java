package ru.hh.resumebuilderbot.telegram.handler.message.factory;

import ru.hh.resumebuilderbot.telegram.handler.message.MessageHandler;

public interface MessageHandlerFactory {
    MessageHandler get();
}
