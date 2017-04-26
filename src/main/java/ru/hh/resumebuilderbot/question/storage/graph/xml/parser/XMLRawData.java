package ru.hh.resumebuilderbot.question.storage.graph.xml.parser;

import java.util.List;

public class XMLRawData {
    private int rootIndex;
    private List<XMLEntry> entriesList;

    public XMLRawData(int rootIndex, List<XMLEntry> entriesList) {
        this.rootIndex = rootIndex;
        this.entriesList = entriesList;
    }

    public List<XMLEntry> getEntriesList() {
        return entriesList;
    }

    public int getRootIndex() {
        return rootIndex;
    }
}
