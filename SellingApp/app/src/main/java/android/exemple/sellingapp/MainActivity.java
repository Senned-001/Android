package android.exemple.sellingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        DataAdapter adapter = new DataAdapter(this, productList);
        recyclerView.setAdapter(adapter);



        /*
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
        ll.setId(1);

        LayoutInflater ltInflater = getLayoutInflater();
        View view = ltInflater.inflate(ll.getResources().getLayout(1), null);

         */
    }

    private void initData(){
        String[] textData = getResources().getStringArray(R.array.str);
        for(int i=0;i<textData.length;i++){
            int imageID = getResources().getIdentifier("img"+i, "drawable", "android.exemple.sellingapp");
            productList.add(new Product(textData[i],imageID));
        }
    }

    public void onProductClick(int itemPosition){
        Toast.makeText(this, productList.get(itemPosition).getName(), Toast.LENGTH_LONG).show();
/*
        Intent intentOrder = new Intent(this, OrderActivity.class);
        intentOrder.putExtra("ID", getResources().getResourceEntryName(view.getId()).replace("id",""));
        startActivity(intentOrder);*/
    }
}
