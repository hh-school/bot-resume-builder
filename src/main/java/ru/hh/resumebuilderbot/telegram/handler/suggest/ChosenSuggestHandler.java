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

import java.util.List;
import java.util.stream.Collectors;

public class ChosenSuggestHandler extends Handler {
    private final SuggestService suggestService;

    public ChosenSuggestHandler(DBService dbService, Graph graph, SuggestService suggestService) {
        super(dbService, graph);
        this.suggestService = suggestService;
    }

    public void saveChosenSuggest(Long telegramId, Integer resultId, String queryText) {
        if (resultId > 50) {
            log.warn("User {} select notification suggest with id={}", telegramId, resultId);
        } else {
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
                    dbService.saveSkill(telegramId, skill.getText(), Integer.valueOf(skill.getId()));
                    break;
                case POSITIONS_SUGGEST:
                    Position position = suggestService.getPositions(queryText).get(resultId);
                    if (getCurrentNode(telegramId).getFieldNameToSave().equals("workPosition")) {
                        dbService.saveExperiencePosition(telegramId, position.getText());
                    } else {
                        //TODO add specialization and profArea save
                        dbService.saveCareerObjective(telegramId, position.getText());
                    }
                    break;
                case AREAS_SUGGEST:
                    Area area = suggestService.getAreas(queryText).get(resultId);
                    dbService.saveUserArea(telegramId, area.getText(), Integer.valueOf(area.getId()));
                    break;
                case FACULTIES_SUGGEST:
                    String lowCaseQueryText = queryText.toLowerCase();
                    String instituteHHId = dbService.getInstituteHHId(telegramId).toString();
                    List<Faculty> queryResults = suggestService.getFaculties(instituteHHId, queryText);
                    if (queryResults == null || queryResults.isEmpty()) {
                        break;
                    }
                    Faculty chosenFaculty = queryResults.stream()
                            .filter(faculty -> faculty.getName().toLowerCase().contains(lowCaseQueryText))
                            .collect(Collectors.toList())
                            .get(resultId);
                    dbService.saveFaculty(telegramId, Integer.valueOf(chosenFaculty.getId()), chosenFaculty.getName());
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }
}
