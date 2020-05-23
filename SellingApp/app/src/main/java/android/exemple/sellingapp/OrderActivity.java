package android.exemple.sellingapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderActivity extends AppCompatActivity {
    private Product product;
    private int quantity = 1;
    private int totalSum = 0;
    private String paramSelected = "";
    private String userName = "";
    private String userPhone = "";
    private String orderInfo = "";
    private boolean bonus1IsChecked = false;
    private boolean bonus2IsChecked = false;
    private String resultOfOrder = "Error: please check your internet connection and try again";
    private int price = 0;

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
        setListenersOnEdits();
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
        checkBox1.setText(product.getBonus1Name());
        checkBox2.setText(product.getBonus2Name());
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
        String text = "Your order: " + product.getName() + " - " + quantity + " p." + ", " + paramSelected;
        if(bonus1IsChecked)
            text+= ", " + product.getBonus1Name();
        if(bonus2IsChecked)
            text+= ", " + product.getBonus2Name();
        text+= "\nYour info: " + userName + ", " + userPhone;
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
        totalSumText.setText(totalSum +" $");
    }

    public void orderClick(View view){
        if(userName.isEmpty()||userPhone.isEmpty()||userPhone.matches("\\D")){
            displayToast("Please enter correct Name and Phone");
            return;
        }
        view.setEnabled(false);

        Date date = new Date();
        SimpleDateFormat fr = new SimpleDateFormat("dd.MM.YYYY HH:mm");
        String time = fr.format(date);
        orderInfo+="\nDate: " + time;
        orderInfo+="\nTotal Sum: " + totalSum;
        orderInfo = orderInfo.replaceAll("Your", "User");

        final ClientConnection newClientConnection = new ClientConnection();
        /* Open new connection in new thread */
        Thread connectionTread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    newClientConnection.openConnection();
                    newClientConnection.sendData(orderInfo.getBytes());
                    newClientConnection.closeConnection();
                    resultOfOrder = "Success Order, our manager will contact to you";
                } catch (Exception e) {
                }
            }
        });
        connectionTread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        displayToast(resultOfOrder);
        if(!resultOfOrder.contains("Error"))
            finish();
        view.setEnabled(true);
    }

    private void displayToast(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    private void setListenersOnEdits(){
        EditText userNameText = (EditText) findViewById(R.id.userName);
        userNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                userName = s.toString();
                displayTotalText();
            }
        });

        EditText userNamePhone = (EditText) findViewById(R.id.userPhone);
        userNamePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                userPhone = s.toString();
                displayTotalText();
            }
        });
    }
}
