package com.geekymax.maxschedule.database;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by max on 2017/9/17.
 */

public class Deadline extends DataSupport{
    public static final int DEADLINE = 0;
    public static final int SIMPLE_DEADLINE = 1;
    private int id;
    private String event="";
    private String detail="";
    private String location="";
    private Date date=null;

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getDetail() {
        return detail;
    }

    public String getEvent() {
        return event;
    }

    public String getLocation() {
        return location;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
