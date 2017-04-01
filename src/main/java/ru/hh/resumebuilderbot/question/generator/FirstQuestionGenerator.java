package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.Question;

public class FirstQuestionGenerator implements QuestionGenerator {


    private final QuestionGenerator questionGenerator;

    public FirstQuestionGenerator() {
        this.questionGenerator = new QuestionGeneratorByNumber(0);
    }

    @Override
    public Question generateNext() {
        return questionGenerator.generateNext();
    }
}
