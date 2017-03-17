package ru.hh.resumebuilderbot.MessageHandler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.QuestionGenerator;

public interface MessageHandler {
    public QuestionGenerator handle(Answer answer);
}
