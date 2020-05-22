package android.exemple.sellingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String[] textData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textData = getResources().getStringArray(R.array.str);
        GridLayout grid = new GridLayout(this);
        grid.setColumnCount(2);
        grid.setRowCount(Math.round(textData.length/2));

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ImageView iw = new ImageView(this);
        iw.setImageResource(R.drawable.img0);
        TextView tw = new TextView(this);
        tw.setText("Text1");
        ll.addView(iw);
        ll.addView(tw);


        LayoutInflater ltInflater = getLayoutInflater();
        View view = ltInflater.inflate(ll, null);
    }

    public void onProductClick(View view){

        Intent intentOrder = new Intent(this, OrderActivity.class);
        intentOrder.putExtra("ID", getResources().getResourceEntryName(view.getId()).replace("id",""));
        startActivity(intentOrder);
    }
}
