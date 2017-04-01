package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;

import java.util.Queue;

public interface MessageHandler {
    Queue<QuestionGenerator> handle(Answer answer);
}
