package com.projects.jeancarlos.siguemeproject;

import android.content.ContentValues;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.projects.jeancarlos.siguemeproject.dialog.CreateRouteDialog;
import com.projects.jeancarlos.siguemeproject.fragment.ContentFragment;
import com.projects.jeancarlos.siguemeproject.fragment.DescriptionMaskFragment;
import com.projects.jeancarlos.siguemeproject.fragment.NavigationDrawerFragment;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.DrawerFragmentListener {

    public final static String SHARE_PREF_NAME_STATUS = "com.projects.jeancarlos.siguemeproject.main.STATUS";
    public final static String EXTRA_FRAGMENT_ROUTE = "routeFragment";
    public final static String EXTRA_FRAGMENT_DESCRIPTION = "descriptionFragment";
    public final static String EXTRA_FRAGMENT_OPTIONS = "optionsFragment";

    public final static String FRAGMENT_LIST_ROUTES = "1";
    public final static String FRAGMENT_MASK_DESCRIPTION = "2";
    public final static String FRAGMENT_MASK_OPTIONS = "3";
    public final static String FRAGMENT_OPTIONS = "4";
    public final static String FRAGMENT_POSITION = "5";
    public final static String FRAGMENT_ROUTE_IN_PROCESS = "I";
    public final static String FRAGMENT_ROUTE_NONE = "N";


    public static String SAVE_FRAGMENT_DESCRIPTION = "2";
    public static String SAVE_FRAGMENT_OPTIONS = "3";

    private Toolbar toolbar;
    private NavigationDrawerFragment drawerFragment;

    private ContentFragment contentFragment;


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
                firstOption();
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

    private void firstOption(){
        final CreateRouteDialog createRouteDialog = new CreateRouteDialog(MainActivity.this);
        createRouteDialog.show();
        createRouteDialog.setInterfaceDialogRoute(new CreateRouteDialog.InterfaceDialogRoute() {
            @Override
            public void closeDialogRoute(int i, String nameRoute, String description) {
                if (i == 0) {
                    createRouteDialog.dismiss();
                }

                if (i == 1) {
                    createRouteDialog.dismiss();
                    SAVE_FRAGMENT_DESCRIPTION = FRAGMENT_MASK_DESCRIPTION;
                    SAVE_FRAGMENT_OPTIONS = FRAGMENT_OPTIONS;

                    /*String date = df.format(Calendar.getInstance().getTime());

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DataBaseManager.NAME, nameRoute);
                    contentValues.put(DataBaseManager.DESCRIPTION, description);
                    contentValues.put(DataBaseManager.DATE_BEGIN, DateUtil.getDate(date));
                    contentValues.put(DataBaseManager.HOUR_BEGIN, DateUtil.getHour(date));

                    getSharedPreferences(PositionService.SHARE_PREF_NAME_POSITION_SERVICE,MODE_PRIVATE).edit().putString(PositionService.SHARE_PREF_KEY_ROUTE_NAME,nameRoute).commit();*/


                    contentFragment = new ContentFragment();
                    getFragmentManager().beginTransaction().replace(R.id.activity_main_container,contentFragment).commit();
                }
            }
        });
    }
}
