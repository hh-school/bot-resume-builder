package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.message.handler.MessageHandler;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.generator.QuestionsGenerator;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

import java.util.Queue;

public class BotBodyImpl implements BotBody {
    private MessengerAdapter messengerAdapter;

    @Override
    public void askNextQuestions(User user, Answer answer) {
        synchronized (UserDataStorage.getMutex(user)) {
            MessageHandler messageHandler = Selector.select(answer);
            QuestionsGenerator questionsGenerator = messageHandler.handle(user, answer);
            Queue<Question> questions = questionsGenerator.generateQuestions();
            questions.forEach((question) -> messengerAdapter.ask(user, question));
        }
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
}
