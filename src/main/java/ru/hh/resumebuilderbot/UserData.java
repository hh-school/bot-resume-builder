package ru.hh.resumebuilderbot;

import java.util.Map;

public class UserData {

    private CurrentUserState currentState;
    private Map<String, String> answers;

    public CurrentUserState getCurrentState() {
        return currentState;
    }

    public void registerAnswer(String question, String answer)
    {
        answers.put(question, answer);
    }

    public void incrementCurrentQuestion()
    {
        currentState.setCurrentQuestion(currentState.getCurrentQuestion() + 1);
    }
}
