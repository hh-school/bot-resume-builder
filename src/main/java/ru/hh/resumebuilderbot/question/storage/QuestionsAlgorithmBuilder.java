package ru.hh.resumebuilderbot.question.storage;

import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.node.Node;
import ru.hh.resumebuilderbot.question.storage.node.NodeType;
import ru.hh.resumebuilderbot.question.storage.node.NonTerminalNode;
import ru.hh.resumebuilderbot.question.storage.node.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAlgorithmBuilder {
    private List<Question> questions = new ArrayList<>();

    public void registerQuestion(String text, List<String> allowedAnswers) {
        questions.add(new Question(text, allowedAnswers));
    }

    public void registerQuestion(String text) {
        questions.add(new Question(text));
    }

    public Node build() {
        if (questions.isEmpty()) {
            return null; // throw exception??
        }
        boolean firstQuestion = true;
        NonTerminalNode root = new NonTerminalNode(questions.get(0));
        NonTerminalNode previousNode = root;

        for (Question question : questions) {
            if (!firstQuestion) {
                NonTerminalNode currentNode = new NonTerminalNode(question);
                previousNode.setNext(currentNode);
                previousNode = currentNode;
            }
            firstQuestion = false;
        }
        previousNode.setNext(new TerminalNode());
        return root;
    }
}
