package ru.hh.resumebuilderbot;

public class CurrentUserState {
    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    private int currentQuestion;
}
