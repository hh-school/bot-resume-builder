package ru.hh.resumebuilderbot;

import ru.hh.resumebuilderbot.message.handler.MessageHandler;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.generator.QuestionsGenerator;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

import java.util.Queue;

public class BotBodyImpl implements BotBody {
    private MessengerAdapter messengerAdapter;

    @Override
    public void answer(User user, Answer answer) {
        synchronized (UserDataStorage.getMutex(user)) {
            MessageHandler messageHandler = Selector.select(answer);
            QuestionsGenerator questionGenerator = messageHandler.handle(user, answer);
            Queue<Question> questions = questionGenerator.generateQuestions();
            questions.forEach((x) -> messengerAdapter.ask(user, x));
        }
    }

    @Override
    public void setMessenger(MessengerAdapter messenger) {
        this.messengerAdapter = messenger;
    }
}
