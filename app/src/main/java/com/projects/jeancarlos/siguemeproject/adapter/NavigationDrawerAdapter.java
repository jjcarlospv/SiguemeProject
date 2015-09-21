package com.projects.jeancarlos.siguemeproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.jeancarlos.siguemeproject.R;
import com.projects.jeancarlos.siguemeproject.model.NavigationDrawerItem;

import java.util.List;

/**
 * Created by JEANCARLOS on 20/09/2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {

    private List<NavigationDrawerItem> navigationDrawerItemList;
    private LayoutInflater layoutInflater;
    private Context context;

    public NavigationDrawerAdapter(List<NavigationDrawerItem> navigationDrawerItemList, Context context) {
        this.navigationDrawerItemList = navigationDrawerItemList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_navigation_drawer, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavigationDrawerItem navigationDrawerItem = navigationDrawerItemList.get(position);
        holder.titleIcon.setText(navigationDrawerItem.getTitleIcon());
    }

    @Override
    public int getItemCount() {
        return navigationDrawerItemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleIcon = (TextView) itemView.findViewById(R.id.item_navigation_drawer_title);
        }
    }
}
