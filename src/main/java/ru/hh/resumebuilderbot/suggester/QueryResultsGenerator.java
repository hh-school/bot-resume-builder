package ru.hh.resumebuilderbot.suggester;

import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryResultsGenerator {

    private final static Integer maxResultsAmount = 50;

    public static List<InlineQueryResult> getResults(InlineQuery inlineQuery) {
        Integer chatId = inlineQuery.getFrom().getId();
        String textForSearch = inlineQuery.getQuery();
        String currentState = "Institutes";

        List<InlineQueryResult> queryResults = new ArrayList<>();
        switch (currentState) {
            case "Institutes":
                queryResults = getResultsFromRawData(SuggestGenerator.getInstitutes(textForSearch));
                break;
            case "Faculties":
                String instId = "39144";
                queryResults = getResultsFromRawData(SuggestGenerator.getFaculties(instId, textForSearch));
                break;
            case "Companies":
                queryResults = getResultsFromRawData(SuggestGenerator.getCompanies(textForSearch));
                break;
            case "Specializations":
                queryResults = getResultsFromRawData(SuggestGenerator.getSpecializations(textForSearch));
                break;
            case "Skills":
                queryResults = getResultsFromRawData(SuggestGenerator.getSkills(textForSearch));
                break;
            case "Positions":
                queryResults = getResultsFromRawData(SuggestGenerator.getPositions(textForSearch));
                break;
            case "Areas":
                queryResults = getResultsFromRawData(SuggestGenerator.getAreas(textForSearch));
                break;
            default:
                break;
        }
        return queryResults;
    }

    private static List<InlineQueryResult> getResultsFromRawData(RawSuggestData rawData) {
        List<InlineQueryResult> queryResults = new ArrayList<>();
        for (int i = 0; i < rawData.size() && i < maxResultsAmount; i++) {
            Map<String, String> rowElement = rawData.get(i);
            rowElement.put("index", Integer.toString(i + 1));
            queryResults.add(getQueryResultFromMap(rowElement));
        }
        return queryResults;
    }

    private static InlineQueryResult getQueryResultFromMap(Map<String, String> rowElement) {
        InlineQueryResultArticle queryResult = new InlineQueryResultArticle();
        queryResult.setTitle(rowElement.get("title"));
        queryResult.setId(rowElement.get("index"));
        if (rowElement.containsKey("description") && !rowElement.get("description").equals("")) {
            queryResult.setDescription(rowElement.get("description"));
        }
        if (rowElement.containsKey("thumb")) {
            queryResult.setThumbUrl(rowElement.get("thumb"));
        }
        InputTextMessageContent messageContent = new InputTextMessageContent();
        messageContent.setMessageText(rowElement.get("text"));
        queryResult.setInputMessageContent(messageContent);
        return queryResult;
    }
}
