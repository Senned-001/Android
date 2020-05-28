package android.exemple.birthdayreminder;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {
    EditText nameBox;
    EditText dayBox;
    EditText monthBox;
    EditText yearBox;
    EditText ageBox;
    Button delButton;
    Button saveButton;

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

        adapter = new DatabaseAdapter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд
            adapter.open();
            User user = adapter.getUser(userId);
            nameBox.setText(user.getName());
            dayBox.setText(String.valueOf(user.getDay()));
            monthBox.setText(String.valueOf(user.getMonth()));
            yearBox.setText(String.valueOf(user.getYear()));
            ageBox.setText(String.valueOf(user.getAge()));
            adapter.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }

    public void save(View view){
        String name = nameBox.getText().toString();
        int day = Integer.parseInt(dayBox.getText().toString());
        int month = Integer.parseInt(monthBox.getText().toString());
        int year = Integer.parseInt(yearBox.getText().toString());
        int age = Integer.parseInt(ageBox.getText().toString());
        User user = new User(userId, name, day, month);

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
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}