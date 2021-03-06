package ru.hh.resumebuilderbot.telegram.handler.suggest;

import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.SuggestService;
import ru.hh.resumebuilderbot.http.response.entity.Faculty;
import ru.hh.resumebuilderbot.question.ReplyKeyboardEnum;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.Handler;
import ru.hh.resumebuilderbot.telegram.handler.suggest.exceptions.NoSuggestsFoundException;
import ru.hh.resumebuilderbot.telegram.handler.suggest.exceptions.NonFacultiesFoundException;
import ru.hh.resumebuilderbot.telegram.handler.suggest.exceptions.ShortSearchQueryException;

import java.util.List;
import java.util.stream.Collectors;

public class SuggestHandler extends Handler {
    private static final Integer MAX_RESULTS_AMOUNT = 50;
    private static final Integer MIN_QUERY_LEN = 2;
    private final SuggestService suggestService;

    public SuggestHandler(DBProcessor dbProcessor, Graph graph, SuggestService suggestService) {
        super(dbProcessor, graph, SuggestHandler.class);
        this.suggestService = suggestService;
    }

    public List<?> getSuggestResults(Long telegramId, String textForSearch)
            throws NoSuggestsFoundException, NonFacultiesFoundException, ShortSearchQueryException {
        SuggestType neededSuggest = getCurrentNode(telegramId).getQuestion().getSuggestField();
        List<?> queryResults;
        if (neededSuggest == SuggestType.FACULTIES_SUGGEST) {
            Integer instituteId = dbProcessor.getInstituteHHId(telegramId);
            queryResults = getFacultiesSuggests(instituteId, textForSearch);
        } else {
            queryResults = getCommonSuggests(neededSuggest, textForSearch);
        }
        if (queryResults == null || queryResults.isEmpty()) {
            throw new NoSuggestsFoundException(textForSearch);
        }
        if (queryResults.size() >= MAX_RESULTS_AMOUNT) {
            queryResults = queryResults.subList(0, MAX_RESULTS_AMOUNT);
        }
        return queryResults;
    }

    private List<?> getFacultiesSuggests(Integer instituteId, String textForSearch)
            throws NonFacultiesFoundException {
        String lowCaseTextForSearch = textForSearch.toLowerCase();
        if (instituteId == null) {
            throw new NonFacultiesFoundException();
        }
        List<Faculty> queryResults = suggestService.getFaculties(instituteId.toString(), textForSearch);
        if (queryResults == null || queryResults.isEmpty()) {
            throw new NonFacultiesFoundException();
        }
        return queryResults.stream()
                .filter(faculty -> faculty.getName().toLowerCase().contains(lowCaseTextForSearch))
                .collect(Collectors.toList());
    }

    private List<?> getCommonSuggests(SuggestType neededSuggest, String textForSearch)
            throws ShortSearchQueryException {
        List<?> queryResults;
        if (textForSearch.length() < MIN_QUERY_LEN) {
            throw new ShortSearchQueryException();
        }
        switch (neededSuggest) {
            case INSTITUTES_SUGGEST:
                queryResults = suggestService.getInstitutes(textForSearch);
                break;
            case COMPANIES_SUGGEST:
                queryResults = suggestService.getCompanies(textForSearch);
                break;
            case STUDY_FIELDS_SUGGEST:
                queryResults = suggestService.getStudyFields(textForSearch);
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

    public boolean isStrongSuggest(Long telegramId) {
        return getCurrentNode(telegramId).getQuestion().getReplyKeyboardEnum() == ReplyKeyboardEnum.STRONG_SUGGEST;
    }
}
