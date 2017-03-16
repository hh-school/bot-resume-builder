package ru.hh.resumebuilderbot;

import java.util.Map;

public class UserData {
    private CurrentUserState currentState;
    private Map<String, String> answers;

    public void registerAnswer(Question question, Answer answer)
    {
        answers.put(question.getText(), answer.getAnswerBody().toString());
    }

    public CurrentUserState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(CurrentUserState currentState) {
        this.currentState = currentState;
    }
}
