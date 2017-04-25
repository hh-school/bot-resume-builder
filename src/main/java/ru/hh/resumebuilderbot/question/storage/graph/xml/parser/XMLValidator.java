package ru.hh.resumebuilderbot.question.storage.graph.xml.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class XMLValidator {

    private final static Logger log = LoggerFactory.getLogger(XMLValidator.class);

    public static void validate(XMLRawData rawData) throws IOException {
        checkIDUniqueness(rawData);
    }

    private static void checkIDUniqueness(XMLRawData rawData) throws IOException {
        Set<Integer> usedIndices = new HashSet<>();
        for (XMLEntry entry : rawData.getEntriesList()) {
            int index = entry.getIndex();
            if (usedIndices.contains(index)) {
                throw new IOException("Wrong XML schema - duplicate node index: " + index);
            }
            usedIndices.add(index);
        }
    }
}
