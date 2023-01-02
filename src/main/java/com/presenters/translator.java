package com.presenters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
/*
This presenter class is responsible for connecting to the external interface(Google api) and
connecting it to our internal program.  This class translates the text by taking in the language
codes from the individual language presenter classes
 */
public class translator {

    public static void main(String[] args) {
        String text = "Hello!";
        //Translated text: Hallo Welt!
        System.out.println("Translated text: " + translate("", "af", text));
    }

    static String translate(String langFrom, String langTo, String text)  {
        // INSERT YOU URL HERE
        try{
            String urlStr = "https://script.google.com/macros/s/AKfycbw_wAAny4t8GEQSvbNM8q_SfpXnqJ3Kc9GQih-KtmcnRgCmZSo/exec" +
                    "?q=" + URLEncoder.encode(text, "UTF-8") +
                    "&target=" + langTo +
                    "&source=" + langFrom;
            URL url = new URL(urlStr);
            StringBuilder response = new StringBuilder();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (IOException e) {
            return "";
        }

    }

}