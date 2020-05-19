package android.exemple.sellingapp;

import androidx.appcompat.app.AppCompatActivity;

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
    private int quantity = 1;
    private int totalSum = 0;
    private String color = "";
    private String userName = "";
    private String userPhone = "";
    private String orderInfo = "";
    private boolean bonus1IsChecked = false;
    private boolean bonus2IsChecked = false;
    private String resultOfOrder = "Success Order, our manager will contact to you";

    private int price = 20;
    private int bonus1 = 5;
    private int bonus2 = 10;
    private String name = "Product1";
    private String[] colors = {"Red", "White", "Black"};
    private String checkBox1Name = "bonus (5$)";
    private String checkBox2Name = "bonus (10$)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize(){
        TextView nameText = (TextView) findViewById(R.id.name);
        nameText.setText(name);
        TextView priceText = (TextView) findViewById(R.id.price);
        priceText.setText(price + " $");
        initRadioButtons();
        initCheckBoxes();
        setListenersOnEdits();
        displayQuantity();
        displayTotalText();
        displayTotalSum();
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
            if(view.getId() == R.id.color1)
                color = colors[0];
            if(view.getId() == R.id.color2)
                color = colors[1];
            if(view.getId() == R.id.color3)
                color = colors[2];
        displayTotalText();
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
        String text = "Your order: " + name + " - " + quantity + " p." + ", " + color;
        if(bonus1IsChecked)
            text+= ", " + checkBox1Name;
        if(bonus2IsChecked)
            text+= ", " + checkBox2Name;
        text+= "\nYour info: " + userName + ", " + userPhone;
        totalText.setText(text);
        orderInfo = text.replaceAll("Your", "User");
    }

    private void displayTotalSum(){
        totalSum = price*quantity;
        if(bonus1IsChecked)
            totalSum+=bonus1;
        if(bonus2IsChecked)
            totalSum+=bonus2;
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
                    resultOfOrder = "Error: please check your internet connection and try again";
                }
            }
        });
        connectionTread.start();
        displayResultOfOrder();
    }

    private void displayResultOfOrder(){
        Toast toast = new Toast(this);
        toast.setText("DONE");
        toast.setDuration(Toast.LENGTH_LONG);
    }

    private void initRadioButtons(){
        RadioButton[] radioButtons = {
                (RadioButton) findViewById(R.id.color1),
                (RadioButton) findViewById(R.id.color2),
                (RadioButton) findViewById(R.id.color3),
        };

        for(int i=0; i<radioButtons.length; i++){
            if(i<=colors.length-1)
                radioButtons[i].setText(colors[i]);
            else
                radioButtons[i].setVisibility(View.INVISIBLE);
        }
        radioButtons[0].setChecked(true);
        color = colors[0];
    }

    private void initCheckBoxes(){
        CheckBox checkBox1 = findViewById(R.id.checkbox1);
        CheckBox checkBox2 = findViewById(R.id.checkbox2);
        checkBox1.setText(checkBox1Name);
        checkBox2.setText(checkBox2Name);
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
