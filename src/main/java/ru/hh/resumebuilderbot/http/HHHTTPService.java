package ru.hh.resumebuilderbot.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.hh.resumebuilderbot.http.response.entity.Area;
import ru.hh.resumebuilderbot.http.response.entity.Company;
import ru.hh.resumebuilderbot.http.response.entity.Faculty;
import ru.hh.resumebuilderbot.http.response.entity.Institute;
import ru.hh.resumebuilderbot.http.response.entity.Position;
import ru.hh.resumebuilderbot.http.response.entity.Skill;
import ru.hh.resumebuilderbot.http.response.entity.Specialization;

import java.util.List;

public interface HHHTTPService {
    @GET("suggests/educational_institutions")
    Call<List<Institute>> listInstitutes(@Query("text") String text, @Query("locale") String locale);

    @GET("suggests/companies")
    Call<List<Company>> listCompanies(@Query("text") String text, @Query("locale") String locale);

    @GET("suggests/fields_of_study")
    Call<List<Specialization>> listSpecializations(@Query("text") String text, @Query("locale") String locale);

    @GET("suggests/skill_set")
    Call<List<Skill>> listSkills(@Query("text") String text, @Query("locale") String locale);

    @GET("suggests/positions")
    Call<List<Position>> listPositions(@Query("text") String text, @Query("locale") String locale);

    @GET("suggests/areas")
    Call<List<Area>> listAreas(@Query("text") String text, @Query("locale") String locale);

    @GET("educational_institutions/{id}/faculties")
    Call<List<Faculty>> listFaculties(@Path("id") String instituteId, @Query("locale") String locale);
}
