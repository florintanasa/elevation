package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://api.opentopodata.org/v1/mapzen?locations=57.688709,11.976404");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response);

            String json = "{\"results\": [{\"dataset\": \"mapzen\",\"elevation\": 55.0,\"location\": {\"lat\": 57.688709,\"lng\": 11.976404}}],\"status\": \"OK\"}";

            //JsonElement jsonElement = JsonParser.parseString(json);
            //JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
            System.out.println(jsonObject.get("results"));
            System.out.println(jsonObject.get("status"));
        }
    }
}