package ru.hh.resumebuilderbot.cv.builder;

import ru.hh.resumebuilderbot.DBProcessor;

public enum CVFormats {
    PLAIN_TEXT {
        @Override
        public CVBuilder getBuilder(DBProcessor dbProcessor) {
            return new PlainTextCVBuilder(dbProcessor);
        }
    };

    public abstract CVBuilder getBuilder(DBProcessor dbProcessor);
}
