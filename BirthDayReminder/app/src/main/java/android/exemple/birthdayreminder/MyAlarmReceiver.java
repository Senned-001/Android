package android.exemple.birthdayreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.util.Map;

public class MyAlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Action action = new Action(context);
        action.start();
    }

    private class Action extends Thread {
         private Context context;
         private final int NOTIFY_ID = 101;
         private String CHANNEL_ID = "Message channel";

         public Action(Context context) {
             this.context = context;
         }

         @Override
         public void run() {
             /*DatabaseAdapter adapter = new DatabaseAdapter(context.getApplicationContext());
             Map<String, String> annonceText = adapter.getAnnonce();
             if (annonceText != null) {
                 String message = "";
                 if (annonceText.containsKey("today")) {
                     message += "Today is BD of" + annonceText.get("today") + "\n";
                 }
                 if (annonceText.containsKey("tomorrow")) {
                     message += "Tomorrow is BD of" + annonceText.get("tomorrow") + "\n";
                 }*/


                 NotificationCompat.Builder builder =
                         new NotificationCompat.Builder(context, CHANNEL_ID)
                                 .setContentTitle("BirthDayReminder")
                                 .setSmallIcon(R.drawable.logo)
                                 .setContentText("message")
                                 .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                 NotificationManagerCompat notificationManager =
                         NotificationManagerCompat.from(context);
                 notificationManager.notify(NOTIFY_ID, builder.build());
             Log.d("Info", "Not is done");

         }
     }
}
