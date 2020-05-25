package android.exemple.sellingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Product> productList = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        MainDataAdapter adapter = new MainDataAdapter(this, productList);
        recyclerView.setAdapter(adapter);
    }

    public static List<Order> getOrders() {
        return orders;
    }

    private void initData(){
        String[] textData = getResources().getStringArray(R.array.str);
        for(int i=0;i<textData.length;i++){
            int imageID = getResources().getIdentifier("img"+i, "drawable", "android.exemple.sellingapp");
            productList.add(new Product(textData[i],imageID));
        }
    }

    public void onBasketClick(View view){
        if(orders.isEmpty())
            Toast.makeText(this, "Basket is empty", Toast.LENGTH_LONG).show();
        else{
            Intent intentOrder = new Intent(this, BasketActivity.class);
            startActivity(intentOrder);
        }
    }

    //click on header = call to operator
    public void onHeaderClick(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1234567"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //changing of icon of basket
    @Override
    protected void onResume() {
        ImageView basketImage = findViewById(R.id.basket);
        if(orders.isEmpty())
            basketImage.setImageResource(R.drawable.basket);
        else
            basketImage.setImageResource(R.drawable.basket2);
        super.onResume();
    }
}
