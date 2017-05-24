package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import ru.hh.resumebuilderbot.Answer;

public class SalaryValidator extends Validator {
    public SalaryValidator() {
        super("Описание прекрасное, но мы же говорим про деньги? Введите желаемое число цифрами");
    }

    @Override
    public Validator clone() {
        return new SalaryValidator();
    }

    @Override
    public boolean answerIsValid(Answer answer) {
        try {
            return Integer.parseInt((String) answer.getAnswerBody()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
