package com.projects.jeancarlos.siguemeproject.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;
import com.projects.jeancarlos.siguemeproject.R;
import com.projects.jeancarlos.siguemeproject.service.PositionService;

/**
 * Created by JEANCARLOS on 21/09/2015.
 */
public class PositionFragment extends Fragment {

    private MapFragment mapFragment;
    private getPositionReceiver getPositionReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_position, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mapFragment = new MapFragment();
        //mapFragment.setMapFragmentInterface(this);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_position_place_holder, mapFragment)
                .commit();
       // getChildFragmentManager().findFragmentById(R.id.fragment_position_place_holder);
        //mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getFragmentManager().beginTransaction().remove(mapFragment);
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


        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == PositionService.POSITION_ACTION) {

/*
                positionItems.add(new Position_DTO(
                        "id"
                        ,"Address"
                        ,intent.getStringExtra(PositionService.PROGRESS_LATITUDE)
                        ,intent.getStringExtra(PositionService.PROGRESS_LONGITUDE)
                        ,intent.getStringExtra(PositionService.PROGRESS_DATE)
                        ,intent.getStringExtra(PositionService.PROGRESS_HOUR)
                        ,""
                ));

                fragment_position_position.setAdapter(new PositionAdapter(getActivity(),positionItems));
                fragment_position_txt_location.setText(intent.getStringExtra(PositionService.PROGRESS_POSITION));*/

                broadCLatitude = intent.getStringExtra(PositionService.PROGRESS_LATITUDE);
                broadCLongitude = intent.getStringExtra(PositionService.PROGRESS_LONGITUDE);

                mapFragment.addMarker(Double.valueOf(broadCLatitude), Double.valueOf(broadCLongitude));

            }
            Log.e("BROADCAST _CONTENT", "RECEIVED");
        }
    }
}
