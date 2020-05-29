package android.exemple.birthdayreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DatabaseAdapter {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private static SimpleDateFormat df = new SimpleDateFormat("d.M.yyyy");
    private static Calendar currentDate = new GregorianCalendar();

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_DAY, DatabaseHelper.COLUMN_MONTH, DatabaseHelper.COLUMN_AGE};
        return  database.query(DatabaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                int day = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_DAY));
                int month = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_MONTH));
                int age = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_AGE));
                users.add(new User(id, name, day, month, age));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }

    public User getUser(long id){
        User user = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            int day = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_DAY));
            int month = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_MONTH));
            int age = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_AGE));
            user = new User(id, name, day, month, age);
        }
        cursor.close();
        return  user;
    }

    public List<User> getNearestDateUsers(List<User> users, int numberOfUsers){
        if(users.isEmpty())
            return null;
        List<User> result = new ArrayList<>();

        for(int n=0;n<numberOfUsers;n++) {
            User user = users.get(0);
            long min = Long.MAX_VALUE;

            for (int i = 0; i < users.size(); i++) {
                Date dateFromBase = null;
                try {
                    dateFromBase = df.parse(users.get(i).getDay() + "." + users.get(i).getMonth() + "." + currentDate.get(Calendar.YEAR));
                } catch (ParseException e) {
                    return null;
                }
                long s = dateFromBase.getTime() - currentDate.getTime().getTime();
                if (s > 0) {
                    if (s < min) {
                        min = s;
                        user = users.get(i);
                    }
                }
            }
            result.add(user);
            users.remove(user);
        }
        return result;
    }

    public long insert(User user){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, user.getName());
        cv.put(DatabaseHelper.COLUMN_DAY, user.getDay());
        cv.put(DatabaseHelper.COLUMN_MONTH, user.getMonth());
        cv.put(DatabaseHelper.COLUMN_AGE, user.getAge());
        return  database.insert(DatabaseHelper.TABLE, null, cv);
    }

    public long delete(long userId){
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(userId)};
        return database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }

    public long update(User user){
        String whereClause = DatabaseHelper.COLUMN_ID + "=" + String.valueOf(user.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, user.getName());
        cv.put(DatabaseHelper.COLUMN_DAY, user.getDay());
        cv.put(DatabaseHelper.COLUMN_MONTH, user.getMonth());
        cv.put(DatabaseHelper.COLUMN_AGE, user.getAge());
        return database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }
}

