package ru.hh.resumebuilderbot.user.data.storage;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.QuestionsStorage;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;

import java.util.ArrayList;
import java.util.List;

public class UserData {

    private QuestionNode questionNode;
    private List<UserAnswer> answers = new ArrayList<>();

    public UserData() {
        questionNode = QuestionsStorage.getRoot();
    }

    void registerAnswer(Answer answer) {
        questionNode.registerAnswer(answer);
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

    private void saveAnswer(Answer answer) {
        Question currentQuestion = questionNode.getQuestion();
        answers.add(new UserAnswer(currentQuestion, answer));
    }


    public void savePhoneNumber(String phoneNumber)
    {}
}
