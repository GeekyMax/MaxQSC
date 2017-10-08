package com.geekymax.maxschedule.qsc;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by max on 2017/9/18.
 */
public class Courses {
    @SerializedName("2017-2018AW")
    public List<Course> courses_2017_2018_AW;
    @SerializedName("2017-2018SM")
    public  List<Course> courses_2017_2018_SM;
    @SerializedName("2016-2017AW")
    public  List<Course> courses_2016_2017_AW;
    @SerializedName("2016-2017SS")
    public List<Course> courses_2016_2017_SS;
    public class Course {
        public String code;
        public String name;
        public String identifier;
        public String teacher;
        public String semester;
        public List<String> semesterArray;
        public List<TimePlace> timePlace;
        public String status;
        public boolean determined;
        public String v2hash;
        public String hash;
        public String year;
        public BasicInfo basicInfo;
        public List<String> place;
        public List<String> time;
        public String location;
        public String term;
    }
    public class TimePlace{
        public String day;
        public String week;
        public String place;
        public List<String> course;
        public String semester;
        public String dayOfWeek;
    }
    public class BasicInfo{
        @SerializedName("Credit")
        public String credit;
        @SerializedName("Name")
        public String name;
        @SerializedName("EnglishName")
        public String englishName;
        @SerializedName("Faculty")
        public String faculty;
        @SerializedName("TimesWeekly")
        public String timesWeekly;
        @SerializedName("WeightsNumber")
        public String weightsNumber;
        @SerializedName("Category")
        public String category;
        @SerializedName("Prerequisite")
        public String prerequisite;
        @SerializedName("Code")
        public String code;
    }
}
