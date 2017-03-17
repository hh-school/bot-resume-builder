package ru.hh.resumebuilderbot.QuestionGenerator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;

public interface QuestionGenerator {
    public Question generateNext(ChatId chatId);
}
