package org.example;

import com.google.gson.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://api.opentopodata.org/v1/mapzen?locations=47.636246,26.240526");
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
            //System.out.println(response);

            //String json = "{\"results\": [{\"dataset\": \"mapzen\",\"elevation\": 55.0,\"location\": {\"lat\": 57.688709,\"lng\": 11.976404}}],\"status\": \"OK\"}";

            //JsonObject jsonObject = new Gson().fromJson(response.toString(), JsonObject.class);

            //System.out.println(jsonObject.get("results"));
            //System.out.println(jsonObject.get("status"));


            JsonElement jsonElement = JsonParser.parseString(response.toString());
            JsonObject jsonObjectArray = jsonElement.getAsJsonObject();
            JsonArray jsonArray = jsonObjectArray.getAsJsonArray("results");

            //System.out.println(jsonArray.asList().get(0));

            JsonObject jsonObject1 = new Gson().fromJson(jsonArray.asList().get(0).toString(), JsonObject.class);

            String elev = String.valueOf(jsonObject1.get("elevation"));
            System.out.println("Altitude  is: "+elev);
            double altitude = Double.parseDouble(elev);
            int elevation = (int) altitude;
            System.out.println("Elevation is: "+elevation);
        }
    }
}