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
    public static String urlCountries = "https://api.beta.kvartirka.pro/client/1.4/country";

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
            urlConnection.setReadTimeout(10000 /* milliseconds */ );
            urlConnection.setConnectTimeout(15000 /* milliseconds */ );
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

    public void getJSONData (JSONObject jsonObject){
        JSONArray citiesFrom = jsonObject.getJSONArray("countries ");

    }
}
