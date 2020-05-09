package android.exemple.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        Intent intent = new Intent(this, Display2Activity.class);
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        String number = quantityTextView.getText().toString();
        intent.putExtra("NUMBER", number);

        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        String price = priceTextView.getText().toString();
        intent.putExtra("PRICE", price);

        startActivity(intent);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    public void changeQuantity (View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        int value = Integer.parseInt(quantityTextView.getText().toString());

        if(view.equals(findViewById(R.id.button_max_quantity))){
            value++;
            quantityTextView.setText("" + value);
        }
        if(view.equals(findViewById(R.id.button_min_quantity))&&value > 0){
            value--;
            quantityTextView.setText("" + value);
        }
        changePrice(value);
    }

    public void changePrice (int number){
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText("" + number*5);
    }
}
