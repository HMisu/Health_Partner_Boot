package com.bit.healthpartnerboot.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ApiExplorer {
    public void getApi() {
        try {
            /*URL*/
            String urlBuilder = "http://apis.data.go.kr/1390802/AgriFood/MzenFoodNutri/getKoreanFoodIdntList" + "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=8ZcskwShapZjaTsOEAyi%2FH6j66Q98M0c2q%2BJ6DHxWAqNqLw5KPO1dYEp8XVIYJ8YHXaRq4VDMjcCWK6N0Qn8lw%3D%3D" + /*Service Key*/
                    "&" + URLEncoder.encode("food_Code", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("D061001", StandardCharsets.UTF_8); /*음식 코드 식별ID 값 * 식단관리(메뉴젠) 음식 정보 API 참조 (https://www.data.go.kr/data/15077804/openapi.do)*/
            URL url = new URL(urlBuilder);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
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