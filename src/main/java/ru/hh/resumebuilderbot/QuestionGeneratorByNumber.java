package ru.hh.resumebuilderbot;

public class QuestionGeneratorByNumber implements QuestionGenerator {

    private final int questionNumber;

    public QuestionGeneratorByNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    @Override
    public Question generate(ChatId chatId) {
        return new Question(chatId, QuestionsStorage.getQuestion(questionNumber));
    }
}
