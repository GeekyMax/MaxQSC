package com.geekymax.maxschedule.database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by max on 2017/9/20.
 */

public class Course extends DataSupport {
    @Column(unique = true)
    private String hash;
    private String name;
    private String studentId;
    private String code;
    private String credit;
    private String teacher;
    private String semester;
    private String englishName;
    private String Faculty;
    private String category;
    private String timesWeekly;
    private String year;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSemester() {
        return semester;
    }

    public String getYear() {
        return year;
    }


    public String getCredit() {
        return credit;
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }


    public String getEnglishName() {
        return englishName;
    }

    public String getFaculty() {
        return Faculty;
    }

    public String getCategory() {
        return category;
    }

    public String getTimesWeekly() {
        return timesWeekly;
    }

    public String getCode() {
        return code;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTimesWeekly(String timesWeekly) {
        this.timesWeekly = timesWeekly;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
