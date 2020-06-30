package android.exemple.appartamentoviewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonHelper {
    public static String urlRequestCountries = "https://api.beta.kvartirka.pro/client/1.4/country";
    private static String DefaultCountryName = "Россия";
    private static String DefaultCityName = "Москва";

    public static JSONObject getJSONObjectFromURL(String urlString) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("X-Client-ID", "test");
            urlConnection.setRequestProperty("X-Device-ID", "test");
            urlConnection.setRequestProperty("X-Device-OS", "test");
            urlConnection.setRequestProperty("X-ApplicationVersion", "test");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            String jsonString = sb.toString();
            //System.out.println("JSON: " + jsonString);

            return new JSONObject(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getJSONDataForCountryAndCity (JSONObject jsonObject, String country, String city){
        JSONObject countryData = null;
        JSONObject cityData = null;
        //search of country
        try {
            JSONArray countries = jsonObject.getJSONArray("countries");
            JSONObject countryDefault = null;
            for (int i=0; i<countries.length();i++){
                if(countries.getJSONObject(i).getString("name")==country)
                    countryData = countries.getJSONObject(i);
                if(countries.getJSONObject(i).getString("name")==DefaultCountryName)
                    countryDefault = countries.getJSONObject(i);
            }
            //if country was not found - take default country
            if(countryData==null)
                countryData = countryDefault;

            //search of city
            JSONArray cities = countryData.getJSONArray("cities");
            JSONObject cityDefault = null;
            for (int i=0; i<cities.length();i++){
                if(cities.getJSONObject(i).getString("name")==city)
                    cityData = countries.getJSONObject(i);
                if(cities.getJSONObject(i).getString("name")==DefaultCityName)
                    cityDefault = countries.getJSONObject(i);
            }
            //if city was not found - take default city
            if(cityData==null)
                cityData = cityDefault;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cityData;
    }


}
