package com.example.souborno.track;

/**
 * Created by Souborno on 3/28/2017.
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;


/**
 * Created by Souborno on 2/17/2017.
 */

public class GPSS extends Service {
    private LocationListener locationListener;
    private LocationManager locationManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationListener=new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                Intent intent=new Intent("location_update");
                intent.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());
                sendBroadcast(intent);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent=new Intent(Settings.ACTION_LOCALE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);





            }

        };
        locationManager=(LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        //noinspection MissingPermission
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,3000,0,locationListener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationListener!=null)
        {
            //noinspection MissingPermission
            locationManager.removeUpdates(locationListener);
        }
    }
}