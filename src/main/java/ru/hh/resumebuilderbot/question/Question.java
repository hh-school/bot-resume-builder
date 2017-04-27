package ru.hh.resumebuilderbot.question;

import ru.hh.resumebuilderbot.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {
    private final String text;

    private final List<String> variantsOfAnswer;
    private final boolean otherVariantsAllowed;

    private QuestionSuggest suggestField = QuestionSuggest.NoSuggestNeeded;

    public Question(String text, List<String> variantsOfAnswer, boolean otherVariantsAllowed) {
        this.text = text;
        this.variantsOfAnswer = variantsOfAnswer;
        this.otherVariantsAllowed = otherVariantsAllowed;
    }

    public Question(String text, List<String> variantsOfAnswer,
                    boolean otherVariantsAllowed, QuestionSuggest questionSuggest) {
        this.text = text;
        this.variantsOfAnswer = variantsOfAnswer;
        this.otherVariantsAllowed = otherVariantsAllowed;
        this.suggestField = questionSuggest;
    }

    public Question(String text) {
        this(text, new ArrayList<>(), true);
    }

    public List<String> getVariantsOfAnswer() {
        return variantsOfAnswer;
    }

    public String getText() {
        return text;
    }

    public boolean answerIsAllowed(Answer answer) {
        return variantsOfAnswer.isEmpty() || otherVariantsAllowed ||
                variantsOfAnswer.contains(answer.getAnswerBody());
    }

    public QuestionSuggest getSuggestField() {
        return suggestField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question that = (Question) o;
        return Objects.equals(getText(), that.getText()) &&
                Objects.equals(variantsOfAnswer, that.variantsOfAnswer) &&
                Objects.equals(otherVariantsAllowed, that.otherVariantsAllowed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getVariantsOfAnswer(), otherVariantsAllowed);
    }
}
