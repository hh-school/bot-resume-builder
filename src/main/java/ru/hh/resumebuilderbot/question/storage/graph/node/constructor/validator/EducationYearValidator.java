package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import ru.hh.resumebuilderbot.Answer;

public class EducationYearValidator extends Validator {
    private static final int MAX_YEAR = 2025;
    private static final int MIN_YEAR = 1950;

    public EducationYearValidator() {
        super("Пожалуйста, выберите корректный год.");
    }

    @Override
    public boolean answerIsValid(Answer answer) {
        try {
            int year = Integer.parseInt(answer.getAnswerBody().toString());
            return year > MIN_YEAR && year < MAX_YEAR;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Validator clone() {
        return new EducationYearValidator();
    }
}
