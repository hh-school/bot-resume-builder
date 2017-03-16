package ru.hh.resumebuilderbot;

public class CurrentUserState {
    private int lastAnsweredQuestions;

    public int getLastAnsweredQuestions() {
        return lastAnsweredQuestions;
    }

    public void setLastAnsweredQuestions(int lastAnsweredQuestions) {
        this.lastAnsweredQuestions = lastAnsweredQuestions;
    }
}
