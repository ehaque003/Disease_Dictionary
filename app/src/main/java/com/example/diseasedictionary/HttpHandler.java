package com.example.diseasedictionary;

import android.util.Log;

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
    String response23 = "";
    public static final String TAG = "Info";
    String[] info = {"", "", "", "", "", "", ""};
    public void apiCallMethod(String diseaseName) throws InterruptedException {
        new Thread() {
            public void run() {
                try {
                    URL url = new URL("https://disease-info-api.herokuapp.com/diseases/"+diseaseName+".json");
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
                    String message = sb.toString();
                    Log.d("Warning", message);
                    if(message.isEmpty()){
                        response23="None";
                    }
                    else{
                        response23 = message;
                    }


                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Thread.sleep(2000);

    }

    public void jsonFormatter(String name) throws InterruptedException {
        apiCallMethod(name);

    }


}
