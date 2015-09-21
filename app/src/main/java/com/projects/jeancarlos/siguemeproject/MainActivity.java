package com.projects.jeancarlos.siguemeproject;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.projects.jeancarlos.siguemeproject.fragment.NavigationDrawerFragment;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.DrawerFragmentListener {

    private Toolbar toolbar;
    private NavigationDrawerFragment drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

        switch(position){

            case 0:
                Toast.makeText(MainActivity.this,"0",Toast.LENGTH_SHORT).show();
                break;

            case 1:
                Toast.makeText(MainActivity.this,"1",Toast.LENGTH_SHORT).show();
                break;

            case 2:
                Toast.makeText(MainActivity.this,"2",Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
