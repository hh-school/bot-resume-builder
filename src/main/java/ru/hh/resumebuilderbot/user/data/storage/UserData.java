package ru.hh.resumebuilderbot.user.data.storage;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;

import java.util.ArrayList;
import java.util.List;

class UserData {

    private QuestionNode questionNode;
    private List<UserAnswer> answers = new ArrayList<>();

    void registerAnswer(Answer answer) {
        questionNode.registerAnswer(answer);
        if (questionNode.needToSaveAnswer()) {
            saveAnswer(answer);
        }
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
}
