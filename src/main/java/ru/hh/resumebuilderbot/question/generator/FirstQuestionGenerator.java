package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.Question;
import ru.hh.resumebuilderbot.questions.storage.QuestionsStorage;

public class FirstQuestionGenerator implements QuestionGenerator {


    @Override
    public Question generateNext() {
        return QuestionsStorage.getFirstQuestion();
    }
}
