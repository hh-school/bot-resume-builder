package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.QuestionsStorage;
import ru.hh.resumebuilderbot.question.storage.node.Node;
import ru.hh.resumebuilderbot.question.storage.node.NonTerminalNode;

public class CurrentUserState {
    private Node currentQuestionNode;

    public CurrentUserState() {
        currentQuestionNode = QuestionsStorage.getRoot();
    }

    public Question getCurrentQuestion() {
        return currentNonTerminalNode().getQuestion();
    }

    public boolean isLastQuestion() {
        return currentQuestionNode.isTerminal();
    }

    public void incrementCurrentQuestion() {
        currentQuestionNode = currentNonTerminalNode().getNext();
    }

    private NonTerminalNode currentNonTerminalNode() {
        return (NonTerminalNode) currentQuestionNode;
    }
}
