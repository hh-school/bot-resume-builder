package ru.hh.resumebuilderbot.question.storage.node.prod;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.node.basic.QuestionNodeLinear;
import ru.hh.resumebuilderbot.user.data.storage.UserData;

public class QuestionNodeTelephone extends QuestionNodeLinear {
    public QuestionNodeTelephone(Question question, boolean isSkippable) {
        super(question, isSkippable);
    }

    @Override
    public void saveAnswer(UserData dest, Answer answer) {
        dest.savePhoneNumber((String)answer.getAnswerBody());
    }
}
