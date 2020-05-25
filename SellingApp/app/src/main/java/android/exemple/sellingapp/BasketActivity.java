package android.exemple.sellingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BasketActivity extends AppCompatActivity {
    private double totalSum;
    private String userName = "";
    private String userPhone = "";
    private String resultOfSendingOrder = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        setListenersOnEdits();
        displayTotalSum();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.basketRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BasketDataAdapter adapter = new BasketDataAdapter(this, MainActivity.getOrders());
        recyclerView.setAdapter(adapter);
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
            }
        });
    }

    public void onOrderClick (View view){
        if(MainActivity.getOrders().isEmpty()){
            displayToast("The Basket is empty");
            return;
        }
        if(userName.isEmpty()||userPhone.isEmpty()||userPhone.matches("\\D")){
            displayToast("Please enter correct Name and Phone");
            return;
        }

        view.setEnabled(false);
        final String totalResultOrder = getResultOrderString();

        resultOfSendingOrder = "Error: please check your internet connection and try again";
        final ClientConnection newClientConnection = new ClientConnection();
        // Open new connection in new thread
        Thread connectionTread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    newClientConnection.openConnection();
                    newClientConnection.sendData(totalResultOrder.getBytes());
                    newClientConnection.closeConnection();
                    resultOfSendingOrder = "Success Order, our manager will contact to you";
                } catch (Exception e) {
                }
            }
        });
        connectionTread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        displayToast(resultOfSendingOrder);
        if(!resultOfSendingOrder.contains("Error")) {
            MainActivity.getOrders().clear();
            finish();
        }

        view.setEnabled(true);
    }

    private String getResultOrderString(){
        String totalInfo = "USER ORDER:\n";
        for(Order order : MainActivity.getOrders()){
            totalInfo+=order.toString()+"\n";
        }
        totalInfo+="USER NAME: " + userName + "\n";
        totalInfo+="USER PHONE: " + userPhone + "\n";

        Date date = new Date();
        SimpleDateFormat fr = new SimpleDateFormat("dd.MM.YYYY HH:mm");
        String time = fr.format(date);
        totalInfo+="DATE: " + time;

        totalInfo+="\nTOTAL SUM: " + doubleToString(totalSum);
        return totalInfo;
    }

    private void displayToast(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    public void displayTotalSum(){
        TextView totalSumText = findViewById(R.id.totalSumInBasket);
        totalSum = 0;
        for(Order order: MainActivity.getOrders()){
            totalSum+=order.getTotalCost();
        }
        totalSumText.setText("TOTAL SUM: " + doubleToString(totalSum));
    }

    public static String doubleToString(double sum){
        return String.format("%.2f$", sum);
    }
}
