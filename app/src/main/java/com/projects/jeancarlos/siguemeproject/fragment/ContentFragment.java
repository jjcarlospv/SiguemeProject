package com.projects.jeancarlos.siguemeproject.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.IntentService;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.projects.jeancarlos.siguemeproject.MainActivity;
import com.projects.jeancarlos.siguemeproject.R;
import com.projects.jeancarlos.siguemeproject.service.PositionService;

/**
 * Created by JEANCARLOS on 20/09/2015.
 */
public class ContentFragment extends Fragment {

    private String fragment_description_Type;
    private String fragment_options_Type;
    private OptionsFragment optionsFragment;
    private DescriptionMaskFragment descriptionMaskFragment;
    private PositionFragment positionFragment;
    private ListPositionFragment listPositionFragment;
    private OptionMaskFragment optionMaskFragment;
    private ListRoutesFragment listRoutesFragment;
    private CloseRoutesFragment closeRoutesFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragment_options_Type = getActivity().getSharedPreferences(MainActivity.SHARE_PREF_NAME_STATUS, Context.MODE_PRIVATE).getString(MainActivity.EXTRA_FRAGMENT_OPTIONS, MainActivity.FRAGMENT_MASK_OPTIONS);
        fragment_description_Type = getActivity().getSharedPreferences(MainActivity.SHARE_PREF_NAME_STATUS, Context.MODE_PRIVATE).getString(MainActivity.EXTRA_FRAGMENT_DESCRIPTION, MainActivity.FRAGMENT_MASK_DESCRIPTION);

        switch (fragment_description_Type) {
            case MainActivity.FRAGMENT_MASK_DESCRIPTION:
                fragmentMaskDescription();
                break;

            case MainActivity.FRAGMENT_POSITION:
                fragmentPosition();
                break;

            case MainActivity.FRAGMENT_LIST_ROUTES:
                fragmentListRoute();
                break;
        }

        switch (fragment_options_Type) {

            case MainActivity.FRAGMENT_MASK_OPTIONS:
                fragmentMaskOption();
                break;

            case MainActivity.FRAGMENT_OPTIONS:
                fragmentOptions();
                break;

            case MainActivity.FRAGMENT_CLOSE_ROUTES:
                fragmentCloseRoutes();
                break;
        }
    }

    private void fragmentMaskDescription() {
        getActivity().getSharedPreferences(MainActivity.SHARE_PREF_NAME_STATUS, Context.MODE_PRIVATE).edit().putString(MainActivity.EXTRA_FRAGMENT_DESCRIPTION, MainActivity.FRAGMENT_MASK_DESCRIPTION).commit();
        descriptionMaskFragment = new DescriptionMaskFragment();
        getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment_content_container_description, descriptionMaskFragment).commit();
    }

    private void fragmentPosition() {
        getActivity().getSharedPreferences(MainActivity.SHARE_PREF_NAME_STATUS, Context.MODE_PRIVATE).edit().putString(MainActivity.EXTRA_FRAGMENT_DESCRIPTION, MainActivity.FRAGMENT_POSITION).commit();
        positionFragment = new PositionFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_content_container_description, positionFragment).commit();
    }

    private void fragmentListRoute() {
        getActivity().getSharedPreferences(MainActivity.SHARE_PREF_NAME_STATUS, Context.MODE_PRIVATE).edit().putString(MainActivity.EXTRA_FRAGMENT_DESCRIPTION, MainActivity.FRAGMENT_LIST_ROUTES).commit();
        listRoutesFragment = new ListRoutesFragment();
        getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment_content_container_description, listRoutesFragment).commit();
    }

    private void fragmentListPosition() {
        getActivity().getSharedPreferences(MainActivity.SHARE_PREF_NAME_STATUS, Context.MODE_PRIVATE).edit().putString(MainActivity.EXTRA_FRAGMENT_DESCRIPTION, MainActivity.FRAGMENT_LIST_POSITIONS).commit();
        listPositionFragment = new ListPositionFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_content_container_description, listPositionFragment).commit();
    }

    private void fragmentMaskOption() {

        getActivity().getSharedPreferences(MainActivity.SHARE_PREF_NAME_STATUS, Context.MODE_PRIVATE).edit().putString(MainActivity.EXTRA_FRAGMENT_OPTIONS, MainActivity.FRAGMENT_MASK_OPTIONS).commit();

        optionMaskFragment = new OptionMaskFragment();
        getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment_content_container_options, optionMaskFragment).commit();
    }

    private void fragmentOptions() {

        getActivity().getSharedPreferences(MainActivity.SHARE_PREF_NAME_STATUS, Context.MODE_PRIVATE).edit().putString(MainActivity.EXTRA_FRAGMENT_OPTIONS, MainActivity.FRAGMENT_OPTIONS).commit();

        optionsFragment = new OptionsFragment();
        getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment_content_container_options, optionsFragment).commit();

        final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        optionsFragment.setInterfaceOptions(new OptionsFragment.InterfaceOptions() {
            @Override
            public void getOption(int i) {

                switch (i) {
                    case 0:
                        String RouteName = getActivity()
                                .getSharedPreferences(PositionService.SHARE_PREF_NAME_POSITION_SERVICE, getActivity().MODE_PRIVATE)
                                .getString(PositionService.SHARE_PREF_KEY_SERV_STATUS, PositionService.SHARE_PREF_KEY_SERV_STOPPED);


                        if (RouteName == PositionService.SHARE_PREF_KEY_SERV_STOPPED) {
                            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                                fragmentPosition();
                                getActivity().startService(new Intent(getActivity(), PositionService.class));

                                Toast.makeText(getActivity(), R.string.main_activity_start_service, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), R.string.main_activity_error_gps, Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                                fragmentPosition();
                                Toast.makeText(getActivity(), R.string.main_activity_continue_route, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), R.string.main_activity_error_gps, Toast.LENGTH_SHORT).show();
                            }

                        }
                        break;

                    case 1:
                        fragmentListPosition();
                        break;

                    case 2:

                        break;

                    case 3:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(getResources().getString(R.string.main_activity_exit_message))
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        getActivity().stopService(new Intent(getActivity(), PositionService.class));
                                        getActivity().getSharedPreferences(PositionService.SHARE_PREF_NAME_POSITION_SERVICE, getActivity().MODE_PRIVATE).edit().putString(PositionService.SHARE_PREF_KEY_ROUTE_NAME, PositionService.SHARE_PREF_KEY_ROUTE_NULL).commit();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        break;
                }
            }
        });
    }

    private void fragmentCloseRoutes(){

        getActivity().getSharedPreferences(MainActivity.SHARE_PREF_NAME_STATUS, Context.MODE_PRIVATE).edit().putString(MainActivity.EXTRA_FRAGMENT_OPTIONS, MainActivity.FRAGMENT_CLOSE_ROUTES).commit();

        closeRoutesFragment = new CloseRoutesFragment();
        getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment_content_container_options, closeRoutesFragment).commit();
        closeRoutesFragment.setInterfaceCloseRoutes(new CloseRoutesFragment.InterfaceCloseRoutes() {
            @Override
            public void closeRoutes(int close) {
                if(close == 1){
                    fragmentPosition();
                    fragmentOptions();
                }

            }
        });

    }
}
