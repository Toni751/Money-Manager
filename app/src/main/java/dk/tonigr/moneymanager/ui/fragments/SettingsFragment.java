package dk.tonigr.moneymanager.ui.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.Calendar;

import dk.tonigr.moneymanager.R;
import dk.tonigr.moneymanager.util.AlertReceiver;

public class SettingsFragment extends PreferenceFragmentCompat {
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this.requireContext());

        EditTextPreference notificationsTime = findPreference("notifications_time");
        notificationsTime.setOnPreferenceClickListener(p -> {
            TimePickerDialog timeDialog = new TimePickerDialog(getContext(), (timePicker, hour, minute) -> {
                notificationsTime.setText(convertDayMonthToString(hour) + ":" + convertDayMonthToString(minute));
            }, Integer.parseInt(notificationsTime.getText().split(":")[0]), Integer.parseInt(notificationsTime.getText().split(":")[1]), true);
            timeDialog.show();
            return false;
        });

        sharedPreferences.registerOnSharedPreferenceChangeListener((sp, key) -> {
           if(key.equals("notifications")){
                boolean isShowNotifications = sp.getBoolean(key, false);
                Log.i("pref", "Changed " + isShowNotifications);
                if(isShowNotifications){
                    int hour = Integer.parseInt(sp.getString("notifications_time", "").split(":")[0]);
                    int minute = Integer.parseInt(sp.getString("notifications_time", "").split(":")[1]);
                    startAlarm(hour, minute);
                    Log.i("pref", "Started alarm at " + hour + " " + minute);
                }
                else {
                    setUp();
                    Log.i("pref", "Canceled alarm");
                    alarmManager.cancel(pendingIntent);
                }
            }
           else if(key.equals("notifications_time")){
               if(sp.getBoolean("notifications", false)){
                   int hour = Integer.parseInt(sp.getString("notifications_time", "").split(":")[0]);
                   int minute = Integer.parseInt(sp.getString("notifications_time", "").split(":")[1]);
                   startAlarm(hour, minute);
                   Log.i("pref", "Started alarm at " + hour + " " + minute);
               }
           }
        });
    }

    private void startAlarm(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        setUp();
        Log.i("notification-thing", String.valueOf(calendar.getTimeInMillis()));
        Log.i("notification-thing", "started alarm");
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void setUp(){
        alarmManager = (AlarmManager) this.requireContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private String convertDayMonthToString(int value) {
        return value < 10 ? "0" + value : "" + value;
    }
}