package com.geekymax.maxschedule;

import java.util.Date;

/**
 * Created by max on 2017/9/16.
 */

public class TimeItem {
    public final static int EMPTY = 0;
    public final static int TIME_BLOCK = 2;
    public final static int TIME_POINT = 1;
    private String title = null;
    private int itemType = 0;
    private Date startDate = null;
    private Date endDate = null;
    private String place = null;
    private String detail = null;
    private int state = 0;

    public TimeItem(int itemType) {
        this.setItemType(itemType);
    }

    public TimeItem() {
        itemType = EMPTY;
    }

    public String getTitle() {
        return title;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getItemType() {
        return itemType;
    }

    public int getState() {
        return state;
    }

    public String getDetail() {
        return detail;
    }

    public String getPlace() {
        return place;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
