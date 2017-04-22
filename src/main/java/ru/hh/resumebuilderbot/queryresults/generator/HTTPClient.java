package ru.hh.resumebuilderbot.queryresults.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Sergey on 21.04.2017.
 */
public class HTTPClient {
    private final static Logger log = LoggerFactory.getLogger(HTTPClient.class);

    private final static String divider = "&";

    private final static String queryBeginner = "?";

    public static String sendGetRequest(String urlToRead) {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");//проверить с другим именем
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            return result.toString();
        } catch (IOException e) {
            log.error(e.getMessage());
            return "Error";
        }
    }

    public static String sendGetRequest(String url, Map<String, String> queryParams) {
        String urlWithParams = url;
        if (!queryParams.isEmpty()) {
            for (Map.Entry<String, String> param : queryParams.entrySet()) {
                urlWithParams = addQueryParameter(urlWithParams, param.getKey(), param.getValue());
            }
        }
        return sendGetRequest(urlWithParams);
    }

    private static String addQueryParameter(String url, String param, String value) {
        try {
            StringBuilder urlWithParam = new StringBuilder(url);
            if (url.contains(queryBeginner)) {
                urlWithParam.append(divider);
            } else {
                urlWithParam.append(queryBeginner);
            }
            urlWithParam.append(String.format("%s=%s",
                    URLEncoder.encode(param, "UTF-8"),
                    URLEncoder.encode(value, "UTF-8")));
            return urlWithParam.toString();
        } catch (IOException e) {
            log.error(e.getMessage());
            return url;
        }
    }
}
