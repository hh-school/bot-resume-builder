package ru.hh.resumebuilderbot.user.data.storage;

import ru.hh.resumebuilderbot.CurrentUserState;

import java.util.ArrayList;
import java.util.List;

public class UserData {

    private CurrentUserState currentState;
    private List<UserAnswer> answers = new ArrayList<>();

    public UserData() {
        currentState = new CurrentUserState();
        currentState.setCurrentQuestion(0);
    }

    public CurrentUserState getCurrentState() {
        return currentState;
    }

    public void registerAnswer(String question, String answer) {
        answers.add(new UserAnswer(question, answer));
    }

    public void incrementCurrentQuestion() {
        currentState.setCurrentQuestion(currentState.getCurrentQuestion() + 1);
    }

    public List<UserAnswer> getAnswers() {
        return answers;
    }
}
