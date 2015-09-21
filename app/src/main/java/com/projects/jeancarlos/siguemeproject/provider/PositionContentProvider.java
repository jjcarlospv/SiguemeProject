package com.projects.jeancarlos.siguemeproject.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.projects.jeancarlos.siguemeproject.database.DataBaseManager;


/**
 * Created by alumno on 8/22/15.
 */
public class PositionContentProvider extends ContentProvider {

    public static final Uri URI = Uri.parse("content://com.projects.jeancarlos.siguemeproject.PositionContentProvider");
    public static final Uri URI_POSITION = Uri.parse("content://com.projects.jeancarlos.siguemeproject.PositionContentProvider/Position");
    public static final Uri URI_ROUTE = Uri.parse("content://com.projects.jeancarlos.siguemeproject.PositionContentProvider/Route");

    private DataBaseManager dataBaseManager;

    private static final int POSITION = 1;
    private static final int POSITION_ID = 2;
    private static final int ROUTE = 3;
    private static final int ROUTE_ID = 4;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(URI.getAuthority(), "Position", POSITION);
        uriMatcher.addURI(URI.getAuthority(), "Position/#", POSITION_ID);
        uriMatcher.addURI(URI.getAuthority(), "Route", ROUTE);
        uriMatcher.addURI(URI.getAuthority(), "Route/#", ROUTE_ID);
    }

    public static final String MIME_ITEM = "vnd.android.cursor.item/vnd.com.projects.jeancarlos.siguemeproject";
    public static final String MIME_DIR = "vnd.android.cursor.dir/vnd.com.projects.jeancarlos.siguemeproject";


    @Override
    public boolean onCreate() {

        dataBaseManager = new DataBaseManager(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor = null;
        if (uriMatcher.match(uri) != UriMatcher.NO_MATCH) {

            switch (uriMatcher.match(uri)) {
                case POSITION_ID:
                    selection = "_id=" + uri.getLastPathSegment();
                    cursor = dataBaseManager.sqLiteDatabase.query(DataBaseManager.TABLE_NAME_POSITION, projection, selection, selectionArgs, null, null, sortOrder);
                    break;

                case POSITION:
                    cursor = dataBaseManager.sqLiteDatabase.query(DataBaseManager.TABLE_NAME_POSITION, projection, selection, selectionArgs, null, null, sortOrder);
                    break;

                case ROUTE_ID:
                    selection = "_id=" + uri.getLastPathSegment();
                    cursor = dataBaseManager.sqLiteDatabase.query(DataBaseManager.TABLE_NAME_ROUTE, projection, selection, selectionArgs, null, null, sortOrder);
                    break;

                case ROUTE:
                    cursor = dataBaseManager.sqLiteDatabase.query(DataBaseManager.TABLE_NAME_ROUTE, projection, selection, selectionArgs, null, null, sortOrder);
                    break;
            }

        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case POSITION_ID:
                return MIME_ITEM;

            case POSITION:
                return MIME_DIR;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Uri _uri = null;

        if (uriMatcher.match(uri) != UriMatcher.NO_MATCH) {

            switch (uriMatcher.match(uri)) {
                case POSITION:
                    long idp = dataBaseManager.dataBaseHelper.getWritableDatabase().insert(DataBaseManager.TABLE_NAME_POSITION, null, values);
                    if(idp != -1){_uri = ContentUris.withAppendedId(URI_POSITION, idp);}
                    else{_uri = null;}
                    break;

                case ROUTE:
                    long idr = dataBaseManager.dataBaseHelper.getWritableDatabase().insert(DataBaseManager.TABLE_NAME_ROUTE, null, values);
                    if(idr != -1){_uri = ContentUris.withAppendedId(URI_POSITION, idr);}
                    else{_uri = null;}
                    break;
            }
        }
        return _uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int i = 0;

        if (uriMatcher.match(uri) != UriMatcher.NO_MATCH) {

            switch(uriMatcher.match(uri)){
                case POSITION_ID:
                    selection = "_id=" + uri.getLastPathSegment();
                    i = dataBaseManager.dataBaseHelper.getWritableDatabase().delete(DataBaseManager.TABLE_NAME_POSITION,selection,selectionArgs);
                    break;

                case ROUTE_ID:
                    selection = "_id=" + uri.getLastPathSegment();
                    i = dataBaseManager.dataBaseHelper.getWritableDatabase().delete(DataBaseManager.TABLE_NAME_ROUTE,selection,selectionArgs);
                    break;
            }
        }
        return i;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int i = 0;

        if (uriMatcher.match(uri) != UriMatcher.NO_MATCH) {

            switch(uriMatcher.match(uri)){
                case POSITION_ID:
                    selection = "_id=" + uri.getLastPathSegment();
                    i = dataBaseManager.dataBaseHelper.getWritableDatabase().update(DataBaseManager.TABLE_NAME_POSITION, values, selection, selectionArgs);
                    break;

                case ROUTE_ID:
                    selection = "_id=" + uri.getLastPathSegment();
                    i = dataBaseManager.dataBaseHelper.getWritableDatabase().update(DataBaseManager.TABLE_NAME_ROUTE, values, selection, selectionArgs);
                    break;
            }
        }
        return i;
    }

}
