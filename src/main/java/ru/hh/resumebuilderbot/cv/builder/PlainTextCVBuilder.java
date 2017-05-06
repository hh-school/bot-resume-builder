package ru.hh.resumebuilderbot.cv.builder;

import ru.hh.resumebuilderbot.DBService;
import ru.hh.resumebuilderbot.TelegramUser;

public class PlainTextCVBuilder implements CVBuilder {
    private DBService dbService;

    public PlainTextCVBuilder(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public String build(TelegramUser telegramUser) {
        // TODO Выдача резюме в текстовом формате
        return "not implemented yet";
    }
}
