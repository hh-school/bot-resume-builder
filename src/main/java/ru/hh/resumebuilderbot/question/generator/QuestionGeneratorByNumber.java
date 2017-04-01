package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.Question;
import ru.hh.resumebuilderbot.questions.storage.QuestionsStorage;

public class QuestionGeneratorByNumber implements QuestionGenerator {

    private final int questionNumber;

    public QuestionGeneratorByNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    @Override
    public Question generateNext() {
        Question result = new Question(QuestionsStorage.getQuestion(questionNumber));
        if (QuestionsStorage.allowedAnswersPresent(questionNumber)) {
            result.setAllowedAnswers(QuestionsStorage.getAllowedAnswers(questionNumber));
        }
        return result;
    }
}
