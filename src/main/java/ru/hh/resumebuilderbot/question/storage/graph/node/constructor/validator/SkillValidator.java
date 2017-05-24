package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import ru.hh.resumebuilderbot.Answer;

public class SkillValidator extends Validator {
    private static final int minLength = 1;
    private static final int maxLength = 100;

    @Override
    public boolean answerIsValid(Answer answer) {
        String skill = (String) answer.getAnswerBody();
        return skill.length() >= minLength && skill.length() <= maxLength;
    }

    @Override
    public Validator clone() {
        return new SkillValidator();
    }
}
