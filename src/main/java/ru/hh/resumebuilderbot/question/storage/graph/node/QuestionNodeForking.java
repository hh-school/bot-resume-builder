package ru.hh.resumebuilderbot.question.storage.graph.node;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.user.data.storage.UserData;

import java.util.Map;
import java.util.regex.Pattern;

public class QuestionNodeForking implements QuestionNode {
    private Question question;
    private QuestionNode nextYes;
    private QuestionNode nextNo;
    private Pattern answerPattern;
    private boolean isSkippable;
    private boolean matches;


    public QuestionNodeForking(Question question, String answerPattern, boolean isSkippable) {
        this.question = question;
        this.answerPattern = Pattern.compile(answerPattern);
        this.isSkippable = isSkippable;
    }

    private QuestionNodeForking(Question question, Pattern pattern, boolean isSkippable) {
        this.question = question;
        this.answerPattern = pattern;
        this.isSkippable = isSkippable;
    }

    @Override
    public void setLinks(Map<String, QuestionNode> links) {
        nextYes = links.get("nextYes");
        nextNo = links.get("nextNo");
    }

    @Override
    public boolean answerIsValid(Answer answer) {
        return question.answerIsAllowed(answer);
    }

    @Override
    public void registerAnswer(Answer answer) {
        matches = answerPattern.matcher((String) (answer.getAnswerBody())).matches();
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public QuestionNode getNext() {
        return matches ? nextYes : nextNo;
    }

    @Override
    public boolean isSkippable() {
        return isSkippable;
    }

    @Override
    public QuestionNode cloneContent() {
        return new QuestionNodeForking(question, answerPattern, isSkippable);
    }

    @Override
    public void saveAnswer(UserData dest, Answer answer) {

    }
}
