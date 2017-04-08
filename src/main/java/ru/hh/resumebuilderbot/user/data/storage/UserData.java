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

    CurrentUserState getCurrentState() {
        return currentState;
    }

    void registerAnswer(Answer answer) {
        currentState.registerAnswer(answer);
        if (currentState.needToSaveAnswer()) {
            persistAnswer(answer);
        }
        currentState.moveForward();
    }

    List<UserAnswer> getAnswers() {
        return answers;
    }

    private void persistAnswer(Answer answer) {
        Question currentQuestion = currentState.getCurrentQuestion();
        answers.add(new UserAnswer(currentQuestion, answer));
    }
}
