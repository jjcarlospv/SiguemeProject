package com.projects.jeancarlos.siguemeproject.fragment;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projects.jeancarlos.siguemeproject.R;

/**
 * Created by JEANCARLOS on 21/09/2015.
 */
public class MapFragment extends com.google.android.gms.maps.MapFragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationChangeListener {

    private GoogleMap googleMap;
    private MapFragmentInterface mapFragmentInterface;
    private static boolean currPos = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationChangeListener(this);
    }

    /*
        Si este método devuelve "false" entonces automáticamente cuando se obtenga la ubicación
        la cámara se desplazará a ese punto. Si devuelve "true" entonces no se desplazará automáticamente.
         */
    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(getActivity(), R.string.getting_location, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (mapFragmentInterface != null) {

            mapFragmentInterface.onLocationChange(location);
        }
    }

    /*
    En este laboratorio no vamos a utilizar este método, sin embargo
    sirve para mover el mapa hacia unas coordenadas deseadas.
     */
    public void moveTo(final double latitude, final double longitude, final boolean animate) {
        final LatLng latLng = new LatLng(latitude, longitude);
        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(13)
                .build();
        final CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        if (animate) {
            googleMap.animateCamera(cameraUpdate);
        } else {
            googleMap.moveCamera(cameraUpdate);
        }
    }

    public Marker addMarker(double lat, double lng){

        LatLng latLng1 = new LatLng(lat,lng);

        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(latLng1)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
        return marker;
    }

    public void setMapFragmentInterface(MapFragmentInterface mapFragmentInterface) {
        this.mapFragmentInterface = mapFragmentInterface;
    }

    public static interface MapFragmentInterface {

        public void onLocationChange(Location location);

    }
}
