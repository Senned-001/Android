package android.exemple.appartamentoviewer;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

        List<Appartament> listOfAppartaments = new ArrayList<>();
        for(int i=0;i<flatsData.length();i++){
            try {
                JSONObject flat = flatsData.getJSONObject(i);
                Appartament appartament = new Appartament();
                appartament.setId(flat.getInt("id"));
                appartament.setTitle(flat.getString("name"));
                appartament.setCoast(flat.getJSONObject("prices").getInt("day"));
                appartament.setAddress(flat.getString("address"));
                appartament.setNumberOfRooms(flat.getInt("rooms"));
                appartament.setInfo(flat.getString("description"));
                appartament.setMainPhoto(flat.getJSONObject("photo_default").getString("url"));
                appartament.setCoordinate1(flat.getJSONObject("coordinates").getString("lat"));
                appartament.setCoordinate1(flat.getJSONObject("coordinates").getString("lon"));
                appartament.setName(flat.getJSONObject("contacts").getString("name"));
                JSONObject phone = flat.getJSONObject("contacts").getJSONArray("phones").getJSONObject(0);
                appartament.setPhone(phone.getString("phone"));
                appartament.setDescription(flat.getString("description_full"));
                JSONArray photos = flat.getJSONArray("photos");
                String[] photosOfAppartaments = new String[photos.length()];
                for(int j=0;j<photos.length();j++){
                    photosOfAppartaments[i]=photos.getJSONObject(i).getString("url");
                }
                appartament.setPhotos(photosOfAppartaments);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
