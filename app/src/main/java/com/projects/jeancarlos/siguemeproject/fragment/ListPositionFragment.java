package com.projects.jeancarlos.siguemeproject.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.projects.jeancarlos.siguemeproject.R;
import com.projects.jeancarlos.siguemeproject.adapter.PositionAdapter;
import com.projects.jeancarlos.siguemeproject.model.Position_DTO;
import com.projects.jeancarlos.siguemeproject.provider.PositionContentProvider;

import java.util.ArrayList;

/**
 * Created by JEANCARLOS on 21/09/2015.
 */
public class ListPositionFragment extends Fragment {

    private TextView fragment_position_txt_location;
    private ListView fragment_position_list_position;
    private ArrayList<Position_DTO> listPositionItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_position,container,false);

        fragment_position_txt_location = (TextView)view.findViewById(R.id.fragment_position_txt_location);
        fragment_position_list_position = (ListView)view.findViewById(R.id.fragment_position_list_position);

        listPositionItems = new ArrayList<Position_DTO>();

        final Cursor cursor = getActivity().getContentResolver().query(PositionContentProvider.URI_POSITION, null, null, null, null);

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
        fragment_position_list_position.setAdapter(new PositionAdapter(getActivity(), listPositionItems));


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
