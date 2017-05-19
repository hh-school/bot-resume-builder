package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.database.model.User;

import java.util.regex.Pattern;

public class PhoneNumberValidator extends PatternValidator {
    public PhoneNumberValidator() {
        pattern = Pattern.compile("^\\+?[\\d()-]{7,20}+$");
        notification = "Хм, это точно номер телефона? Как же расстроится работодатель, " +
                "когда не сможет с вами связаться! Давайте попробуем еще раз!";
    }

    @Override
    public Validator clone() {
        return new PhoneNumberValidator();
    }

    @Override
    public boolean answerIsValid(Answer answer) {
        String answerAsString = (String) answer.getAnswerBody();
        return answerAsString.length() <= User.PHONE_LENGTH && pattern.matcher(answerAsString).matches();
    }
}
