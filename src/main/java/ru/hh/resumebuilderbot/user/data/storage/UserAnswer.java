package ru.hh.resumebuilderbot.user.data.storage;

import ru.hh.resumebuilderbot.question.Question;

public class UserAnswer {
    private Question question;
    private String answer;

    public UserAnswer(Question question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
