package ru.hh.resumebuilderbot.cv.builder;

import ru.hh.resumebuilderbot.DBService;

public enum CVFormats {
    PLAIN_TEXT {
        @Override
        public CVBuilder getBuilder(DBService dbService) {
            return new PlainTextCVBuilder(dbService);
        }
    };

    public abstract CVBuilder getBuilder(DBService dbService);
}
