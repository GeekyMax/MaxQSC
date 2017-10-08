package com.geekymax.maxschedule.qsc;

import java.util.List;

/**
 * Created by max on 2017/9/19.
 */

public class Grades {
    public Double totalCredit;
    public Double gradePoint;
    public Double totalCreditMajor;
    public Double gradePointMajor;
    public ScoreObject scoreObject;
    public class ScoreObject{
        public YearGrade grade_2016_2017_AW;
        public YearGrade grade_2016_2017_SS;
        public YearGrade grade_2017_2018_AW;
    }
    public class YearGrade{
        public Double totalCredit;
        public Double totalScore;
        public Double averageScore;
        public Double averangeScore;
        public List<CourseGrade> scoreList;
    }
    public class CourseGrade{
        public Double credit;
        public String gradePoint;
        public String identifier;
        public String makeup;
        public String name;
        public String score;
    }
}
