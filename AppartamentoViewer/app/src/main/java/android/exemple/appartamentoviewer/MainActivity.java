package android.exemple.appartamentoviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    public static List<Appartament> appartamentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        JSONObject jso = JsonHelper.getJSONObjectFromURL(JsonHelper.urlRequestCountries);
  //      JSONObject js = JsonHelper.getJSONDataForCountryAndCity(jso, "Россия", "Москва");
    //    JSONArray flatsData = JsonHelper.getJSONDataForCityId(js);

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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jso = JsonHelper.getJSONObjectFromURL(JsonHelper.urlRequestCountries);
                JSONObject js = JsonHelper.getJSONDataForCountryAndCity(jso, inputData[0], inputData[1]);
                JSONArray flatsData = JsonHelper.getJSONDataForCityId(js);

                //parsing JSONArray to List of Appartaments
                List<Appartament> listOfAppartaments = new ArrayList<>();
                for (int i = 0; i < flatsData.length(); i++) {
                    try {
                        JSONObject flat = flatsData.getJSONObject(i);
                        Appartament appartament = new Appartament();
                        appartament.setId(flat.getInt("id"));
                        appartament.setTitle(flat.getString("title"));
                        appartament.setCoast(flat.getJSONObject("prices").getInt("day"));
                        appartament.setAddress(flat.getString("address"));
                        appartament.setNumberOfRooms(flat.getInt("rooms"));
                        appartament.setInfo(flat.getString("description"));
                        appartament.setMainPhoto(flat.getJSONObject("photo_default").getString("url"));
                        appartament.setCoordinate1(flat.getJSONObject("coordinates").getString("lon"));
                        appartament.setCoordinate2(flat.getJSONObject("coordinates").getString("lat"));
                        appartament.setName(flat.getJSONObject("contacts").getString("name"));
                        JSONObject phone = flat.getJSONObject("contacts").getJSONArray("phones").getJSONObject(0);
                        appartament.setPhone(phone.getString("phone"));
                        appartament.setDescription(flat.getString("description_full"));
                        JSONArray photos = flat.getJSONArray("photos");
                        String[] photosOfAppartaments = new String[photos.length()];
                        for (int j = 0; j < photosOfAppartaments.length; j++) {
                            photosOfAppartaments[j] = photos.getJSONObject(j).getString("url");
                        }
                        appartament.setPhotos(photosOfAppartaments);
                        listOfAppartaments.add(appartament);
                        //System.out.println(appartament.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                appartamentList = listOfAppartaments;
                Intent intentOrder = new Intent(getApplicationContext(), ListOfAppActivity.class);
                startActivity(intentOrder);

            }
        });
        thread.start();
    }
}
