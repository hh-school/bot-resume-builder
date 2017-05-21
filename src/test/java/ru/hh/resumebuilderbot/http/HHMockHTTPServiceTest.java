package ru.hh.resumebuilderbot.http;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import retrofit2.Response;
import ru.hh.resumebuilderbot.http.response.entity.Vacancy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class HHMockHTTPServiceTest {
    public static HHHTTPService hhHTTPService;
    private MockWebServer server;

    @BeforeClass
    public void setUpClass() throws Exception {
        server = new MockWebServer();
        server.start();
        hhHTTPService = HHHTTPServiceTestUtils.getService(
                HHHTTPServiceUtils.buildGson(),
                HHHTTPServiceUtils.buildHttpClient(),
                server.url("/").toString()
        );
    }

    @AfterClass
    public void tearDownClass() throws IOException {
        server.shutdown();
    }

    private List<Vacancy> getExpectedVacancies() {
        List<Vacancy> vacancies = new ArrayList<>();
        Vacancy vacancy1 = new Vacancy();
        vacancy1.setId("20443823");
        vacancy1.setName("Программист Java");
        vacancy1.setUrl("https://hh.ru/vacancy/20443823");
        vacancy1.setCompanyName("Brain4net");
        vacancy1.setCompanyLogo("https://hhcdn.ru/employer-logo-original/311081.jpg");
        vacancies.add(vacancy1);

        Vacancy vacancy2 = new Vacancy();
        vacancy2.setId("20358943");
        vacancy2.setName("Разработчик Java");
        vacancy2.setUrl("https://hh.ru/vacancy/20358943");
        vacancy2.setCompanyName("Наука");
        vacancy2.setCompanyLogo("https://hhcdn.ru/employer-logo-original/396426.jpg");
        vacancy2.setSalaryFrom(60000);
        vacancy2.setSalaryCurrency("RUR");
        vacancies.add(vacancy2);

        Vacancy vacancy3 = new Vacancy();
        vacancy3.setId("20402013");
        vacancy3.setName("Java-программист");
        vacancy3.setUrl("https://hh.ru/vacancy/20402013");
        vacancy3.setCompanyName("Immoviewer");
        vacancy3.setSalaryFrom(2000);
        vacancy3.setSalaryTo(2500);
        vacancy3.setSalaryCurrency("USD");
        vacancies.add(vacancy3);
        return vacancies;
    }

    @Test
    public void testListVacancies() throws Exception {
        String filePath = "http/HHHTTPServiceTest/vacancies.json";
        server.enqueue(new MockResponse().setBody(readStringFile(filePath)));

        Response<List<Vacancy>> response = hhHTTPService.listVacancies(new HashMap<>()).execute();

        List<Vacancy> vacancies = response.body();
        List<Vacancy> expectedVacancies = getExpectedVacancies();
        assertEquals(vacancies.size(), expectedVacancies.size());
        for (int i = 0; i < vacancies.size(); ++i){
            assertEquals(vacancies.get(i), expectedVacancies.get(i));
        }
    }

    public String readStringFile(String filePath) throws Exception {
        return new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(filePath).toURI())));
    }
}
