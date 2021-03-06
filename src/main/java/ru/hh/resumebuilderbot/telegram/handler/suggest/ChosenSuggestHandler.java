package ru.hh.resumebuilderbot.telegram.handler.suggest;

import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.SuggestService;
import ru.hh.resumebuilderbot.http.response.entity.Area;
import ru.hh.resumebuilderbot.http.response.entity.Company;
import ru.hh.resumebuilderbot.http.response.entity.Faculty;
import ru.hh.resumebuilderbot.http.response.entity.Institute;
import ru.hh.resumebuilderbot.http.response.entity.Position;
import ru.hh.resumebuilderbot.http.response.entity.Skill;
import ru.hh.resumebuilderbot.http.response.entity.StudyField;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.Handler;

import java.util.List;
import java.util.stream.Collectors;

public class ChosenSuggestHandler extends Handler {
    private final SuggestService suggestService;

    public ChosenSuggestHandler(DBProcessor dbProcessor, Graph graph, SuggestService suggestService) {
        super(dbProcessor, graph, ChosenSuggestHandler.class);
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
                    dbProcessor.setCurrentEducationInstitute(
                            telegramId,
                            Integer.valueOf(institute.getId()),
                            institute.getText()
                    );
                    break;
                case COMPANIES_SUGGEST:
                    Company company = suggestService.getCompanies(queryText).get(resultId);
                    dbProcessor.saveCurrentExperienceCompany(
                            telegramId,
                            company.getName(),
                            Integer.valueOf(company.getId())
                    );
                    break;
                case STUDY_FIELDS_SUGGEST:
                    StudyField studyField = suggestService.getStudyFields(queryText).get(resultId);
                    dbProcessor.setCurrentEducationSpeciality(telegramId, Integer.valueOf(studyField.getId()),
                            studyField.getText());
                    break;
                case SKILLS_SUGGEST:
                    Skill skill = suggestService.getSkills(queryText).get(resultId);
                    dbProcessor.saveUserSkill(telegramId, skill.getText(), Integer.valueOf(skill.getId()));
                    break;
                case POSITIONS_SUGGEST:
                    Position position = suggestService.getPositions(queryText).get(resultId);
                    if (getCurrentNode(telegramId).getFieldNameToSave().equals("workPosition")) {
                        dbProcessor.setCurrentExperiencePosition(telegramId, position.getName());
                    } else {
                        dbProcessor.setCareerObjectiveWithSpecializations(telegramId, position);
                    }
                    break;
                case AREAS_SUGGEST:
                    Area area = suggestService.getAreas(queryText).get(resultId);
                    dbProcessor.saveUserArea(telegramId, area.getText(), Integer.valueOf(area.getId()));
                    break;
                case FACULTIES_SUGGEST:
                    String lowCaseQueryText = queryText.toLowerCase();
                    String instituteHHId = dbProcessor.getInstituteHHId(telegramId).toString();
                    List<Faculty> queryResults = suggestService.getFaculties(instituteHHId, queryText);
                    if (queryResults == null || queryResults.isEmpty()) {
                        break;
                    }
                    Faculty chosenFaculty = queryResults.stream()
                            .filter(faculty -> faculty.getName().toLowerCase().contains(lowCaseQueryText))
                            .collect(Collectors.toList())
                            .get(resultId);
                    dbProcessor.setCurrentEducationFaculty(
                            telegramId,
                            Integer.valueOf(chosenFaculty.getId()),
                            chosenFaculty.getName()
                    );
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }
}
