package ru.hh.resumebuilderbot.question;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.telegram.handler.suggest.SuggestType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {
    public static final SuggestType DEFAULT_SUGGEST_TYPE = SuggestType.NO_SUGGEST_NEEDED;
    public static final ReplyKeyboard DEFAULT_KEYBOARD = ReplyKeyboard.NO_KEYBOARD_NEEDED;

    private final String text;

    private final List<String> variantsOfAnswer;
    private final boolean otherVariantsAllowed;

    private final SuggestType suggestField;
    private final ReplyKeyboard replyKeyboard;

    public Question(String text, List<String> variantsOfAnswer, boolean otherVariantsAllowed) {
        this(text, variantsOfAnswer, otherVariantsAllowed, DEFAULT_SUGGEST_TYPE, DEFAULT_KEYBOARD);
    }

    public Question(String text, List<String> variantsOfAnswer, boolean otherVariantsAllowed,
                    SuggestType suggestType, ReplyKeyboard replyKeyboard) {
        this.text = text;
        this.variantsOfAnswer = variantsOfAnswer;
        this.otherVariantsAllowed = otherVariantsAllowed;
        this.replyKeyboard = replyKeyboard;
        if (suggestType == null) {
            this.suggestField = DEFAULT_SUGGEST_TYPE;
        } else {
            this.suggestField = suggestType;
        }
    }

    public Question(String text) {
        this(text, new ArrayList<>(), true, DEFAULT_SUGGEST_TYPE, DEFAULT_KEYBOARD);
    }

    public Question(String text, SuggestType suggestField, ReplyKeyboard replyKeyboard) {
        this(text, new ArrayList<>(), true, suggestField, replyKeyboard);
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

    public SuggestType getSuggestField() {
        return suggestField;
    }

    public ReplyKeyboard getReplyKeyboard() {
        return replyKeyboard;
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
