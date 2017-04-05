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
        Question currentQuestion = currentState.getCurrentQuestion();
        answers.add(new UserAnswer(currentQuestion, answer));
        currentState.registerAnswer(answer);
        currentState.moveToNextQuestion();
    }

    List<UserAnswer> getAnswers() {
        return answers;
    }
}
