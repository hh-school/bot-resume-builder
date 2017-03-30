package ru.hh.resumebuilderbot;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserData {

    private CurrentUserState currentState;
    private Map<Integer, String> answers = new HashMap<>();

    public UserData() {
        currentState = new CurrentUserState();
        currentState.setCurrentQuestion(0);
    }

    public CurrentUserState getCurrentState() {
        return currentState;
    }

    public void registerAnswer(int questionId, String answer)
    {
        answers.put(questionId, answer);
    }

    public String buildResume(){
        try {
            String filePath = "src\\main\\resources\\";
            String fileName = "ResumeTemplate.xml";
            File file = new File(filePath + fileName);
            String resume = FileUtils.readFileToString(file, "utf-8");
            if (answers.size() == 0)
                return "Вы еще не ответили ни на один вопрос.";
            for (Map.Entry<Integer, String> entry : answers.entrySet()) {
                resume = resume.replace(QuestionsStorage.dictionary.get(entry.getKey()), entry.getValue());
            }
            return resume;
        }
        catch (IOException e){
            return "Ошибка при генерации файла резюме";
        }
    }

    public void incrementCurrentQuestion()
    {
        currentState.setCurrentQuestion(currentState.getCurrentQuestion() + 1);
    }
}
