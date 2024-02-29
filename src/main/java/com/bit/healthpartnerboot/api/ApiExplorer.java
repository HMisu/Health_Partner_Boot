package com.bit.healthpartnerboot.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiExplorer {
    public void getApi() {
        try {
            String urlBuilder = "http://apis.data.go.kr/1390802/AgriFood/FdImage/getKoreanFoodFdImageList?serviceKey=8ZcskwShapZjaTsOEAyi%2FH6j66Q98M0c2q%2BJ6DHxWAqNqLw5KPO1dYEp8XVIYJ8YHXaRq4VDMjcCWK6N0Qn8lw%3D%3D&service_Type=json&Page_No=1&Page_Size=20";
            URL url = new URL(urlBuilder);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
                System.out.println("Response code: " + conn.getResponseCode());
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            System.out.println(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}