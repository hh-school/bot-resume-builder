package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.question.Question;

public interface QuestionGenerator {
    Question generateNext();
}
