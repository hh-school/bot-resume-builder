package ru.hh.resumebuilderbot.question.storage.graph.node.constructor.validator;

import java.util.regex.Pattern;

public class NameValidator extends PatternValidator {

    public NameValidator() {
        pattern = Pattern.compile("^(?=(((?:[ ʼ’'`-]|^)+([A-ZА-ЯЁ][a-zа-яё]*)?)*))\\1$");
        notification = "Вы уверены, что ввели правильные данные? " +
                "Пожалуйста, попробуйте ввести данные еще раз с большой буквы.";
    }

    @Override
    public Validator clone() {
        return new NameValidator();
    }
}
