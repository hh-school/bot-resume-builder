package ru.hh.resumebuilderbot.telegram.handler.suggest;

import ru.hh.resumebuilderbot.DBService;
import ru.hh.resumebuilderbot.SuggestService;
import ru.hh.resumebuilderbot.http.response.entity.Area;
import ru.hh.resumebuilderbot.http.response.entity.Company;
import ru.hh.resumebuilderbot.http.response.entity.Faculty;
import ru.hh.resumebuilderbot.http.response.entity.Institute;
import ru.hh.resumebuilderbot.http.response.entity.Position;
import ru.hh.resumebuilderbot.http.response.entity.Skill;
import ru.hh.resumebuilderbot.http.response.entity.Specialization;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.Handler;

public class ChosenSuggestHandler extends Handler {
    private final SuggestService suggestService;

    public ChosenSuggestHandler(DBService dbService, Graph graph, SuggestService suggestService) {
        super(dbService, graph);
        this.suggestService = suggestService;
    }

    public void saveChosenSuggest(Long telegramId, Integer resultId, String queryText) {
        SuggestType neededSuggest = getCurrentNode(telegramId).getQuestion().getSuggestField();

        switch (neededSuggest) {
            case INSTITUTES_SUGGEST:
                Institute institute = suggestService.getInstitutes(queryText).get(resultId);
                dbService.saveInstitute(telegramId, Integer.valueOf(institute.getId()), institute.getText());
                break;
            case COMPANIES_SUGGEST:
                Company company = suggestService.getCompanies(queryText).get(resultId);
                dbService.saveExperienceCompany(telegramId, company.getName(), Integer.valueOf(company.getId()));
                break;
            case SPECIALIZATIONS_SUGGEST:
                Specialization specialization = suggestService.getSpecializations(queryText).get(resultId);
                dbService.saveSpeciality(telegramId, Integer.valueOf(specialization.getId()),
                        specialization.getText());
                break;
            case SKILLS_SUGGEST:
                Skill skill = suggestService.getSkills(queryText).get(resultId);
                //TODO add skill save, position save, errorHandle
                break;
            case POSITIONS_SUGGEST:
                Position position = suggestService.getPositions(queryText).get(resultId);
                break;
            case AREAS_SUGGEST:
                Area area = suggestService.getAreas(queryText).get(resultId - 1);
                dbService.saveUserArea(telegramId, area.getText(), Integer.valueOf(area.getId()));
                break;
            case FACULTIES_SUGGEST:
                Integer instituteId = dbService.getInstituteHHId(telegramId);
                Faculty faculty = suggestService.getFaculties(instituteId.toString()).get(resultId);
                dbService.saveFaculty(telegramId, Integer.valueOf(faculty.getId()), faculty.getName());
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
