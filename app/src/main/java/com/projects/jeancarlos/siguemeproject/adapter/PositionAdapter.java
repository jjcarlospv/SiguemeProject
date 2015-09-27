package com.projects.jeancarlos.siguemeproject.adapter;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projects.jeancarlos.siguemeproject.R;
import com.projects.jeancarlos.siguemeproject.model.Position_DTO;

import java.util.ArrayList;

/**
 * Created by JEANCARLOS on 21/09/2015.
 */
public class PositionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Position_DTO> position_dtos;
    private LayoutInflater layoutInflater;

    public PositionAdapter(Context context, ArrayList<Position_DTO> position_dtos) {
        this.context = context;
        this.position_dtos = position_dtos;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return position_dtos.size();
    }

    @Override
    public Object getItem(int i) {
        return position_dtos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder;
        Position_DTO position_dto = position_dtos.get(i);

        if(view == null){

            holder = new Holder();
            view = layoutInflater.inflate(R.layout.item_position,viewGroup,false);
            holder.latitude = (TextView)view.findViewById(R.id.item_position_latitude);
            holder.longitude = (TextView)view.findViewById(R.id.item_position_longitude);
            holder.address = (TextView)view.findViewById(R.id.item_position_address);
            holder.date = (TextView)view.findViewById(R.id.item_position_date);
            holder.hour = (TextView)view.findViewById(R.id.item_position_hour);

            view.setTag(holder);
        }
        else{
            holder = (Holder)view.getTag();
        }

        holder.latitude.setText(context.getString(R.string.item_position_latitude,position_dto.getLatitud()));
        holder.longitude.setText(context.getString(R.string.item_position_longitude,position_dto.getLongitud()));
        holder.address.setText(context.getString(R.string.item_position_address,position_dto.getAddress()));
        holder.date.setText(context.getString(R.string.item_position_date,position_dto.getDate()));
        holder.hour.setText(context.getString(R.string.item_position_hour,position_dto.getHour()));

        return view;
    }

    public class Holder{
        TextView address;
        TextView latitude;
        TextView longitude;
        TextView date;
        TextView hour;
    }
}
