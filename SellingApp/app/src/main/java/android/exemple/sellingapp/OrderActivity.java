package android.exemple.sellingapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {
    private Product product;
    private int quantity = 1;
    private double totalSum = 0.00;
    private String paramSelected = "";
    private String orderInfo = "";
    private boolean bonus1IsChecked = false;
    private boolean bonus2IsChecked = false;
    private double price = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intentOrder = getIntent();
        product = (Product) intentOrder.getSerializableExtra("Product");
        initialize();
    }

    private void setImage(){
        ImageView img = (ImageView) findViewById(R.id.imgOrder);
        img.setImageResource(product.getImageID());
    }

    private void initialize(){
        TextView nameText = (TextView) findViewById(R.id.name);
        nameText.setText(product.getName());
        TextView priceText = (TextView) findViewById(R.id.info);
        priceText.setText(product.getInfo());
        setImage();
        initRadioButtons();
        initCheckBoxes();
        displayQuantity();
        displayTotalText();
        displayTotalSum();
    }

    private void initRadioButtons(){
        RadioButton[] radioButtons = {
                (RadioButton) findViewById(R.id.radio1),
                (RadioButton) findViewById(R.id.radio2),
                (RadioButton) findViewById(R.id.radio3),
        };

        for(int i=0; i<radioButtons.length; i++){
            if(i<=product.getParams().length-1)
                radioButtons[i].setText(product.getParams()[i]);
            else
                radioButtons[i].setVisibility(View.INVISIBLE);
        }
        radioButtons[0].setChecked(true);
        paramSelected = product.getParams()[0];
        price = product.getParamPrices()[0];
    }

    private void initCheckBoxes(){
        CheckBox checkBox1 = findViewById(R.id.checkbox1);
        CheckBox checkBox2 = findViewById(R.id.checkbox2);
        checkBox1.setText(product.getBonus1Name() + "(" + product.getBonus1Price() + "$)");
        checkBox2.setText(product.getBonus2Name() + "(" + product.getBonus2Price() + "$)");
    }

    public void changeQuantity(View view){
        if(view.getId() == R.id.minusButton){
            if(quantity>1)
                quantity--;
            else return;
        }
        if(view.getId() == R.id.plusButton){
            quantity++;
        }
        displayQuantity();
        displayTotalText();
        displayTotalSum();
    }

    public void onRadioClicked(View view){
        if(view.getId() == R.id.radio1) {
            paramSelected = product.getParams()[0];
            price = product.getParamPrices()[0];
        }
        if(view.getId() == R.id.radio2) {
            paramSelected = product.getParams()[1];
            price = product.getParamPrices()[1];
        }
        if(view.getId() == R.id.radio3) {
            paramSelected = product.getParams()[2];
            price = product.getParamPrices()[2];
        }
        displayTotalText();
        displayTotalSum();
    }

    public void onCheckboxClicked(View view) {
        CheckBox checkBox = (CheckBox) view;
        if(checkBox.isChecked()){
            if(view.getId()==R.id.checkbox1){
                bonus1IsChecked = true;
            }
            else
                bonus2IsChecked = true;
        }
        else
        {
            if(view.getId()==R.id.checkbox1){
                bonus1IsChecked = false;
            }
            else
                bonus2IsChecked = false;
        }
        displayTotalText();
        displayTotalSum();
    }

    private void displayQuantity(){
        TextView quantityText = (TextView) findViewById(R.id.textQuantity);
        quantityText.setText("" + quantity);
    }

    private void displayTotalText(){
        TextView totalText = (TextView) findViewById(R.id.totalText);
        String text = product.getName() + " x" + quantity + ", " + paramSelected;
        if(bonus1IsChecked)
            text+= ", " + product.getBonus1Name();
        if(bonus2IsChecked)
            text+= ", " + product.getBonus2Name();
        totalText.setText(text);
        orderInfo = text;
    }

    private void displayTotalSum(){
        totalSum = price*quantity;
        if(bonus1IsChecked)
            totalSum+= product.getBonus1Price()*quantity;
        if(bonus2IsChecked)
            totalSum+= product.getBonus2Price()*quantity;
        TextView totalSumText = (TextView) findViewById(R.id.totalSum);
        totalSumText.setText(BasketActivity.doubleToString(totalSum));
    }

    public void onAddClick(View view){
        MainActivity.getOrders().add(new Order(product.getImageID(),orderInfo, totalSum));
        Toast.makeText(this, "Your order was added in basket", Toast.LENGTH_LONG).show();
        finish();
    }
}
