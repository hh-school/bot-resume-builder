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
public class URLRequest {
    private final static Logger log = LoggerFactory.getLogger(URLRequest.class);

    public static String get(String urlToRead) {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
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

    public static String get(String url, Map<String, String> queryParams) {
        try {
            if (!queryParams.isEmpty()) {
                StringBuilder query = new StringBuilder();
                query.append("?");
                for (Map.Entry<String, String> param : queryParams.entrySet()) {
                    query.append(String.format("%s=%s&",
                            URLEncoder.encode(param.getKey(), "UTF-8"),
                            URLEncoder.encode(param.getValue(), "UTF-8")));
                }
                query.deleteCharAt(query.length()-1);
                url = url + query.toString();
            }
            return get(url);
        } catch (IOException e) {
            log.error(e.getMessage());
            return "error";
        }
    }
}
