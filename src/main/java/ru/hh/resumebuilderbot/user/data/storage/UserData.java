package ru.hh.resumebuilderbot.user.data.storage;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    private QuestionNode questionNode;
    private List<UserAnswer> answers = new ArrayList<>();

    UserData(QuestionNode rootQuestionNode) {
        questionNode = rootQuestionNode;
    }

    void registerAnswer(Answer answer) {
        questionNode.saveAnswer(this, answer);
    }

    void moveForward() {
        questionNode = questionNode.getNext();
    }

    Question getCurrentQuestion() {
        return questionNode.getQuestion();
    }

    boolean answerIsValid(Answer answer) {
        return questionNode.answerIsValid(answer);
    }

    boolean currentNodeIsSkippable() {
        return questionNode.isSkippable();
    }

    public void savePhoneNumber(String phoneNumber) {
    }
}
