package com.geekymax.maxschedule.database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by max on 2017/9/24.
 */

public class Grade extends DataSupport{
    @Column(unique = true)
    private String identifier;
    private String name;
    private double credit;
    private String gradePoint;
    private String score;
    private String semester;

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public double getCredit() {
        return credit;
    }

    public String getGradePoint() {
        return gradePoint;
    }

    public String getScore() {
        return score;
    }

    public String getSemester() {
        return semester;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setGradePoint(String gradePoint) {
        this.gradePoint = gradePoint;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
