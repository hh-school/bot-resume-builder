package ru.hh.resumebuilderbot;

public interface MessageHandler {
    public NextQuestionGenerator handle(Answer answer);
}
