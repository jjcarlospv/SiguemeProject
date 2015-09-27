package com.projects.jeancarlos.siguemeproject.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.projects.jeancarlos.siguemeproject.R;
import com.projects.jeancarlos.siguemeproject.adapter.PositionAdapter;
import com.projects.jeancarlos.siguemeproject.database.DataBaseManager;
import com.projects.jeancarlos.siguemeproject.model.Position_DTO;
import com.projects.jeancarlos.siguemeproject.provider.PositionContentProvider;
import com.projects.jeancarlos.siguemeproject.service.PositionService;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by JEANCARLOS on 21/09/2015.
 */
public class ListPositionFragment extends Fragment {

    private TextView fragment_position_txt_location;
    private ListView fragment_position_list_position;
    private ArrayList<Position_DTO> listPositionItems;
    private ProgressDialog progressDialog;

    static Cursor cursor;
    //HttpConnection httpConnection;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_position,container,false);

        fragment_position_txt_location = (TextView)view.findViewById(R.id.fragment_position_txt_location);
        fragment_position_list_position = (ListView)view.findViewById(R.id.fragment_position_list_position);

        listPositionItems = new ArrayList<Position_DTO>();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String tempNameRoute = getActivity().getSharedPreferences(PositionService.SHARE_PREF_NAME_POSITION_SERVICE, getActivity().MODE_PRIVATE).getString(PositionService.SHARE_PREF_KEY_ROUTE_NAME, PositionService.SHARE_PREF_KEY_ROUTE_NULL);
        cursor = getActivity().getContentResolver().query(PositionContentProvider.URI_POSITION, null, DataBaseManager.ID_ROUTE +"=?", new String[]{tempNameRoute}, null);


        Log.e("ListPFrag", "onActivityCreated");

        new AsyncTask<Void, String, String>() {

            final static String REVERSE_GEOCODING_API_URL = "http://nominatim.openstreetmap.org/reverse";

            String latitude;
            String longitude;
            String url;
            String stringOri;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Cargando Lista");
                progressDialog.setMessage("Espere un momento ...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(Void... voids) {

              while(cursor.moveToNext()){
                try {
                        latitude = URLEncoder.encode(cursor.getString(2), "utf-8");
                        longitude = URLEncoder.encode(cursor.getString(3), "utf-8");
                        url = REVERSE_GEOCODING_API_URL + "?format=json" + "&lat=" + latitude + "&lon=" + longitude;

                } catch (IOException e) {
                    e.printStackTrace();
                }


                final HttpClient httpClient = new DefaultHttpClient();
                final HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader("Accept", "application/json");

                try {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(httpClient.execute(httpGet, new BasicResponseHandler()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stringOri = jsonObject.getString("display_name");

                    listPositionItems.add(new Position_DTO(
                            "id"
                            , stringOri
                            , cursor.getString(2)
                            , cursor.getString(3)
                            , cursor.getString(4)
                            , cursor.getString(5)
                            , ""
                    ));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
                return stringOri;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                fragment_position_list_position.setAdapter(new PositionAdapter(getActivity(), listPositionItems));
                progressDialog.dismiss();
            }
        }.execute();
    }
}
