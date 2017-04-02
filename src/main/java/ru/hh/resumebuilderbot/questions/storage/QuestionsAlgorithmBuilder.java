package ru.hh.resumebuilderbot.questions.storage;

import ru.hh.resumebuilderbot.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAlgorithmBuilder {
    private List<Question> questions = new ArrayList<>();

    public void registerQuestion(String text, List<String> allowedAnswers)
    {
        questions.add(new Question(text, allowedAnswers));
    }

    public void registerQuestion(String text)
    {
        registerQuestion(text, null);
    }

    public Node build()
    {
        if (questions.isEmpty())
        {
            return null; // throw exception??
        }
        boolean firstQuestion = true;
        Node root = new Node(NodeType.CONTINUE, questions.get(0));
        Node previousNode = root;

        for (Question question : questions) {
            if (!firstQuestion) {
                Node currentNode = new Node(NodeType.CONTINUE, question);
                previousNode.setNext(currentNode);
                previousNode = currentNode;
            }
            firstQuestion = false;
        }
        previousNode.setNext(new Node(NodeType.END));
        return root;
    }
}
