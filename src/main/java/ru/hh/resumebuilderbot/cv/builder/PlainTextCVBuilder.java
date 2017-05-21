package ru.hh.resumebuilderbot.cv.builder;

import ru.hh.resumebuilderbot.DBProcessor;

public class PlainTextCVBuilder implements CVBuilder {
    private DBProcessor dbProcessor;

    public PlainTextCVBuilder(DBProcessor dbProcessor) {
        this.dbProcessor = dbProcessor;
    }

    @Override
    public String build(Long telegramId) {
        return dbProcessor.getUser(telegramId).toString().replaceAll(", ", ", \n");
    }
}
