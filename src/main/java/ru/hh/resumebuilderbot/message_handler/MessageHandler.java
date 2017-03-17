package ru.hh.resumebuilderbot.message_handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question_generator.QuestionGenerator;

public interface MessageHandler {
    public QuestionGenerator handle(Answer answer);
}
