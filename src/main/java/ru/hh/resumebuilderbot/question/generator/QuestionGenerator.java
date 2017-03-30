package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;

public interface QuestionGenerator {
    Question generateNext(ChatId chatId);

    public void setPrefix(String prefix);
}
