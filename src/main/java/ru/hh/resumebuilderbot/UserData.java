package ru.hh.resumebuilderbot;

import java.util.HashMap;
import java.util.Map;

public class UserData {

    private CurrentUserState currentState;
    private Map<String, String> answers = new HashMap<>();

    public UserData() {
        currentState = new CurrentUserState();
        currentState.setCurrentQuestion(0);
    }

    public CurrentUserState getCurrentState() {
        return currentState;
    }

    public void registerAnswer(String question, String answer) {
        answers.put(question, answer);
    }

    public void incrementCurrentQuestion() {
        currentState.setCurrentQuestion(currentState.getCurrentQuestion() + 1);
    }
}
