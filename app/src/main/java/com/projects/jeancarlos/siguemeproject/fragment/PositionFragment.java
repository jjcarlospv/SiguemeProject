package com.projects.jeancarlos.siguemeproject.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.projects.jeancarlos.siguemeproject.R;
import com.projects.jeancarlos.siguemeproject.adapter.PositionAdapter;
import com.projects.jeancarlos.siguemeproject.database.DataBaseManager;
import com.projects.jeancarlos.siguemeproject.model.Position_DTO;
import com.projects.jeancarlos.siguemeproject.provider.PositionContentProvider;
import com.projects.jeancarlos.siguemeproject.service.PositionService;

import java.util.ArrayList;

/**
 * Created by JEANCARLOS on 21/09/2015.
 */
public class PositionFragment extends Fragment {

    private MapFragment mapFragment;
    private getPositionReceiver getPositionReceiver;
    private ArrayList<Position_DTO> listPositionItems;
    private String tempLat;
    private String tempLng;

    private LatLng latLng_old;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_position, container, false);

        listPositionItems = new ArrayList<Position_DTO>();

        String tempNameRoute = getActivity().getSharedPreferences(PositionService.SHARE_PREF_NAME_POSITION_SERVICE, getActivity().MODE_PRIVATE).getString(PositionService.SHARE_PREF_KEY_ROUTE_NAME, PositionService.SHARE_PREF_KEY_ROUTE_NULL);

        final Cursor cursor = getActivity().getContentResolver().query(PositionContentProvider.URI_POSITION, null, DataBaseManager.ID_ROUTE +"=?", new String[]{tempNameRoute}, null);

        while(cursor.moveToNext()){

            listPositionItems.add(new Position_DTO(
                    "id"
                    , "Address"
                    , cursor.getString(2)
                    , cursor.getString(3)
                    , cursor.getString(4)
                    , cursor.getString(5)
                    , ""
            ));

        }



        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("PositFrag", "onActivityCreated");
        mapFragment = new MapFragment();
        //mapFragment.setMapFragmentInterface(this);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_position_place_holder, mapFragment)
                .commit();

        mapFragment.setInterfaceMapStatus(new MapFragment.InterfaceMapStatus() {
            @Override
            public void getMapStatus(int i) {
                if(i == 1){
                    Toast.makeText(getActivity(),"Maps Ready",Toast.LENGTH_SHORT).show();

                    if(listPositionItems.size() > 0) {
                        tempLat = listPositionItems.get(0).getLatitud();
                        tempLng = listPositionItems.get(0).getLongitud();
                        mapFragment.addMarker(Double.valueOf(tempLat), Double.valueOf(tempLng));


                        for (int j = 1; j < listPositionItems.size(); j++) {

                            Double oriLat = Double.valueOf(listPositionItems.get(j - 1).getLatitud());
                            Double oriLon = Double.valueOf(listPositionItems.get(j - 1).getLongitud());
                            Double desLat = Double.valueOf(listPositionItems.get(j).getLatitud());
                            Double desLon = Double.valueOf(listPositionItems.get(j).getLongitud());
                            ;

                            mapFragment.drawLine(new LatLng(oriLat, oriLon), new LatLng(desLat, desLon));
                            mapFragment.addMarker(desLat, desLon);
                            latLng_old = new LatLng(desLat, desLon);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        /** BroadcastReceiver Local Register
         *
         */
        getPositionReceiver = new getPositionReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PositionService.POSITION_ACTION);
        getActivity().registerReceiver(getPositionReceiver, intentFilter);
        Log.e("BROADCAST_FRAGM", "REGISTER");
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(getPositionReceiver);
        Log.e("BROADCAST_FRAGM", "UNREGISTER");
    }


    public class getPositionReceiver extends BroadcastReceiver {

        String broadCLatitude;
        String broadCLongitude;
        int posTime = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == PositionService.POSITION_ACTION) {

                broadCLatitude = intent.getStringExtra(PositionService.PROGRESS_LATITUDE);
                broadCLongitude = intent.getStringExtra(PositionService.PROGRESS_LONGITUDE);

                if(posTime == 0){
                    mapFragment.moveTo(Double.valueOf(broadCLatitude), Double.valueOf(broadCLongitude), true);
                    latLng_old = new LatLng(Double.valueOf(broadCLatitude),Double.valueOf(broadCLongitude));
                    posTime = 1;
                }

                mapFragment.addMarker(Double.valueOf(broadCLatitude), Double.valueOf(broadCLongitude));
                mapFragment.drawLine(latLng_old, new LatLng(Double.valueOf(broadCLatitude), Double.valueOf(broadCLongitude)));

            }
            Log.e("BROADCAST _CONTENT", "RECEIVED");
        }
    }
}
