package ru.hh.resumebuilderbot.user.data.storage;

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

    void registerAnswer(Question question, String answer) {
        answers.add(new UserAnswer(question, answer));
    }

    void moveToNextQuestion(String currentAnswer) {
        currentState.moveToNextQuestion(currentAnswer);
    }

    List<UserAnswer> getAnswers() {
        return answers;
    }
}
