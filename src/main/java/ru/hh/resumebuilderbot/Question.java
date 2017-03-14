package ru.hh.resumebuilderbot;

class ChatId {
    // todo: make private and override constructor
    final long index;

    ChatId(long index) {
        this.index = index;
    }
}

enum AnswerType {
    YesNo,
    AnyString,
    SelectedFromList
}

class Question {
    private final ChatId chatId;
    private final String text;
//    private ru.hh.resumebuilderbot.AnswerType answerType;
//    private List<String> allowedAnswers;

    Question(ChatId chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }

    ChatId getChatId() {
        return chatId;
    }

    String getText() {
        return text;
    }
}

class Answer {
    private ChatId chatId;
    private Object answerBody;

    Answer(ChatId chatId, Object answerBody) {
        this.chatId = chatId;
        this.answerBody = answerBody;
    }

    ChatId getChatId() {
        return chatId;
    }

    Object getAnswerBody() {
        return answerBody;
    }
}

interface MessengerAdapter {
    // Задать вопрос пользователю
    void ask(Question question, int timeoutMs);

    // Подписать бота на ответы пользователя
    void setHandler(AbstractBotBody handler);

    // Начать принимать ответы пользователя
    void start();
}

interface AbstractBotBody {
    // Вызывается при новом ответе пользователя
    void onAnswer(Answer answer, int timeoutMs);

    // Вызывается при добавлении нового пользователя
    void onStartChat(ChatId chatId);
}

abstract class AuthData {}

abstract class CV {}

interface JobSiteAdapter {
    void connect(AuthData authData, int timeout);
    void register(AuthData authData, int timeout);
    void pushCV(CV cv);
}
