package android.exemple.appartamentoviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    final String[] inputData = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editTextCountryID = (EditText) findViewById(R.id.countryID);
        EditText editTextcityID = (EditText) findViewById(R.id.cityID);
        editTextCountryID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputData[0] = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextcityID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputData[1] = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    public void onSearchButtonClick (View view){
        JSONObject js = JsonHelper.getJSONDataForCountryAndCity(JsonHelper.getJSONObjectFromURL(JsonHelper.urlRequestCountries), inputData[0], inputData[1]);
        JSONArray flatsData = JsonHelper.getJSONDataForCityId(js);
    }
}
