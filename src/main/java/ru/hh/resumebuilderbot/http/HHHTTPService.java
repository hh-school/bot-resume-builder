package ru.hh.resumebuilderbot.http;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.http.request.entity.Negotiation;
import ru.hh.resumebuilderbot.http.response.entity.Area;
import ru.hh.resumebuilderbot.http.response.entity.Company;
import ru.hh.resumebuilderbot.http.response.entity.Faculty;
import ru.hh.resumebuilderbot.http.response.entity.Institute;
import ru.hh.resumebuilderbot.http.response.entity.Position;
import ru.hh.resumebuilderbot.http.response.entity.Skill;
import ru.hh.resumebuilderbot.http.response.entity.Specialization;
import ru.hh.resumebuilderbot.http.response.entity.Vacancy;

import java.util.List;
import java.util.Map;

public interface HHHTTPService {
    @GET("educational_institutions/{institution_id}/faculties")
    Call<List<Faculty>> listFaculties(@Path("institution_id") String institutionId, @Query("locale") String locale);

    @POST("negotiations")
    Call<Void> createNegotiation(@Body Negotiation negotiation, @Header("Authorization") String authorization);

    @POST("resumes")
    Call<Void> createResume(@Body User user, @Header("Authorization") String authorization);

    @DELETE("resumes/{resume_id}")
    Call<Void> deleteResume(@Path("resume_id") String resumeId, @Header("Authorization") String authorization);

    @POST("resumes/{resume_id}/publish")
    Call<Void> publishResume(@Path("resume_id") String resumeId, @Header("Authorization") String authorization);

    @GET("resumes/{resume_id}/similar_vacancies")
    Call<List<Vacancy>> listResumeSimilarVacancies(
            @Path("resume_id") String resumeId,
            @Header("Authorization") String authorization
    );

    @GET("suggests/areas")
    Call<List<Area>> listAreas(@Query("text") String text, @Query("locale") String locale);

    @GET("suggests/companies")
    Call<List<Company>> listCompanies(@Query("text") String text, @Query("locale") String locale);

    @GET("suggests/educational_institutions")
    Call<List<Institute>> listInstitutes(@Query("text") String text, @Query("locale") String locale);

    @GET("suggests/fields_of_study")
    Call<List<Specialization>> listSpecializations(@Query("text") String text, @Query("locale") String locale);

    @GET("suggests/positions")
    Call<List<Position>> listPositions(@Query("text") String text, @Query("locale") String locale);

    @GET("suggests/skill_set")
    Call<List<Skill>> listSkills(@Query("text") String text, @Query("locale") String locale);

    @GET("vacancies")
    Call<List<Vacancy>> listVacancies(@QueryMap Map<String, String> queryParams);
}
