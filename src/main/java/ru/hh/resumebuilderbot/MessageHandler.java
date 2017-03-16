package ru.hh.resumebuilderbot;

import java.util.Map;

public interface MessageHandler {
    public NextQuestionGenerator handle(Map<ChatId, UserData> userData, Answer answer);
}
