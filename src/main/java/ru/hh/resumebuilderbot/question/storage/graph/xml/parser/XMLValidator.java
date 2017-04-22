package ru.hh.resumebuilderbot.question.storage.graph.xml.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XMLValidator {

    private final static Logger log = LoggerFactory.getLogger(XMLValidator.class);

    public static void validate(List<XMLEntry> rawData) {
        // step 1 - check if number of roots exactly equals 17
        long numberOfRoots = rawData.stream()
                .filter((x) -> x.isRoot())
                .count();
        if (numberOfRoots != 1) {
            log.error("Error: wrong XML schema - number of nodes with 'root=true' not equals 1");
            throw new RuntimeException("Error: wrong XML schema - number of nodes with 'root=true' not equals 1");
        }

        // step 2 - check ids' uniqueness
        Set<Integer> usedIndices = new HashSet<>();
        for (XMLEntry entry : rawData) {
            int index = entry.getIndex();
            if (usedIndices.contains(index)) {
                log.error("Error: wrong XML schema - duplicate node index");
                throw new RuntimeException("Error: wrong XML schema - duplicate node index");
            }
            usedIndices.add(index);
        }
    }
}
