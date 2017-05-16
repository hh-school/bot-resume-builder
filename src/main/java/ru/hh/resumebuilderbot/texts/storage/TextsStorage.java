package ru.hh.resumebuilderbot.texts.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TextsStorage {
    private static final String instructions = "Для очистки своей истории введите /clear." +
            " Чтобы просмотреть Ваше резюме, введите /show";

    private static Map<TextId, String> textsMap = new ConcurrentHashMap<>();

    //hardcode
    static {
        textsMap.put(TextId.ALREADY_STARTED, "Опрос уже запущен. Для удаления сохраненных результатов восполь" +
                "зуйтесь /clear");
        textsMap.put(TextId.UNKNOWN, "Ответ не понятен");
        textsMap.put(TextId.OOPS_TRY_RESTART, "УПС! Что-то пошло не так. Начните заново с команды /start");
        textsMap.put(TextId.HELLO, "Привет! Я помогу составить отличное резюме");
        textsMap.put(TextId.CLEARED,
                "Ваше резюме было очищено. Опрос начнется сначала");
        textsMap.put(TextId.FINISHED,
                "Опрос закончен. " + instructions);
        textsMap.put(TextId.EMPTY,
                "Ваше резюме пока пусто. ");
        textsMap.put(TextId.INVALID_ANSWER,
                "Ответ не понятен");
        textsMap.put(TextId.CANT_SKIP,
                "Этот вопрос пропустить нельзя");
        textsMap.put(TextId.NO_FACULTIES_FOUND,
                "Нет факультета");
        textsMap.put(TextId.NO_FACULTIES_FOUND_DESCRIPTION,
                "У вашего вуза не найдено факультетов");
        textsMap.put(TextId.CONTINUE_INPUT,
                "Продолжайте ввод названия");
        textsMap.put(TextId.NOTHING_SELECTED,
                "Ничего не выбрано");
        textsMap.put(TextId.NEED_MORE_ONE_SYMBOL,
                "Для появления подсказки требуется минимум 2 введенных символа");
        textsMap.put(TextId.CHECK_INPUT_DATA,
                "Проверьте правильность введенных данных");

    }

    //end hardcode

    private TextsStorage() {
    }

    public static String getText(TextId textId) {
        return textsMap.get(textId);
    }
}
