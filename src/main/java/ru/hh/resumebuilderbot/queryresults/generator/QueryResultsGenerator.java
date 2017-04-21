package ru.hh.resumebuilderbot.queryresults.generator;

import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 20.04.2017.
 */
public class QueryResultsGenerator {
    String queryId;
    Integer chatId;
    String textForSearch;

    public QueryResultsGenerator(InlineQuery inlineQuery) {
        this.chatId = inlineQuery.getFrom().getId();
        this.queryId = inlineQuery.getId();
        this.textForSearch = inlineQuery.getQuery();
    }

    public List<InlineQueryResult> getResults() {
        String currentState = "1";//getCurrentState(chatId);
        List<InlineQueryResult> queryResults = new ArrayList<>();
        switch (currentState) {
            case "1":
                queryResults = getResultsFromRowData(SuggestGenerator.getInstitutes(textForSearch));
                break;
            case "2":
                break;
            default:
                break;
        }
        return queryResults;
    }

    private List<InlineQueryResult> getResultsFromRowData(List<Map<String, String>> rowData) {
        List<InlineQueryResult> queryResults = new ArrayList<>();
        for (int i = 0; i < rowData.size() && i < 50; i++) {
            Map<String, String> rowElement = rowData.get(i);
            InlineQueryResultArticle queryResult = new InlineQueryResultArticle();
            queryResult.setTitle(rowElement.get("title"));
            queryResult.setId(Integer.toString(i + 1));
            if (rowElement.containsKey("description")) {
                queryResult.setDescription(rowElement.get("description"));
            }
            InputTextMessageContent messageContent = new InputTextMessageContent();
            messageContent.setMessageText(rowElement.get("text"));
            queryResult.setInputMessageContent(messageContent);
            queryResults.add(queryResult);
        }
        return queryResults;
    }
}
