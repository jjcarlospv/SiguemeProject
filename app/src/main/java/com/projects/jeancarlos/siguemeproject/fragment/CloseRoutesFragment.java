package com.projects.jeancarlos.siguemeproject.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.projects.jeancarlos.siguemeproject.R;

/**
 * Created by JEANCARLOS on 24/09/2015.
 */
public class CloseRoutesFragment extends Fragment implements View.OnClickListener {

    private Button fragment_close_routes_close;
    private InterfaceCloseRoutes interfaceCloseRoutes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_close_routes, container, false);

        fragment_close_routes_close = (Button)view.findViewById(R.id.fragment_close_routes_close);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragment_close_routes_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        interfaceCloseRoutes.closeRoutes(1);
    }

    /* Interfaces*/

    public interface InterfaceCloseRoutes{
        void closeRoutes(int close);
    }

    public void setInterfaceCloseRoutes(InterfaceCloseRoutes interfaceCloseRoutes){
        this.interfaceCloseRoutes = interfaceCloseRoutes;
    }
}
