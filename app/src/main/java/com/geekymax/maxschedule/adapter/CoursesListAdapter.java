package com.geekymax.maxschedule.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geekymax.maxschedule.R;
import com.geekymax.maxschedule.database.Course;
import com.geekymax.maxschedule.qsc.Courses;
import com.geekymax.maxschedule.qsc.Exams;
import com.geekymax.maxschedule.qsc.Grades;
import com.geekymax.maxschedule.qsc.QscService;
import com.geekymax.maxschedule.database.UserSession;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 2017/9/20.
 */

public class CoursesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int AW = 1;
    private final static int SS = 2;
    public int semesterType = AW;
    private final static int REFRESH_COURSES_LIST = 1;
    private final static int RELOAD_COURSES_FROM_DATABASE = 2;
    private List<Course> courseList = new ArrayList<>();
    private Context mContext;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COURSES_LIST:
                    refreshCourseList();
                    break;
                case RELOAD_COURSES_FROM_DATABASE:
                    loadCourses(semesterType);
            }
        }
    };

    public CoursesListAdapter(Context context) {
        super();
        mContext = context;
        loadCourses(semesterType);

    }

    public void loadCourses(int semesterType) {
        String[] semesterSelected = {"","",""};
        switch (semesterType){
            case SS:
                semesterSelected[0] = "春";
                semesterSelected[1] = "夏";
                semesterSelected[2] = "春夏";
                break;
            case AW:
                semesterSelected[0] = "秋";
                semesterSelected[1] = "冬";
                semesterSelected[2] = "秋冬";
                break;


        }
        String id = mContext.getSharedPreferences("data", Context.MODE_PRIVATE).getString("userId", "");
        List<Course> courses = DataSupport
                .where("studentId = ? and year = ? and (semester = ? or semester= ? or semester = ?)",
                        id,"2017-2018", semesterSelected[0],semesterSelected[1],semesterSelected[2])
                .find(Course.class);


        if (courses.size() != 0) {
            courseList.clear();
            courseList.addAll(courses);
        }
        if (courseList.size() == 0) {
            handler.sendEmptyMessageDelayed(RELOAD_COURSES_FROM_DATABASE, 100);
        } else {
            refreshCourseList();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CoursesViewHolder coursesViewHolder = (CoursesViewHolder) holder;
        Course course = courseList.get(position);
        coursesViewHolder.titleEdit.setText(course.getName());
        coursesViewHolder.teacherEdit.setText(course.getCode() + "|" + course.getTeacher());
        coursesViewHolder.creditEdit.setText(
                "Credit:" + course.getCredit()
                        + "|category:" + (course.getCategory().equals("") ? "无" : course.getCategory()));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_item_layout, null, false);
        return new CoursesViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class CoursesViewHolder extends RecyclerView.ViewHolder {
        private TextView titleEdit;
        private TextView teacherEdit;
        private TextView creditEdit;

        public CoursesViewHolder(View itemView) {
            super(itemView);
            titleEdit = (TextView) (itemView.findViewById(R.id.title_text_view));
            teacherEdit = (TextView) (itemView.findViewById(R.id.teacher_text_view));
            creditEdit = (TextView) (itemView.findViewById(R.id.credit_text_view));

        }
    }

    private void refreshCourseList() {
        notifyDataSetChanged();

    }
}
