package com.example.diseasedictionary;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler {

    static String response23 = "";

    public static String[] apiCallMethod(String diseaseName) {
        String[] info = null;

        new Thread() {
            public void run() {
                try {

                    URL url = new URL("https://disease-info-api.herokuapp.com/diseases/" + diseaseName);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setAllowUserInteraction(false);
                    conn.setInstanceFollowRedirects(true);
                    conn.setRequestMethod("GET");
                    conn.connect();
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    in.close();
                    response23 = sb.toString();



                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        return info;
    }

    public static String[] jsonFormatter(String response, String name){
        String[] info = new String[7];

        try{
            JSONObject jsonObject = new JSONObject(response);
            info[0] = jsonObject.getJSONObject("disease").getString("symptoms");
            info[1] = jsonObject.getJSONObject("disease").getString("transmission");
            info[2] = jsonObject.getJSONObject("disease").getString("treatments");
            info[4] = jsonObject.getJSONObject("disease").getString("diagnosis");
            info[5] = jsonObject.getJSONObject("disease").getString("prevention");
            info[3] = jsonObject.getJSONObject("disease").getJSONArray("facts").getString(1);
        } catch (JSONException jsonException){
            apiCallMethod(name);
        }
        return info;
    }


}
