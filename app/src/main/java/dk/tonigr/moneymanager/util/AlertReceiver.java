package dk.tonigr.moneymanager.util;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.ui.activities.SignInActivity;

public class AlertReceiver extends BroadcastReceiver {
    private final String CHANNEL_ID = "misc";
    @Override
    public void onReceive(Context context, Intent received_intent) {
        Log.i("notification-thing", "started building notification");
        Intent intent = new Intent(context, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_monetization_on_24)
                .setContentTitle("Money manager")
                .setContentText("Don't forget to register today's transactions!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        Log.i("notification-thing", "done building notification");
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(100, builder.build());
        Log.i("notification-thing", "showed notification");
    }
}
