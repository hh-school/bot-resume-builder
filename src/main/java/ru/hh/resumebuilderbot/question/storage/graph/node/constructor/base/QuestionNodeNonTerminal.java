package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.saver.DoNothingSaver;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.saver.Saver;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator.DefaultValidator;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator.Validator;
import ru.hh.resumebuilderbot.user.data.storage.UserData;

import java.util.Objects;

public abstract class QuestionNodeNonTerminal implements QuestionNode {
    protected Question question;
    protected boolean skippable;
    protected Validator validator = new DefaultValidator();
    protected Saver saver = new DoNothingSaver();

    @Override
    public boolean answerIsValid(Answer answer) {
        return question.answerIsAllowed(answer) && validator.answerIsValid(answer);
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public void saveAnswer(UserData dest, Answer answer) {
        saver.saveAnswer(dest, answer);
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean hasEqualContent(QuestionNode questionNode) {
        if (!(questionNode instanceof QuestionNodeNonTerminal)) {
            return false;
        }
        QuestionNodeNonTerminal that = (QuestionNodeNonTerminal) questionNode;
        return Objects.equals(that.question, question) &&
                Objects.equals(that.validator, validator) &&
                Objects.equals(that.saver, saver) &&
                that.skippable == skippable;
    }
}
