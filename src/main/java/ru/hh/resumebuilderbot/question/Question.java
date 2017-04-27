package ru.hh.resumebuilderbot.question;

import ru.hh.resumebuilderbot.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {

    private static final QuestionSuggest defaultSuggest = QuestionSuggest.NoSuggestNeeded;

    private final String text;

    private final List<String> variantsOfAnswer;
    private final boolean otherVariantsAllowed;

    private final QuestionSuggest suggestField;

    public Question(String text, List<String> variantsOfAnswer, boolean otherVariantsAllowed) {
        this(text, variantsOfAnswer, otherVariantsAllowed, defaultSuggest);
    }

    public Question(String text, List<String> variantsOfAnswer,
                    boolean otherVariantsAllowed, QuestionSuggest questionSuggest) {
        this.text = text;
        this.variantsOfAnswer = variantsOfAnswer;
        this.otherVariantsAllowed = otherVariantsAllowed;
        if (questionSuggest == null) {
            this.suggestField = defaultSuggest;
        } else {
            this.suggestField = questionSuggest;
        }
    }

    public Question(String text) {
        this(text, new ArrayList<>(), true, defaultSuggest);
    }

    public Question(String text, QuestionSuggest suggestField) {
        this(text, new ArrayList<>(), true, suggestField);
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
