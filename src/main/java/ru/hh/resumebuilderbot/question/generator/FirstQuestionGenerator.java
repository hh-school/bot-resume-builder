package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.QuestionsStorage;

public class FirstQuestionGenerator implements QuestionGenerator {


    @Override
    public Question generateNext() {
        return QuestionsStorage.getFirstQuestion();
    }
}
