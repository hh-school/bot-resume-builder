package ru.hh.resumebuilderbot.cv.builder;

public enum CVFormats {
    PLAIN_TEXT {
        @Override
        public CVBuilder getBuilder() {
            return new PlainTextCVBuilder();
        }
    };

    public abstract CVBuilder getBuilder();
}
