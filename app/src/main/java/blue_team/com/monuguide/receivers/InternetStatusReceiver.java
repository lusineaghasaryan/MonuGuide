package blue_team.com.monuguide.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;


import blue_team.com.monuguide.activities.SettingsActivity;
import blue_team.com.monuguide.service.LocationService;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class InternetStatusReceiver extends BroadcastReceiver {
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean condition2 = sharedPreferences.getBoolean(LocationService.SERVICE_CONNECTION, false);
        boolean condtion1 = sharedPreferences.getBoolean(SettingsActivity.KEY_OF_FUNCTION, false);
        Intent serviceIntent = new Intent(context, LocationService.class);
        if (condtion1) {
            if (isConnect()) {
                if (!condition2) {
                    context.startService(serviceIntent);
                }
            } else context.stopService(serviceIntent);

        }
    }

    private boolean isConnect() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
