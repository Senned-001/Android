package android.exemple.birthdayreminder;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class UserActivity extends AppCompatActivity {
    EditText nameBox;
    EditText dayBox;
    EditText monthBox;
    EditText yearBox;
    EditText ageBox;
    Button delButton;
    Button saveButton;
    TextView orText;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("d.M.yyyy");
    private static Calendar currentDate = new GregorianCalendar();
    static{
        DATE_FORMAT.setLenient(false);
    }

    private DatabaseAdapter adapter;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        nameBox = (EditText) findViewById(R.id.name);
        dayBox = (EditText) findViewById(R.id.day);
        monthBox = (EditText) findViewById(R.id.month);
        yearBox = (EditText) findViewById(R.id.year);
        ageBox = (EditText) findViewById(R.id.age);
        delButton = (Button) findViewById(R.id.deleteButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        orText = (TextView) findViewById(R.id.or);

        adapter = new DatabaseAdapter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        //если не 0 то редактирование
        if (userId > 0) {
            yearBox.setVisibility(View.INVISIBLE);
            orText.setVisibility(View.INVISIBLE);
            // получаем элемент по id из бд
            adapter.open();
            User user = adapter.getUser(userId);
            nameBox.setText(user.getName());
            dayBox.setText(String.valueOf(user.getDay()));
            monthBox.setText(String.valueOf(user.getMonth()));
            ageBox.setText(String.valueOf(user.getAge()));
            adapter.close();

        } else {// если 0, то добавление
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }

    public void save(View view){
        String name = nameBox.getText().toString();
        if(name.isEmpty()){
            Toast.makeText(this, "Введите корректное имя", Toast.LENGTH_LONG).show();
            return;
        }

        int day = 0;
        int month = 0;
        int age = 0;
        try {
            day = Integer.parseInt(dayBox.getText().toString());
            month = Integer.parseInt(monthBox.getText().toString());
            age = Integer.parseInt(ageBox.getText().toString());
            DATE_FORMAT.parse(day+"."+month+"."+currentDate.get(Calendar.YEAR));
        } catch (Exception e) {
            Toast.makeText(this, "Введите корректную дату", Toast.LENGTH_LONG).show();
            return;
        }
        User user = new User(userId, name, day, month, age);

        adapter.open();
        if (userId > 0) {
            adapter.update(user);
        } else {
            adapter.insert(user);
        }
        adapter.close();
        goHome();
    }

    public void delete(View view){
        adapter.open();
        adapter.delete(userId);
        adapter.close();
        goHome();
    }
    private void goHome(){
        finish();
    }
}