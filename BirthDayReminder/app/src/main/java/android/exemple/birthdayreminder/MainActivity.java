package android.exemple.birthdayreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();
        List<User> nearestUsers = adapter.getNearestDateUsers(adapter.getUsers(),3);
        if(nearestUsers!=null) {
            TextView note1 = (TextView) findViewById(R.id.notification1);
            note1.setPadding(0,0,0,0);
            note1.setVisibility(View.INVISIBLE);
            ArrayAdapter<User> arrayAdapter = new UserAdapter(this, R.layout.list_layout, nearestUsers);
            ListView listView = (ListView) findViewById(R.id.list_main);
            listView.setAdapter(arrayAdapter);
        }
        adapter.close();

    }

    public void add(View view){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0,1,0,"Посмотреть всех");
        menu.add(0,2,1,"Выход");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==1) {
            Intent intent = new Intent(this, AllDataActivity.class);
            startActivity(intent);
        }
        if(id==2)
            finish();
        return super.onOptionsItemSelected(item);
    }
}