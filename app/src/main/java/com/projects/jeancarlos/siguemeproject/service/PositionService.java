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
    public static final String SHARE_PREF_KEY_ROUTE_NULL = "RouteNameNull";

    private String RouteName;

    private Location location_old;
    private Location location_curr;
    static double delta_longitude;
    static double delta_latitude;


    private static final int MIN_TIME_FOR_DISTANCE = 1000 * 5;
    private static final long MIN_DISTANCE = 5;
    private static final long MIN_TIME_UPDATE = 1000 * 30;
    private LocationManager locationManager;

    private static final int GET_LOCATION_TIME = 3000;
    private static final int SAMPLE_TIME = 10000;

    private static boolean status;

    private Handler getLocatioHandler;
    private Handler sampleHandler;
    private Runnable endServiceRunnable;
    private Runnable sampleRunnable;

    @Override
    public void onCreate() {
        super.onCreate();

        RouteName = getSharedPreferences(SHARE_PREF_NAME_POSITION_SERVICE, MODE_PRIVATE).getString(SHARE_PREF_KEY_ROUTE_NAME, SHARE_PREF_KEY_ROUTE_NULL);
        getSharedPreferences(SHARE_PREF_NAME_POSITION_SERVICE, MODE_PRIVATE)
                .edit().putString(SHARE_PREF_KEY_SERV_STATUS, SHARE_PREF_KEY_SERV_IN_PROGRESS)
                .commit();

        Log.e("PositionServ", "onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("PositionServ", "onStartCommand");
        init_location = 0;
        startListenerLocation();

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
        stopListenerLocation();

        getSharedPreferences(SHARE_PREF_NAME_POSITION_SERVICE, MODE_PRIVATE)
                .edit().putString(SHARE_PREF_KEY_SERV_STATUS, SHARE_PREF_KEY_SERV_STOPPED)
                .commit();

        getLocatioHandler.removeCallbacks(endServiceRunnable);
        sampleHandler.removeCallbacks(sampleRunnable);
        Log.e("PositionServ", "onDestroy");

    }

    /**
     * Methods for LacationManager
     */

    private void startListenerLocation() {
        locationManager = null;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
    }

    private void stopListenerLocation() {
        locationManager.removeUpdates(PositionService.this);
    }

    static int init_location = 0;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    double latitude_old;
    double latitude;
    double longitude_old;
    double longitude;


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
            saveNewPosition(location_old);
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

            if ((delta_longitude >= 1E-4) && (delta_latitude >= 1E-4)) //Establecemos un delta referencial
            {
                // Guardamos la posición si es diferente a la posicion anterior
                if ((location_old.getLatitude() != location.getLatitude()) && (location_old.getLongitude() != location.getLongitude())) {
                    location_old = location;
                    location_old.setLatitude(latitude);
                    location_old.setLongitude(longitude);
                    saveNewPosition(location_old);
                }

            }
        }
    }

    private void saveNewPosition(Location location){
        String date = df.format(Calendar.getInstance().getTime());
        ContentValues contentValues = new ContentValues();

        contentValues.put(DataBaseManager.ADDRESS, "casa");
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
