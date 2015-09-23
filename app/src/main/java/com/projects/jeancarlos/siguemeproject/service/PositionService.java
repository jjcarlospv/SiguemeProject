package com.projects.jeancarlos.siguemeproject.service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.projects.jeancarlos.siguemeproject.database.DataBaseManager;
import com.projects.jeancarlos.siguemeproject.provider.PositionContentProvider;
import com.projects.jeancarlos.siguemeproject.util.DateUtil;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by JEANCARLOS on 23/08/2015.
 */
public class PositionService extends Service implements LocationListener {

    private final String POSITION_URI = "content://jeancarlos.projects.com.sigueme.PositionContentProvider";

    public static final String POSITION_ACTION = "com.example.jean.POSITION";
    public static final String POSITION_FIN_ACTION = "com.example.jean.POSITION_FIN";
    public static final String PROGRESS_POSITION = "progressPosition";
    public static final String PROGRESS_POSITION_FIN = "progressPositionFinal";
    public static final String PROGRESS_LONGITUDE = "longitude";
    public static final String PROGRESS_LATITUDE = "latitude";
    public static final String PROGRESS_DATE = "date";
    public static final String PROGRESS_HOUR = "hour";
    public static final String PROGRESS_ROUTE = "route";

    /* Variables de estado del servicio */
    public static final String SHARE_PREF_NAME_POSITION_SERVICE = "com.projects.jeancarlos.sigueme.LOCATION";
    public static final String SHARE_PREF_KEY_SERV_STATUS = "status";
    public static final String SHARE_PREF_KEY_SERV_STOPPED = "Stopped";
    public static final String SHARE_PREF_KEY_SERV_IN_PROGRESS = "inProgress";
    public static final String SHARE_PREF_KEY_ROUTE_NAME = "RouteName";

    private String RouteName;

    private Location location_old;
    private Location location_curr;
    static double delta_longitude;
    static double delta_latitude;


    private static final int MIN_TIME_FOR_DISTANCE = 1000 * 5;
    private static final long MIN_DISTANCE = 5;
    private static final long MIN_TIME_UPDATE = 1000 * 30;
    private LocationManager locationManager;

    private static final int GET_LOCATION_TIME = 2000;
    private static final int SAMPLE_TIME = 5000;

    private static boolean status;

    private Handler getLocatioHandler;
    private Handler sampleHandler;
    private Runnable endServiceRunnable;
    private Runnable sampleRunnable;

    @Override
    public void onCreate() {
        super.onCreate();


        RouteName = getSharedPreferences(SHARE_PREF_NAME_POSITION_SERVICE, MODE_PRIVATE).getString(SHARE_PREF_KEY_ROUTE_NAME, null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("onStartCommand", "");
        init_location = 0;
        startListenerLocation();

        getSharedPreferences(SHARE_PREF_NAME_POSITION_SERVICE, MODE_PRIVATE)
                .edit().putString(SHARE_PREF_KEY_SERV_STATUS, SHARE_PREF_KEY_SERV_IN_PROGRESS)
                .commit();


        getLocatioHandler = new Handler();
        endServiceRunnable = new Runnable() {
            @Override
            public void run() {
                stopListenerLocation();
                //getLocatioHandler.removeCallbacks(endServiceRunnable);
                Log.e("GetLoc", "End");
                sampleHandler.postDelayed(sampleRunnable, SAMPLE_TIME);
            }
        };

        sampleHandler = new Handler();
        sampleRunnable = new Runnable() {
            @Override
            public void run() {
                    startListenerLocation();
                    //sampleHandler.removeCallbacks(sampleRunnable);
                    Log.e("SampleLoc", "End");
                    getLocatioHandler.postDelayed(endServiceRunnable, GET_LOCATION_TIME);
            }
        };

        getLocatioHandler.postDelayed(endServiceRunnable, GET_LOCATION_TIME);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        init_location = 0;


        getSharedPreferences(SHARE_PREF_NAME_POSITION_SERVICE, MODE_PRIVATE)
                .edit().putString(SHARE_PREF_KEY_SERV_STATUS, SHARE_PREF_KEY_SERV_STOPPED)
                .commit();

        getLocatioHandler.removeCallbacks(endServiceRunnable);
        sampleHandler.removeCallbacks(sampleRunnable);

        Log.e("POSITION_SERVICE", "Stopped");
    }

    /**
     * Methods for LacationManager
     */

    private void startListenerLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
    }

    private void stopListenerLocation() {
        locationManager.removeUpdates(PositionService.this);
        if(locationManager != null)
        {locationManager = null;}
    }

    /*private boolean isBetterPosition(Location location, Location lastBestPosition) {
        if (lastBestPosition == null) {
            // A new location is always better than no location
            return true;
        }


        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - lastBestPosition.getTime();
        boolean isSignificantlyNewer = timeDelta > MIN_TIME_FOR_DISTANCE;
        boolean isSignificantlyOlder = timeDelta < -MIN_TIME_FOR_DISTANCE;
        boolean isNewer = timeDelta > 0;

        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - lastBestPosition.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                lastBestPosition.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }
*/


    /**
     * LocationListener Methods
     *
     * @param location
     */

    static int init_location = 0;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    DecimalFormat decform = new DecimalFormat("##.####");

    double latitude_old;
    double latitude;
    double longitude_old;
    double longitude;

    static double temp_lat = 0;
    static double temp_lng = 0;
    static int count_loc = 0;

    @Override
    public void onLocationChanged(Location location) {

        // Aplicamos la reducción de decimales


        if (init_location == 0) { // Iniciamos la variable de referencia
            location_old = location;
            latitude_old = Math.round(location.getLatitude() * 10000) / 10000.0;
            longitude_old = Math.round(location.getLongitude() * 10000) / 10000.0;

            location_old.setLatitude(latitude_old);
            location_old.setLongitude(longitude_old);

            init_location = 1;
            savePosition(location_old);
        } else {

            latitude = Math.round(location.getLatitude() * 10000) / 10000.0;
            longitude = Math.round(location.getLongitude() * 10000) / 10000.0;

            delta_latitude = latitude - latitude_old;
            delta_longitude = longitude - longitude_old;

            if (delta_longitude < 0) {
                delta_longitude = delta_longitude * -1; //Hacemos positivo la diferencia
            }
            if (delta_latitude < 0) {
                delta_latitude = delta_latitude * -1;
            }

            if ((delta_longitude > 1.3E-4) || (delta_latitude > 1.3E-4)) //Establecemos un delta referencial
            {
                location_old = location;
                location_old.setLatitude(latitude);
                location_old.setLongitude(longitude);
                savePosition(location_old);
            }
        }
    }


    private void savePosition(Location location) {

        String date = df.format(Calendar.getInstance().getTime());
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseManager.ADDRESS, "");
        contentValues.put(DataBaseManager.LATITUD, String.valueOf(location.getLatitude()));
        contentValues.put(DataBaseManager.LONGITUD, String.valueOf(location.getLongitude()));
        contentValues.put(DataBaseManager.DATE, String.valueOf(DateUtil.getDate(date)));
        contentValues.put(DataBaseManager.HOUR, String.valueOf(DateUtil.getHour(date)));
        contentValues.put(DataBaseManager.ID_ROUTE, RouteName);
        Uri uri = getContentResolver().insert(PositionContentProvider.URI_POSITION, contentValues);

        Intent intentTestService = new Intent(POSITION_ACTION);
        intentTestService.putExtra(PROGRESS_LATITUDE, String.valueOf(location.getLatitude()));
        intentTestService.putExtra(PROGRESS_LONGITUDE, String.valueOf(location.getLongitude()));
        intentTestService.putExtra(PROGRESS_DATE, String.valueOf(DateUtil.getDate(date)));
        intentTestService.putExtra(PROGRESS_HOUR, String.valueOf(DateUtil.getHour(date)));
        intentTestService.putExtra(PROGRESS_ROUTE, RouteName);
        intentTestService.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intentTestService);
        Log.e("SAVE_POSITION", String.valueOf(location.getLatitude()) + "//" + String.valueOf(location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
