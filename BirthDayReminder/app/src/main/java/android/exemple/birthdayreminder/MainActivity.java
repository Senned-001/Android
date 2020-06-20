package android.exemple.birthdayreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //open adapter for work with DB
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        //Output 3 nearest BD from base
        displayListofNearestBD(adapter);

        //Output BD today or tomorrow if exist
        displayTodayOrTomorrowBD(adapter);

        adapter.close();

        setAlarm();
    }

    private void displayListofNearestBD (DatabaseAdapter adapter){
        List<User> nearestUsers = adapter.getNearestDateUsers(adapter.getUsers(),3);
        if(nearestUsers!=null) {
            //hide notification on mainActivity
            TextView note1 = (TextView) findViewById(R.id.notification1);
            note1.setPadding(0,0,0,0);
            note1.setVisibility(View.INVISIBLE);

            ArrayAdapter<User> arrayAdapter = new UserAdapter(this, R.layout.list_layout, nearestUsers);
            ListView listView = (ListView) findViewById(R.id.list_main);
            listView.setAdapter(arrayAdapter);
        }
    }

    private void displayTodayOrTomorrowBD(DatabaseAdapter adapter) {
        Map<String, String> annonceText = adapter.getAnnonce();
        TextView annonceTodayTitle = (TextView) findViewById(R.id.annonceTodayTitle);
        TextView annonceToday = (TextView) findViewById(R.id.annonceToday);
        TextView annonceTomorrowTitle = (TextView) findViewById(R.id.annonceTomorrowTitle);
        TextView annonceTomorrow = (TextView) findViewById(R.id.annonceTomorrow);
        if(annonceText!=null) {
            if (annonceText.containsKey("today")) {
                annonceTodayTitle.setText("Today celebrate their Birthdays:");
                annonceToday.setText(annonceText.get("today"));
            } else {
                annonceTodayTitle.setVisibility(View.INVISIBLE);
                annonceToday.setVisibility(View.INVISIBLE);
            }
            if (annonceText.containsKey("tomorrow")) {
                annonceTomorrowTitle.setText("Tomorrow celebrate their Birthdays:");
                annonceTomorrow.setText(annonceText.get("tomorrow"));
            } else {
                annonceTomorrowTitle.setVisibility(View.INVISIBLE);
                annonceTomorrow.setVisibility(View.INVISIBLE);
            }
        }
        else{
            annonceTodayTitle.setVisibility(View.INVISIBLE);
            annonceToday.setVisibility(View.INVISIBLE);
            annonceTomorrowTitle.setVisibility(View.INVISIBLE);
            annonceTomorrow.setVisibility(View.INVISIBLE);
        }
    }

    public void add(View view){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    private void setAlarm(){
        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(System.currentTimeMillis());
        time.add(Calendar.SECOND, 30);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
    }

    //Create menu
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