package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.database.model.gender.Gender;

public class GenderValidator extends Validator {
    public GenderValidator() {
        super("Пожалуйста, выберите один из предложенных вариантов, нажав на соответствующую кнопку.");
    }

    public static Gender getGenderFromCode(String code) {
        code = code.split(" ")[0];
        if (code.equals("Господин")) {
            return Gender.MALE;
        }
        if (code.equals("Госпожа")) {
            return Gender.FEMALE;
        }
        return null;
    }

    @Override
    public boolean answerIsValid(Answer answer) {
        return getGenderFromCode(answer.getAnswerBody().toString()) != null;
    }

    @Override
    public Validator clone() {
        return new GenderValidator();
    }
}
