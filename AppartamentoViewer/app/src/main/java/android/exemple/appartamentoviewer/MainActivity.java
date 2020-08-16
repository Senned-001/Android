package android.exemple.appartamentoviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread newThread = new Thread(){
            @Override
            public void run() {
                JSONObject js = JsonHelper.getJSONDataForCountryAndCity(JsonHelper.getJSONObjectFromURL(JsonHelper.urlRequestCountries), "Россия", "Ростов");
                JsonHelper.getJSONDataForCityId(js);
            }
        };
        newThread.start();
    }
}
