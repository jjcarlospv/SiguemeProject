package com.projects.jeancarlos.siguemeproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by alumno on 8/22/15.
 */
public class DataBaseManager {

    /**
     * Definicion de tipos de variable*
     */
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";
    public static final String BOOLEAN_TYPE = "boolean";
    public static final String PK = "PRIMARY KEY AUTOINCREMENT";
    public static final String FK = "FOREIGN KEY (";
    public static final String REF = " REFERENCES ";

    /**
     * Nombres de tablas
     */
    public static final String TABLE_NAME_POSITION = "Position";
    public static final String TABLE_NAME_ROUTE = "Route";

    /**
     * Definicion de variables de campo
     */
    public static final String ID = "_id";
    public static final String ADDRESS = "address";
    public static final String LATITUD = "latitud";
    public static final String LONGITUD = "longitud";
    public static final String DATE = "date";
    public static final String HOUR = "hour";
    public static final String ROUTE = "route";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String DATE_BEGIN = "dateBegin";
    public static final String HOUR_BEGIN = "hourBegin";
    public static final String DATE_END = "dateEnd";
    public static final String HOUR_END = "hourEnd";
    public static final String ID_ROUTE = "idRoute";


    /**
     * Creacion de Tablas
     */
    public static final String CREATE_SCRIPT_POSITION = "CREATE TABLE " + TABLE_NAME_POSITION + "("+

            ID + " " + INT_TYPE + " " + PK + ","+
            ADDRESS + " " + STRING_TYPE + "," +
            LATITUD + " " + STRING_TYPE + "," +
            LONGITUD + " " + STRING_TYPE + "," +
            DATE + " " + STRING_TYPE + "," +
            HOUR + " " + STRING_TYPE + "," +
            ID_ROUTE + " " + STRING_TYPE + ")";

    public static final String DROP_TABLE_POSITION = "drop table if exists " + TABLE_NAME_POSITION;

    public static final String CREATE_SCRIPT_ROUTE = "CREATE TABLE " + TABLE_NAME_ROUTE + "("+

            ID + " " + INT_TYPE + " " + PK + ","+
            NAME + " " + STRING_TYPE + "," +
            DESCRIPTION + " " + STRING_TYPE + "," +
            DATE_BEGIN + " " + STRING_TYPE + "," +
            HOUR_BEGIN + " " + STRING_TYPE + "," +
            DATE_END + " " + STRING_TYPE + "," +
            HOUR_END + " " + STRING_TYPE + ")";

    public static final String DROP_TABLE_ROUTE = "drop table if exists " + TABLE_NAME_ROUTE;

    /**
     * Creacion de Metodos
     */

    public DataBaseHelper dataBaseHelper;
    public static SQLiteDatabase sqLiteDatabase;


    public DataBaseManager(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
    }
/*
    public static void insertDataPosition(Position_DTO position_dto){
        ContentValues contentValues = new ContentValues();

        contentValues.put(ADDRESS,position_dto.getAddress());
        contentValues.put(LATITUD,position_dto.getLatitud());
        contentValues.put(LONGITUD,position_dto.getLongitud());

        sqLiteDatabase.insert(TABLE_NAME_POSITION,null,contentValues);
    }

    public static Position_DTO readDataPositionById(int id){

        Position_DTO position_dto = new Position_DTO();
        final String[] columns = new String[]{ADDRESS, LATITUD, LONGITUD,DATE,HOUR};
        final Cursor cursor = sqLiteDatabase.query(TABLE_NAME_POSITION,columns, ID+"=?",new String[]{String.valueOf(id)},null,null,null);

        return null;
    }

    public Collection<Position_DTO> getPositionTable(){
        final Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_POSITION,null);
        final Collection<Position_DTO> position_Collection = new ArrayList<>();

        while(cursor.moveToNext()){
            final String id = cursor.getString(cursor.getColumnIndex(ID));
            final String address = cursor.getString(cursor.getColumnIndex(ADDRESS));
            final String latitud = cursor.getString(cursor.getColumnIndex(LATITUD));
            final String longitud = cursor.getString(cursor.getColumnIndex(LONGITUD));
            final String date = cursor.getString(cursor.getColumnIndex(DATE));
            final String hour= cursor.getString(cursor.getColumnIndex(HOUR));
            final String route= cursor.getString(cursor.getColumnIndex(ROUTE));

            Position_DTO position_dto = new Position_DTO(id, address, latitud, longitud, date, hour, route);
            position_Collection.add(position_dto);
        }

        cursor.close();
        return  position_Collection;
    }*/

}
