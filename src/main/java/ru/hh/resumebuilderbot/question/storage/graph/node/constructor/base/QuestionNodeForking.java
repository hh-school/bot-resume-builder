package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.saver.Saver;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator.Validator;
import ru.hh.resumebuilderbot.user.data.storage.UserData;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class QuestionNodeForking extends QuestionNodeNonTerminal {
    private QuestionNode nextYes;
    private QuestionNode nextNo;
    private Pattern answerPattern;
    private boolean matches;


    public QuestionNodeForking(Question question, String answerPattern, boolean isSkippable) {
        this.question = question;
        this.answerPattern = Pattern.compile(answerPattern);
        this.skippable = isSkippable;
    }

    private QuestionNodeForking(Question question, Pattern pattern, boolean isSkippable, Validator validator,
                                Saver saver) {
        this.question = question;
        this.answerPattern = pattern;
        this.skippable = isSkippable;
        this.validator = validator;
        this.saver = saver;
    }

    public QuestionNodeForking() {
    }

    @Override
    public void setLinks(Map<String, QuestionNode> links) {
        nextYes = links.get("nextYes");
        nextNo = links.get("nextNo");
    }

    @Override
    public void registerAnswer(Answer answer) {
        matches = answerPattern.matcher((String) (answer.getAnswerBody())).matches();
    }

    @Override
    public QuestionNode getNext() {
        return matches ? nextYes : nextNo;
    }

    @Override
    public boolean isSkippable() {
        return skippable;
    }

    @Override
    public QuestionNode cloneContent() {
        return new QuestionNodeForking(question, answerPattern, skippable, validator.clone(), saver.clone());
    }

    @Override
    public void saveAnswer(UserData dest, Answer answer) {

    }

    @Override
    public boolean hasEqualContent(QuestionNode questionNode) {
        if (this == questionNode) {
            return true;
        }
        if (questionNode == null || getClass() != questionNode.getClass()) {
            return false;
        }
        QuestionNodeForking that = (QuestionNodeForking) questionNode;

        return super.hasEqualContent(questionNode) &&
                Objects.equals(that.question, question) &&
                Objects.equals(answerPattern.pattern(), that.answerPattern.pattern()) &&
                that.skippable == skippable;
    }

    public void setAnswerPattern(String answerPatternString) {
        this.answerPattern = Pattern.compile(answerPatternString);
    }
}
