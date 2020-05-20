package android.exemple.sellingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onProductClick(View view){

        Intent intentOrder = new Intent(this, OrderActivity.class);
        intentOrder.putExtra("ID", getResources().getResourceEntryName(view.getId()).replace("id",""));
        startActivity(intentOrder);
    }
}
