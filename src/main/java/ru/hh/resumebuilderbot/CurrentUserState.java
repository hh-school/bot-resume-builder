package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.questions.storage.Node;
import ru.hh.resumebuilderbot.questions.storage.QuestionsStorage;

public class CurrentUserState {
    private Node currentQuestionsNode;

    public CurrentUserState() {
        currentQuestionsNode = QuestionsStorage.getRoot();
    }

    public Question getCurrentQuestion() {
        return currentQuestionsNode.getQuestion();
    }

    public boolean isLastQuestion() {
        return currentQuestionsNode.isLast();
    }

    public void incrementCurrentQuestion() {
        currentQuestionsNode = currentQuestionsNode.getNext();
    }
}
