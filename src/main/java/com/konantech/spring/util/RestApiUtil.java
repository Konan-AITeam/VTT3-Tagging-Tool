package com.konantech.spring.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;


@Component
@ConfigurationProperties
public class RestApiUtil {

    private String darcApiUrl;

    public String streamOut(InputStream inputStream) throws IOException {
        String inputLine = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            stringBuilder.append(StringUtils.trim(inputLine));
            if (StringUtils.isNotEmpty(StringUtils.trim(inputLine))) {
                stringBuilder.append("\r\n");
            }
        }
        in.close();
        return stringBuilder.toString();
    }

    public Map streamOutMap(InputStream inputStream) throws IOException {
        String inputLine = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            stringBuilder.append(StringUtils.trim(inputLine));
            if (StringUtils.isNotEmpty(StringUtils.trim(inputLine))) {
                stringBuilder.append("\r\n");
            }
        }
        in.close();
        String s = stringBuilder.toString();
        return (LinkedHashMap) JSONUtils.jsonStringToObject(s, LinkedHashMap.class);

    }

    public Map doPost(String url, Map<String, String> params) throws Exception {
        try {
            String boundary = "01234567890415";
            URL obj = new URL(darcApiUrl + url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            String delimiter = "\r\n--" + boundary + "\r\n";
            String lastDelimiter = "\r\n--" + boundary + "--";
            StringBuffer postDataBuilder = new StringBuffer();
            for( String key : params.keySet() ){
                postDataBuilder.append(delimiter);
                postDataBuilder.append(setValue(key, params.get(key)));
            }
            postDataBuilder.append(lastDelimiter);

            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(conn.getOutputStream()));
            out.writeUTF(postDataBuilder.toString());
            out.writeBytes(delimiter);
            out.flush();
            out.close();
            return streamOutMap(conn.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String url, String input) throws IOException {
        String output = "";
        String result = "";

        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Content-Type", "application/json");


        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
            result += output;
        }

        conn.disconnect();

        return result;
    }


    public static String put(String url, String input) throws IOException {
        String output = "";
        String result = "";

        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
            result += output;
        }

        conn.disconnect();

        return result;
    }

    public String setValue(String key, String value) {
        return "Content-Disposition: form-data; name=" + key + "\r\n"
                + "Content-type: application/octet-stream; charset=utf-8\r\n\r\n"
                + value;
    }

    public String doGet(String url) throws Exception {
        try {
            URL obj = new URL(darcApiUrl + url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.getResponseCode();
            return streamOut(conn.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
