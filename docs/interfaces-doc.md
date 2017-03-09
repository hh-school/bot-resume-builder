трансфер-типы
```Java
class Question
{
    private ChatId chatId;
    private String text;
    private AnswerType answerType;  // перечисление: YesNo, AnyString, SelectedFromList
    private List<String> allowedAnswers;
}

class Answer
{
    private ChatId chatId;
    private Object answerBody;
}
```
интерфейсы
```Java
interface MessengerAdapter
{
    void ask(Question question, int timeout);
}

interface BotBody
{
    void answer(Answer answer, int timeout);
}

interface JobSiteAdapter
{
    void connect(AuthData authData, int timeout);
    void register(AuthData authData, int timeout);
    void pushCV(CV cv);
}
```