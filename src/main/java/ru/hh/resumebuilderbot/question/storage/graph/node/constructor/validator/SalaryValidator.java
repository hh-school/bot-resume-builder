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
            Integer salary = Integer.parseInt((String) answer.getAnswerBody());
            return salary > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
