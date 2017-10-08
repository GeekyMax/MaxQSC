package com.geekymax.maxschedule.qsc;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by max on 2017/9/19.
 */

public class Exams {
    @SerializedName("2017-2018WM")
    public List<Exam> exams_2017_2018_WM;
    @SerializedName("2017-2018Wi")
    public List<Exam> exams_2017_2018_Wi;
    @SerializedName("2017-2018Au")
    public List<Exam> exams_2017_2018_Au;
    @SerializedName("2016-2017Au")
    public List<Exam> exams_2016_2017_Au;
    @SerializedName("2016-2017Wi")
    public List<Exam> exams_2016_2017_Wi;
    @SerializedName("2016-2017Sp")
    public List<Exam> exams_2016_2017_Sp;
    @SerializedName("2016-2017Su")
    public List<Exam> exams_2016_2017_Su;

    public class Exam{
        public String credit;
        public String identifier;
        public String name;
        public String place;
        public String seat;
        public String semester;
        @SerializedName("semester_real")
        public String semesterReal;
        public List<String> semesterArray;
        public String time;
        public String timeStart;
        public String timeEnd;
    }
}
