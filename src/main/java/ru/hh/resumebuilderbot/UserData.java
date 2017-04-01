package ru.hh.resumebuilderbot;

import java.util.ArrayList;
import java.util.List;

public class UserData {

    private CurrentUserState currentState;
    private List<Entry> answers = new ArrayList<>();

    public UserData() {
        currentState = new CurrentUserState();
        currentState.setCurrentQuestion(0);
    }

    public CurrentUserState getCurrentState() {
        return currentState;
    }

    public void registerAnswer(String question, String answer) {
        answers.add(new Entry(question, answer));
    }

    public void incrementCurrentQuestion() {
        currentState.setCurrentQuestion(currentState.getCurrentQuestion() + 1);
    }

    public List<Entry> getAnswers() {
        return answers;
    }

    public class Entry {
        private String question;
        private String answer;

        public Entry(String question, String answer) {
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
}
