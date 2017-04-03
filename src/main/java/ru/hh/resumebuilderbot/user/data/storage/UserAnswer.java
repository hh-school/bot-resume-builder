package ru.hh.resumebuilderbot.user.data.storage;

public class UserAnswer {
    private String question;
    private String answer;

    public UserAnswer(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
