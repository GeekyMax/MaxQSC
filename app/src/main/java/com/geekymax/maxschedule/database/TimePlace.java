package com.geekymax.maxschedule.database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by max on 2017/9/20.
 */

public class TimePlace extends DataSupport{
    @Column(unique = true)
    private String hash;
    private String code;
    private String studentId;
    private String name;
    private int dayOfWeek;
    private int startCourse;
    private int endCourse;
    private String year;
    private String semester;
    private String place;
    private String day;
    private String week;

    public String getStudentId() {
        return studentId;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getEndCourse() {
        return endCourse;
    }

    public int getStartCourse() {
        return startCourse;
    }

    public String getCode() {
        return code;
    }

    public String getDay() {
        return day;
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getSemester() {
        return semester;
    }

    public String getWeek() {
        return week;
    }

    public String getYear() {
        return year;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setEndCourse(int endCourse) {
        this.endCourse = endCourse;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setStartCourse(int startCourse) {
        this.startCourse = startCourse;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
