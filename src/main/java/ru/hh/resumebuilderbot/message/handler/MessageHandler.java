package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;

public interface MessageHandler {
    QuestionGenerator handle(Answer answer);
}
