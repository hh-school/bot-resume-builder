package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import ru.hh.resumebuilderbot.Answer;

import java.util.regex.Pattern;

public abstract class PatternValidator extends Validator {

    protected Pattern pattern;

    @Override
    public boolean answerIsValid(Answer answer) {
        String answerAsString = (String) answer.getAnswerBody();
        return pattern.matcher(answerAsString).matches();
    }
}
