package ru.hh.resumebuilderbot;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import ru.hh.resumebuilderbot.http.HHHTTPService;
import ru.hh.resumebuilderbot.http.response.entity.Area;
import ru.hh.resumebuilderbot.http.response.entity.Company;
import ru.hh.resumebuilderbot.http.response.entity.Faculty;
import ru.hh.resumebuilderbot.http.response.entity.Institute;
import ru.hh.resumebuilderbot.http.response.entity.Position;
import ru.hh.resumebuilderbot.http.response.entity.Skill;
import ru.hh.resumebuilderbot.http.response.entity.Specialization;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@Singleton
public class SuggestService {
    private static final Logger log = LoggerFactory.getLogger(SuggestService.class);
    private static final Pattern QUERY_LANGUAGE_PATTERN = Pattern.compile("[а-яА-ЯёЁ]*");
    private final HHHTTPService hhHTTPService;

    @Inject
    public SuggestService(HHHTTPService hhHTTPService) {
        this.hhHTTPService = hhHTTPService;
    }

    private static String getQueryLanguage(String query) {
        if (query == null || QUERY_LANGUAGE_PATTERN.matcher(query).matches()) {
            return "RU";
        }
        return "EN";
    }

    private <T> List<T> makeSafeRequest(Supplier<Call<List<T>>> supplier) {
        Response<List<T>> response;
        try {
            response = supplier.get().execute();
        } catch (IOException e) {
            log.error("IO error at HH request", e);
            return null;
        }
        if (response.code() != 200) {
            log.error("Bad response code {}", response.code());
            return null;
        }
        return response.body();
    }

    public List<Institute> getInstitutes(String searchQuery) {
        return makeSafeRequest(
                () -> hhHTTPService.listInstitutes(searchQuery, getQueryLanguage(searchQuery))
        );
    }

    public List<Faculty> getFaculties(String instituteId, String searchQuery) {
        return makeSafeRequest(
                () -> hhHTTPService.listFaculties(instituteId, getQueryLanguage(searchQuery))
        );
    }

    public List<Company> getCompanies(String searchQuery) {
        return makeSafeRequest(
                () -> hhHTTPService.listCompanies(searchQuery, getQueryLanguage(searchQuery))
        );
    }

    public List<Specialization> getSpecializations(String searchQuery) {
        return makeSafeRequest(
                () -> hhHTTPService.listSpecializations(searchQuery, getQueryLanguage(searchQuery))
        );
    }

    public List<Skill> getSkills(String searchQuery) {
        return makeSafeRequest(
                () -> hhHTTPService.listSkills(searchQuery, getQueryLanguage(searchQuery))
        );
    }

    public List<Position> getPositions(String searchQuery) {
        return makeSafeRequest(
                () -> hhHTTPService.listPositions(searchQuery, getQueryLanguage(searchQuery))
        );
    }

    public List<Area> getAreas(String searchQuery) {
        return makeSafeRequest(
                () -> hhHTTPService.listAreas(searchQuery, getQueryLanguage(searchQuery))
        );
    }
}
