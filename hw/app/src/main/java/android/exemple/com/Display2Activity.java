package android.exemple.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Display2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display2);

        Intent intent = getIntent();
        TextView textView = (TextView) findViewById(R.id.display2message);
        String message = "You Have " + intent.getStringExtra("NUMBER") + " orders,\nTotal Price: "+ intent.getStringExtra("PRICE");
        textView.setText(message);
    }
}
