package com.projects.jeancarlos.siguemeproject.adapter;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projects.jeancarlos.siguemeproject.R;
import com.projects.jeancarlos.siguemeproject.model.Route_DTO;

import java.util.ArrayList;

/**
 * Created by JEANCARLOS on 21/09/2015.
 */
public class RouteAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Route_DTO> route_dtos;
    private LayoutInflater layoutInflater;

    public RouteAdapter(Context context, ArrayList<Route_DTO> route_dtos) {
        this.context = context;
        this.route_dtos = route_dtos;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return route_dtos.size();
    }

    @Override
    public Object getItem(int i) {
        return route_dtos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Route_DTO route_dto = route_dtos.get(i);
        Holder holder;

        if(view == null){
            holder = new Holder();
            view = layoutInflater.inflate(R.layout.item_route,viewGroup,false);

            holder.name = (TextView)view.findViewById(R.id.item_route_name);
            holder.description = (TextView)view.findViewById(R.id.item_route_description);
            holder.date_begin = (TextView)view.findViewById(R.id.item_route_date_begin);
            holder.hour_begin = (TextView)view.findViewById(R.id.item_route_hour_begin);
            holder.date_end = (TextView)view.findViewById(R.id.item_route_date_end);
            holder.hour_end = (TextView)view.findViewById(R.id.item_route_hour_end);

            view.setTag(holder);
        }
        else{
            holder =(Holder)view.getTag();
        }

        holder.name.setText(route_dto.getName());
        holder.description.setText(route_dto.getDescription());
        holder.date_begin.setText(route_dto.getDate_begin());
        holder.hour_begin.setText(route_dto.getHour_begin());
        holder.date_end.setText(route_dto.getDate_end());
        holder.hour_end.setText(route_dto.getHour_end());

        return view;
    }

    private class Holder{
        TextView name;
        TextView description;
        TextView date_begin;
        TextView hour_begin;
        TextView date_end;
        TextView hour_end;
    }
}
