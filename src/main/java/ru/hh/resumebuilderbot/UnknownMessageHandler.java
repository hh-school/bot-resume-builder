package ru.hh.resumebuilderbot;

import java.util.Map;

public class UnknownMessageHandler implements MessageHandler {
    @Override
    public NextQuestionGenerator handle(Map<ChatId, UserData> userData, Answer answer) {
        return new ;
    }
}
