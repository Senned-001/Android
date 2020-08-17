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
    private static String versionAPI = "1.4";
    public static String urlRequestCountries = "https://api.beta.kvartirka.pro/client/" + versionAPI + "/country";
    private static String defaultCountryName = "Россия";
    private static String defaultCityName = "Москва";
    public static String urlRequestFlatsFromCity = "http://api.kvartirka.com/client/" + versionAPI + "/flats/?offset=0&device_screen_width=1920&currency_id=643&city_id=";

    //GET request
    public static JSONObject getJSONObjectFromURL(String URLRequest) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(URLRequest);
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
            System.out.println("JSON: " + jsonString);

            return new JSONObject(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONDataForCountryAndCity (JSONObject jsonObject, String country, String city){
        if(country==null)
            country= defaultCountryName;
        if(city==null)
            country=defaultCityName;

        JSONObject countryData = null;
        JSONObject cityData = null;
        //search of country
        try {
            JSONArray countries = jsonObject.getJSONArray("countries");
            JSONObject countryDefault = null;
            for (int i=0; i<countries.length();i++){
                if(countries.getJSONObject(i).getString("name").equals(country))
                    countryData = countries.getJSONObject(i);
                if(countries.getJSONObject(i).getString("name").equals(defaultCountryName))
                    countryDefault = countries.getJSONObject(i);
            }
            //if country was not found - take default country
            if(countryData==null)
                countryData = countryDefault;
            System.out.println("JSON countryData: "+ countryData.toString());

            //search of city
            JSONArray cities = countryData.getJSONArray("cities");
            JSONObject cityDefault = null;
            for (int i=0; i<cities.length();i++){
                if(cities.getJSONObject(i).getString("name").equals(city))
                    cityData = cities.getJSONObject(i);
                if(cities.getJSONObject(i).getString("name").equals(defaultCityName))
                    cityDefault = cities.getJSONObject(i);

            }
            //if city was not found - take default city
            if(cityData==null)
                cityData = cityDefault;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //System.out.println("JSON citydata: " + cityData.toString());
        //return JSONObect with data of one city
        return cityData;
    }

    public static JSONArray getJSONDataForCityId(JSONObject cityData){
        String cityID = null;
        try {
            cityID = cityData.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject flatData = getJSONObjectFromURL(urlRequestFlatsFromCity+cityID);
        try {
            JSONArray flatArray = flatData.getJSONArray("flats");
            //System.out.println(flatArray);

            //return JSON Array with flats from one city
            return flatArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
