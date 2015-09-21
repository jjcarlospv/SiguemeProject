package com.projects.jeancarlos.siguemeproject.model;

/**
 * Created by alumno on 8/22/15.
 */
public class Position_DTO {

    private String id;
    private String address;
    private String latitud;
    private String longitud;
    private String date;
    private String hour;
    private String route;

    public Position_DTO(){}

    public Position_DTO(String id, String address, String latitud, String longitud, String date, String hour, String route) {
        this.id = id;
        this.address = address;
        this.latitud = latitud;
        this.longitud = longitud;
        this.date = date;
        this.hour = hour;
        this.route = route;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
