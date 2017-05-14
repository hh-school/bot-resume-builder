package ru.hh.resumebuilderbot.telegram.handler.suggest;

import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import ru.hh.resumebuilderbot.DBService;
import ru.hh.resumebuilderbot.SuggestService;
import ru.hh.resumebuilderbot.http.response.entity.Faculty;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.Handler;
import ru.hh.resumebuilderbot.telegram.handler.suggest.converter.TelegramConverter;

import java.util.List;
import java.util.stream.Collectors;

public class SuggestHandler extends Handler {
    private static final Integer MAX_RESULTS_AMOUNT = 50;
    private static final Integer MIN_QUERY_LEN = 2;
    private final SuggestService suggestService;
    private final TelegramConverter telegramConverter;

    public SuggestHandler(DBService dbService, Graph graph, SuggestService suggestService,
                          TelegramConverter telegramConverter) {
        super(dbService, graph);
        this.telegramConverter = telegramConverter;
        this.suggestService = suggestService;
    }

    public List<InlineQueryResult> getSuggestResults(Long telegramId, String textForSearch) {
        SuggestType neededSuggest = getCurrentNode(telegramId).getQuestion().getSuggestField();
        List<?> queryResults;
        if (neededSuggest == SuggestType.FACULTIES_SUGGEST) {
            Integer instituteId = dbService.getInstituteHHId(telegramId);
            queryResults = getFacultiesSuggests(instituteId, textForSearch);
        } else {
            queryResults = getCommonSuggests(neededSuggest, textForSearch);
        }
        if (queryResults == null || queryResults.isEmpty()) {
            return NotificationInlineQueryResults.getNotFoundErrorResult(textForSearch);
        }
        if (queryResults.size() >= MAX_RESULTS_AMOUNT) {
            queryResults = queryResults.subList(0, MAX_RESULTS_AMOUNT);
        }
        return telegramConverter.convertList(queryResults);
    }

    private List<?> getFacultiesSuggests(Integer instituteId, String textForSearch) {
        if (instituteId == null) {
            return NotificationInlineQueryResults.getNonFacultiesInstituteResult();
        }
        List<Faculty> queryResults = suggestService.getFaculties(instituteId.toString(), textForSearch);
        if (queryResults == null || queryResults.isEmpty()) {
            return NotificationInlineQueryResults.getNonFacultiesInstituteResult();
        }
        return queryResults.stream()
                .filter(faculty -> faculty.getName().contains(textForSearch))
                .collect(Collectors.toList());
    }

    private List<?> getCommonSuggests(SuggestType neededSuggest, String textForSearch) {
        List<?> queryResults;
        if (textForSearch.length() < MIN_QUERY_LEN &&
                neededSuggest != SuggestType.FACULTIES_SUGGEST) {
            return NotificationInlineQueryResults.getShortQueryErrorResult();
        }
        switch (neededSuggest) {
            case INSTITUTES_SUGGEST:
                queryResults = suggestService.getInstitutes(textForSearch);
                break;
            case COMPANIES_SUGGEST:
                queryResults = suggestService.getCompanies(textForSearch);
                break;
            case SPECIALIZATIONS_SUGGEST:
                queryResults = suggestService.getSpecializations(textForSearch);
                break;
            case SKILLS_SUGGEST:
                queryResults = suggestService.getSkills(textForSearch);
                break;
            case POSITIONS_SUGGEST:
                queryResults = suggestService.getPositions(textForSearch);
                break;
            case AREAS_SUGGEST:
                queryResults = suggestService.getAreas(textForSearch);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return queryResults;
    }
}