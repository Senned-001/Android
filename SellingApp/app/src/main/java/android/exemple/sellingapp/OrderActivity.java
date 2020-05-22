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

public class OrderActivity extends AppCompatActivity {
    private int quantity = 1;
    private int totalSum = 0;
    private String paramSelected = "";
    private String userName = "";
    private String userPhone = "";
    private String orderInfo = "";
    private boolean bonus1IsChecked = false;
    private boolean bonus2IsChecked = false;
    private String resultOfOrder = "Error: please check your internet connection and try again";

    private String name = "Product1";
    private String info = "Product1 Info";
    private String[] params = {"Red", "White", "Black"};
    private int price = 0;
    private int[] paramPrices = new int[3];
    private int bonus1Price = 5;
    private int bonus2Price = 10;
    private String bonus1Name = "bonus(5$)";
    private String bonus2Name = "bonus(10$)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intentOrder = getIntent();
        int id = Integer.parseInt(intentOrder.getStringExtra("ID"));
        String resources = getResources().getStringArray(R.array.str)[id];
        setImage("img" + id);
        initGlobalValues(resources);
        initialize();
    }

    private void initGlobalValues(String resources){
        /*
        Load resources as string, using id from MainActivity with format:
        name:
        info:
        number of options:
        options#1:
        options#2(if exist):
        options#3(if exist):
        cost with options#1:
        cost with options#2(if exist):
        cost with options#3(if exist):
        name of additional bonus#1:
        cost of bonus#1:
        name of additional bonus#2:
        cost of bonus#2
        */

        String[] data = resources.split(":");
        name = data[0];
        info = data[1];
        try {
            params = new String[Integer.parseInt(data[2])];
            paramPrices = new int[Integer.parseInt(data[2])];
            for(int i = 0; i < params.length; i++){
                params[i] = data[3+i];
                paramPrices[i] = Integer.parseInt(data[3+params.length+i]);
            }
            bonus1Name = data[data.length - 4];
            bonus1Price = Integer.parseInt(data[data.length - 3]);
            bonus2Name = data[data.length - 2];
            bonus2Price = Integer.parseInt(data[data.length - 1]);
        } catch (NumberFormatException e) {
            //if something with parsing goes wrong - go to main form
            finish();
        }
    }

    private void setImage(String sourceName){
        ImageView img = (ImageView) findViewById(R.id.imgOrder);
        img.setImageResource(getResources().getIdentifier(sourceName, "drawable", "android.exemple.sellingapp"));
    }

    private void initialize(){
        TextView nameText = (TextView) findViewById(R.id.name);
        nameText.setText(name);
        TextView priceText = (TextView) findViewById(R.id.info);
        priceText.setText(info);
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
            if(i<=params.length-1)
                radioButtons[i].setText(params[i]);
            else
                radioButtons[i].setVisibility(View.INVISIBLE);
        }
        radioButtons[0].setChecked(true);
        paramSelected = params[0];
        price = paramPrices[0];
    }

    private void initCheckBoxes(){
        CheckBox checkBox1 = findViewById(R.id.checkbox1);
        CheckBox checkBox2 = findViewById(R.id.checkbox2);
        checkBox1.setText(bonus1Name);
        checkBox2.setText(bonus2Name);
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
            paramSelected = params[0];
            price = paramPrices[0];
        }
        if(view.getId() == R.id.radio2) {
            paramSelected = params[1];
            price = paramPrices[1];
        }
        if(view.getId() == R.id.radio3) {
            paramSelected = params[2];
            price = paramPrices[2];
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
        String text = "Your order: " + name + " - " + quantity + " p." + ", " + paramSelected;
        if(bonus1IsChecked)
            text+= ", " + bonus1Name;
        if(bonus2IsChecked)
            text+= ", " + bonus2Name;
        text+= "\nYour info: " + userName + ", " + userPhone;
        totalText.setText(text);
        orderInfo = text.replaceAll("Your", "User");
    }

    private void displayTotalSum(){
        totalSum = price*quantity;
        if(bonus1IsChecked)
            totalSum+= bonus1Price*quantity;
        if(bonus2IsChecked)
            totalSum+= bonus2Price*quantity;
        TextView totalSumText = (TextView) findViewById(R.id.totalSum);
        totalSumText.setText(totalSum +" $");
    }

    public void orderClick(View view){
        final ClientConnection newClientConnection = new ClientConnection();
        /* Open new connection in new thread */
        Thread connectionTread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    newClientConnection.openConnection();
                    newClientConnection.sendData(orderInfo.getBytes());
                    newClientConnection.closeConnection();
                } catch (Exception e) {
                    resultOfOrder = "Success Order, our manager will contact to you";
                }
            }
        });
        connectionTread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        displayResultOfOrder();
        if(!resultOfOrder.contains("Error"))
            finish();
    }

    private void displayResultOfOrder(){
        Toast toast = Toast.makeText(this, resultOfOrder, Toast.LENGTH_LONG);
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
