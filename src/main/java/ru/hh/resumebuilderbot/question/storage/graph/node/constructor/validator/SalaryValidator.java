package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.database.model.User;

import java.util.regex.Pattern;

public class SalaryValidator extends PatternValidator {
    public SalaryValidator() {
        pattern = Pattern.compile("^[\\d]+$");
        notification = "Описание прекрасное, но мы же говорим про деньги? Введите желаемое число цифрами";
    }

    @Override
    public Validator clone() {
        return new SalaryValidator();
    }

    @Override
    public boolean answerIsValid(Answer answer) {
        String answerAsString = (String) answer.getAnswerBody();
        return answerAsString.length() <= User.PHONE_LENGTH && pattern.matcher(answerAsString).matches();
    }
}