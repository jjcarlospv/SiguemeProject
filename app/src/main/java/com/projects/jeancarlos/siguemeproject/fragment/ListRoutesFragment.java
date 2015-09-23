package com.projects.jeancarlos.siguemeproject.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.projects.jeancarlos.siguemeproject.R;
import com.projects.jeancarlos.siguemeproject.adapter.PositionAdapter;
import com.projects.jeancarlos.siguemeproject.adapter.RouteAdapter;
import com.projects.jeancarlos.siguemeproject.model.Position_DTO;
import com.projects.jeancarlos.siguemeproject.model.Route_DTO;
import com.projects.jeancarlos.siguemeproject.provider.PositionContentProvider;

import java.util.ArrayList;

/**
 * Created by JEANCARLOS on 22/09/2015.
 */
public class ListRoutesFragment extends Fragment {

    private ListView fragment_list_routes;
    private ArrayList<Route_DTO> listRoutesItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_routes,container,false);
        fragment_list_routes = (ListView)view.findViewById(R.id.fragment_list_routes);

        listRoutesItems = new ArrayList<Route_DTO>();

        final Cursor cursor = getActivity().getContentResolver().query(PositionContentProvider.URI_ROUTE, null, null, null, null);

        while(cursor.moveToNext()){

            listRoutesItems.add(new Route_DTO(
                    cursor.getString(0)
                    , cursor.getString(1)
                    , cursor.getString(2)
                    , cursor.getString(3)
                    , cursor.getString(4)
                    , cursor.getString(5)
                    , cursor.getString(6)
            ));
        }
        fragment_list_routes.setAdapter(new RouteAdapter(getActivity(), listRoutesItems));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
