package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import ru.hh.resumebuilderbot.Answer;

public class StrongSuggestChoiceValidator extends Validator {
    public StrongSuggestChoiceValidator() {
        super("Пожалуйста, выберите вариант из предложенного списка подсказок.");
    }

    @Override
    public boolean answerIsValid(Answer answer) {
        return true;
    }

    @Override
    public Validator clone() {
        return new StrongSuggestChoiceValidator();
    }
}
