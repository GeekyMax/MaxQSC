package com.geekymax.maxschedule.qsc;

import android.util.Log;

import com.geekymax.maxschedule.database.Course;
import com.geekymax.maxschedule.database.Grade;
import com.geekymax.maxschedule.database.TimePlace;
import com.geekymax.maxschedule.database.TotalGrade;
import com.geekymax.maxschedule.database.UserSession;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by max on 2017/9/19.
 */

public class QscService {
    private final static String TAG = "QscService";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public interface SessionCallBack {
        public void callBack(UserSession session);

        public void onFailure(Exception e);
    }

    public interface ResourcesCallBack {
        public void onFailure(Exception e);

        public void coursesCallBack(Courses courses);

        public void gradeCallBack(Grades grades);

        public void examsCallBack(Exams exams);

        public void allCallBack(Courses courses, Grades grades, Exams exams);
    }

    public static final void getResources(UserSession session, final ResourcesCallBack callBack) {
        Log.d(TAG, "getResources: ");
        String json = "{\"sessionId\":\"" + session.getSessionId() + "\",\"sessionVerify\":\"222222\",\"requestList\":\"[{\\\"uuid\\\":\\\"buses\\\",\\\"fetcher\\\":\\\"staticInterface\\\",\\\"data\\\":\\\"{\\\\\\\"key\\\\\\\":\\\\\\\"schoolBus\\\\\\\"}\\\",\\\"version\\\":\\\"semido-db-1505725200\\\"},{\\\"uuid\\\":\\\"acal\\\",\\\"fetcher\\\":\\\"staticInterface\\\",\\\"data\\\":\\\"{\\\\\\\"key\\\\\\\":\\\\\\\"schoolCal.all\\\\\\\"}\\\",\\\"version\\\":\\\"semido-db-1505725200\\\"},{\\\"uuid\\\":\\\"courses\\\",\\\"fetcher\\\":\\\"jwbInfoSystem\\\",\\\"data\\\":\\\"{\\\\\\\"action\\\\\\\":\\\\\\\"CourseAll\\\\\\\",\\\\\\\"noParse\\\\\\\":true}\\\",\\\"version\\\":\\\"API_2_5_83bc54ef8cea9779\\\"},{\\\"uuid\\\":\\\"grades\\\",\\\"fetcher\\\":\\\"jwbInfoSystem\\\",\\\"data\\\":\\\"{\\\\\\\"action\\\\\\\":\\\\\\\"ScoreAll\\\\\\\"}\\\",\\\"version\\\":\\\"API_2_5_d421a8573c2086bc\\\"},{\\\"uuid\\\":\\\"exams\\\",\\\"fetcher\\\":\\\"jwbInfoSystem\\\",\\\"data\\\":\\\"{\\\\\\\"action\\\\\\\":\\\\\\\"ExamAll\\\\\\\"}\\\",\\\"version\\\":\\\"API_2_5_4b2910cb73418d2d\\\"},{\\\"uuid\\\":\\\"statistics\\\",\\\"fetcher\\\":\\\"jwbInfoSystem\\\",\\\"data\\\":\\\"{\\\\\\\"action\\\\\\\":\\\\\\\"Statistics\\\\\\\"}\\\",\\\"version\\\":\\\"API_2_5_83bc54ef8cea9779\\\"}]\"}";
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS).readTimeout(8000, TimeUnit.SECONDS).build();
        RequestBody requestBody = RequestBody.create(JSON, json);
        final Request request = new Request.Builder().url("http://m.zjuqsc.com/api/v3/s1/getResources")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject all = new JSONObject(response.body().string());
                    String responseListString = all.getString("responseList");
                    if (responseListString != null && !responseListString.equals("")) {
                        JSONArray responseList = new JSONArray(responseListString);
                        JSONObject coursesObject = responseList.getJSONObject(2);
                        JSONObject gradesObject = responseList.getJSONObject(3);
                        JSONObject examsObject = responseList.getJSONObject(4);
                        JSONObject coursesData = coursesObject.getJSONObject("data");
                        JSONObject gradesData = gradesObject.getJSONObject("data");
                        JSONObject examsData = examsObject.getJSONObject("data");
                        Gson gson = new Gson();
                        Exams exams = gson.fromJson(examsData.toString(), Exams.class);
                        Courses courses = gson.fromJson(coursesData.toString(), Courses.class);
                        Grades grades = gson.fromJson(gradesData.toString(), Grades.class);
                        callBack.allCallBack(courses, grades, exams);
                        callBack.coursesCallBack(courses);
                        callBack.gradeCallBack(grades);
                        callBack.examsCallBack(exams);
                    }

                } catch (Exception e) {
                    callBack.onFailure(e);
                    e.printStackTrace();
                }
            }
        });
    }

    public static final void getUserSession(final String id, final String password, final SessionCallBack callBack) {
        Log.d(TAG, "getUserSession: ");
        String json = "{\"appKeyHash\":\"PpKNhS7p\",\"salt\":\"+fF0dqQ4\",\"login\":{\"jwbinfosys\":{\"userName\":\"" + id + "\",\"userPass\":\"" + password + "\"}}}";
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS).build();
        RequestBody requestBody = RequestBody.create(JSON, json);
        final Request request = new Request.Builder().url("http://m.zjuqsc.com/api/v3/s1/login")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject all = new JSONObject(response.body().string());
                    UserSession userSession = new UserSession(id, password);
                    userSession.setSessionId(all.getString("sessionId"));
                    userSession.setSessionKey(all.getString("sessionKey"));
                    if (all.getString("sessionId") == null
                            || all.getString("sessionId").equals("null")
                            || all.getString("sessionId").equals("")
                            ) {
                    } else {
                        if (DataSupport.where("userId = ?", userSession.getUserId()).find(UserSession.class).size() == 0) {
                            userSession.save();
                        } else {
                            userSession.updateAll("userId = ?", userSession.getUserId());
                        }
                    }
                    Log.d(TAG, "run: GetSession");
                    callBack.callBack(userSession);
                } catch (Exception e) {
                    callBack.onFailure(e);
                    e.printStackTrace();
                }
            }
        });
    }

    public static final void handleCourses(Courses courses, String studentId) {
        for (Field field : courses.getClass().getDeclaredFields()) {
            try {
                List<Courses.Course> courseList = (List<Courses.Course>) (field.get(courses));
                for (Courses.Course c : courseList) {
                    Course course = new Course();
                    course.setHash(c.hash);
                    course.setCode(c.code);
                    course.setName(c.name);
                    course.setCategory(c.basicInfo.category);
                    course.setCredit(c.basicInfo.credit);
                    course.setEnglishName(c.basicInfo.englishName);
                    course.setFaculty(c.basicInfo.faculty);
                    course.setSemester(c.semester);
                    course.setTimesWeekly(c.basicInfo.timesWeekly);
                    course.setTeacher(c.teacher);
                    course.setYear(c.year);
                    course.setStudentId(studentId);
                    if (DataSupport.where("hash = ?", course.getHash()).find(Course.class).size() == 0) {
                        course.save();
                    } else {
                        course.updateAll("hash = ?", course.getHash());
                    }
                    if (c.timePlace != null) {
                        for (int i = 0; i < c.timePlace.size(); i++) {
                            Courses.TimePlace t = c.timePlace.get(i);
                            TimePlace timePlace = new TimePlace();
                            timePlace.setYear(c.year);
                            timePlace.setPlace(t.place);
                            timePlace.setHash(c.hash + i);
                            timePlace.setCode(c.code);
                            timePlace.setDay(t.day);
                            timePlace.setName(c.name);
                            timePlace.setSemester(t.semester);
                            timePlace.setWeek(t.week);
                            timePlace.setStudentId(studentId);
                            if (t.dayOfWeek == null || t.dayOfWeek.equals("")) {
                                timePlace.setDayOfWeek(-1);
                            } else {
                                try {
                                    timePlace.setDayOfWeek(Integer.valueOf(t.dayOfWeek).intValue());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (t.course == null || t.course.size() <= 0) {
                                timePlace.setStartCourse(-1);
                                timePlace.setEndCourse(-1);
                            } else {
                                try {
                                    timePlace.setStartCourse(Integer.valueOf(t.course.get(0)).intValue());
                                    timePlace.setEndCourse(
                                            Integer.valueOf(t.course.get(t.course.size() - 1)).intValue());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            if (DataSupport.where("hash = ?", timePlace.getHash()).find(TimePlace.class).size() == 0) {
                                timePlace.save();
                            } else {
                                timePlace.updateAll("hash = ?", timePlace.getHash());
                            }
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static final  void handleGrade(Grades grades) {
        DataSupport.deleteAll(TotalGrade.class);
        TotalGrade totalGrade = new TotalGrade();
        totalGrade.setSemester("total");
        totalGrade.setTotalCredit(grades.totalCredit);
        totalGrade.setGradePoint(grades.gradePoint);
        totalGrade.setTotalCreditMajor(grades.totalCreditMajor);
        totalGrade.setGradePointMajor(grades.gradePointMajor);
        //
        for (Field field : grades.scoreObject.getClass().getDeclaredFields()) {
            try {
                Grades.YearGrade yearGrade = (Grades.YearGrade) (field.get(grades.scoreObject));
                for (Grades.CourseGrade courseGrade : yearGrade.scoreList) {
                    Grade grade = new Grade();
                    grade.setSemester(field.getName());
                    grade.setName(courseGrade.name);
                    grade.setCredit(courseGrade.credit);
                    grade.setGradePoint(courseGrade.gradePoint);
                    grade.setIdentifier(courseGrade.identifier);
                    grade.setScore(courseGrade.score);
                    grade.save();
                }
                TotalGrade totalGrade1 = new TotalGrade();
                totalGrade1.setSemester(field.getName());
                totalGrade1.setTotalCredit(yearGrade.totalCredit);
                totalGrade1.setGradePoint(yearGrade.averageScore);
                totalGrade1.setTotalScore(yearGrade.totalScore);
                totalGrade1.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
