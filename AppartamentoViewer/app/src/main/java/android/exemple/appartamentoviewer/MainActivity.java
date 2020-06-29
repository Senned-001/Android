package android.exemple.appartamentoviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread newThread = new Thread(){
            @Override
            public void run() {
                JsonHelper.getJSONObjectFromURL(JsonHelper.urlCountries);
            }
        };
        newThread.start();
    }
}
