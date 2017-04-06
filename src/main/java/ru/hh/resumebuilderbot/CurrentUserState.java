package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.QuestionsStorage;
import ru.hh.resumebuilderbot.question.storage.node.QuestionGraphNode;

public class CurrentUserState {
    private QuestionGraphNode currentQuestionNode;

    public CurrentUserState() {
        currentQuestionNode = QuestionsStorage.getRoot();
    }

    public Question getCurrentQuestion() {
        return currentQuestionNode.getQuestion();
    }

    public void registerAnswer(Answer currentAnswer) {
        currentQuestionNode.registerAnswer(currentAnswer);
    }

    public void moveForward() {
        currentQuestionNode = currentQuestionNode.getNext();
    }

    public boolean persistData() {
        return currentQuestionNode.persistData();
    }
}
