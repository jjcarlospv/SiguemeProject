package com.projects.jeancarlos.siguemeproject.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.projects.jeancarlos.siguemeproject.MainActivity;
import com.projects.jeancarlos.siguemeproject.R;

/**
 * Created by JEANCARLOS on 20/09/2015.
 */
public class ContentFragment extends Fragment {

    private String fragment_description_Type;
    private String fragment_options_Type;
    private OptionsFragment optionsFragment;
    private DescriptionMaskFragment descriptionMaskFragment;
    private PositionFragment positionFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragment_options_Type = getActivity().getSharedPreferences(MainActivity.SHARE_PREF_NAME_STATUS, Context.MODE_PRIVATE).getString(MainActivity.EXTRA_FRAGMENT_OPTIONS, MainActivity.FRAGMENT_MASK_OPTIONS);
        fragment_description_Type = getActivity().getSharedPreferences(MainActivity.SHARE_PREF_NAME_STATUS,Context.MODE_PRIVATE).getString(MainActivity.EXTRA_FRAGMENT_DESCRIPTION,MainActivity.FRAGMENT_MASK_DESCRIPTION);

        optionsFragment = new OptionsFragment();
        descriptionMaskFragment = new DescriptionMaskFragment();
        getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment_content_container_description, descriptionMaskFragment).commit();
        getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment_content_container_options, optionsFragment).commit();

        optionsFragment.setInterfaceOptions(new OptionsFragment.InterfaceOptions() {
            @Override
            public void getOption(int i) {

                if (i == 0) {

                    final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        positionFragment = new PositionFragment();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_content_container_description, positionFragment).commit();

                        //SAVE_FRAGMENT_DESCRIPTION = FRAGMENT_POSITION;
                        //SAVE_FRAGMENT_OPTIONS = FRAGMENT_OPTIONS;
                        //startService(intentService);
                        Toast.makeText(getActivity(), R.string.main_activity_start_service, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), R.string.main_activity_error_gps, Toast.LENGTH_SHORT).show();
                    }

                }

                if (i == 1) {
                    //listPositionFragment = new ListPositionFragment();
                    //getFragmentManager().beginTransaction().replace(R.id.fragment_content_container_description, listPositionFragment).commit();
                }

                if (i == 2) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(getResources().getString(R.string.main_activity_exit_message))
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    //stopService(intentService);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }
}
