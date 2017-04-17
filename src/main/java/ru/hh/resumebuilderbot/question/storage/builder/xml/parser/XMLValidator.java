package ru.hh.resumebuilderbot.question.storage.builder.xml.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XMLValidator {

    private final static Logger log = LoggerFactory.getLogger(XMLValidator.class);

    public static void validate(List<XMLEntry> rawData) throws IOException {
        // step 1 - check if number of roots exactly equals 17
        long numberOfRoots = rawData.stream()
                .filter((x) -> x.isRoot())
                .count();
        if (numberOfRoots != 1) {
            log.error("XML schema error : Number of nodes with 'root=true' isn't equals to 1");
            throw new IOException("Error parsing XML: Number of nodes with 'root=true' isn't equals to 1");
        }

        // step 2 - check ids' uniqueness
        Set<Integer> usedIndices = new HashSet<>();
        for (XMLEntry entry : rawData) {
            int index = entry.getIndex();
            if (usedIndices.contains(index)) {
                log.error("XML schema error : Indices of nodes is not unique");
                throw new IOException("Error parsing XML: Indices of nodes is not unique");
            }
            usedIndices.add(index);
        }
    }
}
