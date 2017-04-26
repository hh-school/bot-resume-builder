package ru.hh.resumebuilderbot.question.storage.graph.xml.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.stream.Stream;

public class XMLValidator {

    private final static Logger log = LoggerFactory.getLogger(XMLValidator.class);

    public static void validate(XMLRawData rawData) throws IOException {

        try {
            checkIDUniqueness(rawData);
        } catch (IOException e) {
            throw new IOException("Invalid XML schema", e);
        }
    }

    private static void checkIDUniqueness(XMLRawData rawData) throws IOException {
        if (getIndexStream(rawData).count() != getIndexStream(rawData).distinct().count()) {
            throw new IOException("Duplicated node index");
        }
    }

    private static Stream<Integer> getIndexStream(XMLRawData rawData) {
        return rawData.getEntriesList().stream().map(XMLEntry::getIndex);
    }

}
