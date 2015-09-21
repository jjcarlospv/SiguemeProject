package com.projects.jeancarlos.siguemeproject.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by JeanPaucar on 21/08/2015.
 */
public class DateUtil {

    public static String getDate(String created) {
        String tempDate = "";
        SimpleDateFormat format_origin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format_origin.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        SimpleDateFormat format_destiny = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = format_origin.parse(created);
            tempDate = format_destiny.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tempDate;
    }

    public static String getHour(String created) {
        String tempHour = "";
        SimpleDateFormat format_origin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format_origin.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        SimpleDateFormat format_destiny = new SimpleDateFormat("HH:mm");
        try {
            Date date = format_origin.parse(created);
            tempHour = format_destiny.format(date);
            if (date.getHours() > 11) {
                tempHour = tempHour + " pm";
            } else {
                tempHour = tempHour + " am";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tempHour;
    }

    /**get Month**/
    public static String getMonth(String tempDate){
        int numberMonth = 0;
        String nameMonth = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        try {
            Date date = format.parse(tempDate);
            numberMonth = date.getMonth();
            switch (numberMonth){
                case 0:nameMonth="Enero";break;
                case 1:nameMonth="Febrero";break;
                case 2:nameMonth="Marzo";break;
                case 3:nameMonth="Abril";break;
                case 4:nameMonth="Mayo";break;
                case 5:nameMonth="Junio";break;
                case 6:nameMonth="Julio";break;
                case 7:nameMonth="Agosto";break;
                case 8:nameMonth="Setiembre";break;
                case 9:nameMonth="Octubre";break;
                case 10:nameMonth="Noviembre";break;
                case 11:nameMonth="Diciembre";break;
                default:nameMonth="Enero";break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nameMonth;
    }
}
