package ru.hh.resumebuilderbot.telegram.handler.suggest;

import com.google.inject.Inject;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import ru.hh.resumebuilderbot.SuggestService;
import ru.hh.resumebuilderbot.telegram.handler.suggest.converter.TelegramConverter;

import java.util.List;

public class SuggestHandler {
    private static final Integer MAX_RESULTS_AMOUNT = 50;
    private static final Integer MIN_QUERY_LEN = 2;
    private final SuggestService suggestService;
    private final TelegramConverter telegramConverter;

    @Inject
    public SuggestHandler(SuggestService suggestService, TelegramConverter telegramConverter) {
        this.telegramConverter = telegramConverter;
        this.suggestService = suggestService;
    }

    public List<InlineQueryResult> getSuggestResults(String textForSearch, SuggestType neededSuggest) {
        List<?> queryResults;
        if (textForSearch.length() < MIN_QUERY_LEN) {
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

        if (queryResults == null || queryResults.isEmpty()) {
            return NotificationInlineQueryResults.getNotFoundErrorResult(textForSearch);
        }
        if (queryResults.size() >= MAX_RESULTS_AMOUNT) {
            queryResults = queryResults.subList(0, MAX_RESULTS_AMOUNT);
        }
        return telegramConverter.convertList(queryResults);
    }

    public List<InlineQueryResult> getFacultiesResult(String instituteId) {
        List<?> queryResults = suggestService.getFaculties(instituteId);
        if (queryResults == null || queryResults.isEmpty()) {
            return NotificationInlineQueryResults.getNonFacultiesInstituteResult();
        }
        return telegramConverter.convertList(queryResults);
    }
}
