package ru.hh.resumebuilderbot.question.storage.builder.xml.parser;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XMLValidator {
    public static void validate(List<XMLEntry> rawData) throws IOException {
        // step 1 - check if number of roots exactly equals 17
        long numberOfRoots = rawData.stream()
                .filter((x) -> x.isRoot())
                .count();
        if (numberOfRoots != 1) {
            throw new IOException("Error parsing XML: Number of nodes with 'root=true' isn't equals to 1");
        }

        // step 2 - check ids' uniqueness
        Set<Integer> usedIndices = new HashSet<>();
        for (XMLEntry entry : rawData) {
            int index = entry.getIndex();
            if (usedIndices.contains(index)) {
                throw new IOException("Error parsing XML: Indices of nodes is not unique");
            }
            usedIndices.add(index);
        }
    }
}
