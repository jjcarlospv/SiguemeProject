package com.projects.jeancarlos.siguemeproject.model;

/**
 * Created by JEANCARLOS on 30/08/2015.
 */
public class Route_DTO {
    private String id;
    private String name;
    private String description;
    private String date_begin;
    private String hour_begin;
    private String date_end;
    private String hour_end;

    public Route_DTO(){}

    public Route_DTO(String id, String name, String description, String date_begin, String hour_begin, String date_end, String hour_end) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date_begin = date_begin;
        this.hour_begin = hour_begin;
        this.date_end = date_end;
        this.hour_end = hour_end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_begin() {
        return date_begin;
    }

    public void setDate_begin(String date_begin) {
        this.date_begin = date_begin;
    }

    public String getHour_begin() {
        return hour_begin;
    }

    public void setHour_begin(String hour_begin) {
        this.hour_begin = hour_begin;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getHour_end() {
        return hour_end;
    }

    public void setHour_end(String hour_end) {
        this.hour_end = hour_end;
    }
}
