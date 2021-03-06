package android.exemple.birthdayreminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

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
             DatabaseAdapter adapter = new DatabaseAdapter(context.getApplicationContext());
             adapter.open();
             Map<String, String> annonceText = adapter.getAnnonce();
             if (annonceText != null) {
                 String message = "";
                 if (annonceText.containsKey("today")) {
                     message += context.getString(R.string.today_message) + annonceText.get("today") + "\n";
                 }
                 if (annonceText.containsKey("tomorrow")) {
                     message += context.getString(R.string.tomorrow_message) + annonceText.get("tomorrow") + "\n";
                 }
                 adapter.close();

                 createNotification(message);

                 //Log.d("Info", message);
             }
         }

         private void createNotification(String message){
             //Create PendingIntent for open mainActivity on tap
             Intent resultIntent = new Intent(context, MainActivity.class);
             PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                     PendingIntent.FLAG_UPDATE_CURRENT);

             NotificationCompat.Builder builder =
                     new NotificationCompat.Builder(context, CHANNEL_ID)
                             .setSmallIcon(R.drawable.logo_small)
                             .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                             .setContentTitle(context.getString(R.string.app_name))
                             .setContentText(message)
                             .setTicker(message)
                             .setContentIntent(resultPendingIntent)
                             .setAutoCancel(true)
                             .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.FLAG_SHOW_LIGHTS)
                             .setPriority(NotificationCompat.PRIORITY_HIGH);

             NotificationManager notificationManager =
                     (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 CharSequence name = "BDReminder_notification";
                 String description = "Notification_chanel";
                 NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name,
                         NotificationManager.IMPORTANCE_HIGH);
                 channel.setDescription(description);
                 channel.enableLights(true);
                 channel.enableVibration(true);
                 channel.setShowBadge(true);

                 // Register the channel with the system; you can't change the importance
                 // or other notification behaviours after this
                 notificationManager =
                         context.getSystemService(NotificationManager.class);
                 notificationManager.createNotificationChannel(channel);
             }

             notificationManager.notify(NOTIFY_ID, builder.build());
         }
     }
}
