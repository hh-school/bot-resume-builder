package ru.hh.resumebuilderbot.user.data.storage;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.CurrentUserState;
import ru.hh.resumebuilderbot.question.Question;

import java.util.ArrayList;
import java.util.List;

class UserData {

    private CurrentUserState currentState;
    private List<UserAnswer> answers = new ArrayList<>();

    UserData() {
        currentState = new CurrentUserState();
    }

    void registerAnswer(Answer answer) {
        currentState.registerAnswer(answer);
        if (currentState.needToSaveAnswer()) {
            saveAnswer(answer);
        }
    }

    void moveForward() {
        currentState.moveForward();
    }

    Question getCurrentQuestion() {
        return currentState.getCurrentQuestion();
    }

    List<UserAnswer> getAnswers() {
        return answers;
    }

    boolean answerIsValid(Answer answer) {
        return currentState.answerIsValid(answer);
    }

    boolean currentNodeIsSkippable() {
        return currentState.currentNodeIsSkippable();
    }

    private void saveAnswer(Answer answer) {
        Question currentQuestion = currentState.getCurrentQuestion();
        answers.add(new UserAnswer(currentQuestion, answer));
    }
}
