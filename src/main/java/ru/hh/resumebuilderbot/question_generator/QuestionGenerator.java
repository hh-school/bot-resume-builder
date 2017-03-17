package ru.hh.resumebuilderbot.question_generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;

public interface QuestionGenerator {
    public Question generateNext(ChatId chatId);
}
