package com.geekymax.maxschedule.database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by max on 2017/9/24.
 */

public class TotalGrade extends DataSupport{
    @Column(unique = true)
    private String semester = null;
    private double totalCredit =0;
    private double gradePoint=0;
    private double totalScore=0;
    private double totalCreditMajor=0;
    private double gradePointMajor=0;

    public String getSemester() {
        return semester;
    }

    public double getTotalCredit() {
        return totalCredit;
    }

    public double getGradePoint() {
        return gradePoint;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public double getTotalCreditMajor() {
        return totalCreditMajor;
    }

    public double getGradePointMajor() {
        return gradePointMajor;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setTotalCredit(double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public void setGradePoint(double gradePoint) {
        this.gradePoint = gradePoint;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public void setTotalCreditMajor(double totalCreditMajor) {
        this.totalCreditMajor = totalCreditMajor;
    }

    public void setGradePointMajor(double gradePointMajor) {
        this.gradePointMajor = gradePointMajor;
    }
    /*
                "totalCredit": 52.5,
            "gradePoint": 3.28,
            "totalCreditMajor": 38,
            "gradePointMajor": 3.21,
            "scoreObject": {
                "2016-2017AW": {
                    "totalCredit": 25,
                    "totalScore": 88.95,
                    "averageScore": 3.56,
                    "averangeScore": 3.56,
                    "scoreList": [{
     */
}
