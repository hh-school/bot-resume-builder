package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.QuestionsStorage;
import ru.hh.resumebuilderbot.question.storage.node.QuestionNode;

import java.io.IOException;

public class CurrentUserState {
    private QuestionNode currentQuestionNode;

    public CurrentUserState() {
        try {
            currentQuestionNode = QuestionsStorage.getRoot();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Question getCurrentQuestion() {
        return currentQuestionNode.getQuestion();
    }

    public void registerAnswer(Answer currentAnswer) {
        currentQuestionNode.checkAnswer(currentAnswer);
    }

    public void moveForward() {
        currentQuestionNode = currentQuestionNode.getNext();
    }

    public boolean needToSaveAnswer() {
        return currentQuestionNode.needToSaveAnswer();
    }
}
