package ru.hh.resumebuilderbot.suggester;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RawSuggestData {
    private List<Map<String, String>> rawListData;

    public RawSuggestData(Map<String, String> rawData) {
        this.rawListData = new ArrayList<>();
        this.rawListData.add(rawData);
    }

    public RawSuggestData() {
        this.rawListData = new ArrayList<>();
    }

    public int size(){
        return rawListData.size();
    }

    public Map<String, String> get(int index){
        return rawListData.get(index);
    }

    public void add(Map<String, String> newElement){
        this.rawListData.add(newElement);
    }
}
