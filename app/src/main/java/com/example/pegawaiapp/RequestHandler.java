package com.example.pegawaiapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHandler {

    public String senGetRequest(String requestURL){
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(10000);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String s;
            while ((s=bufferedReader.readLine())!=null){
                sb.append(s+"\n");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
