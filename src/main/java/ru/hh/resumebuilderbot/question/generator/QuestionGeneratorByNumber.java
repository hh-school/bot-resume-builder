package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;
import ru.hh.resumebuilderbot.QuestionsStorage;

public class QuestionGeneratorByNumber implements QuestionGenerator {

    private final int questionNumber;
    private String prefix = "";

    public QuestionGeneratorByNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    @Override
    public Question generateNext(ChatId chatId) {
        Question result = new Question(chatId, prefix + QuestionsStorage.getQuestion(questionNumber));
        if (QuestionsStorage.allowedAnswersPresent(questionNumber))
        {
            result.setAllowedAnswers(QuestionsStorage.getAllowedAnswers(questionNumber));
        }
        return result;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix + System.lineSeparator();
    }
}
