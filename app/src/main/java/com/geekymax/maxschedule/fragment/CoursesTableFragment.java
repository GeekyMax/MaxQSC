package com.geekymax.maxschedule.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.geekymax.maxschedule.R;
import com.geekymax.maxschedule.TimeTableLayout;
import com.geekymax.maxschedule.activity.MainActivity;
import com.geekymax.maxschedule.database.TimePlace;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class CoursesTableFragment extends Fragment {
    private static final String TAG = "CoursesTableFragment";
    private static final int SPRING = 1;
    private static final int AUTUMN = 2;
    private static final int SUMMER = 3;
    private static final int WINTER = 4;

    private static final int RELOAD_COURSES_FROM_DATABASE = 0;
    private TimeTableLayout timeTableLayout;
    private FrameLayout background;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int type = AUTUMN;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RELOAD_COURSES_FROM_DATABASE:
                    reloadCoursesFromDatabase(type);
            }
        }
    };

    public CoursesTableFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses_table, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        drawBackground();
        drawClass();

    }

    private void initViews(View v) {
        timeTableLayout = (TimeTableLayout) v.findViewById(R.id.time_table_layout);
        background = (FrameLayout) v.findViewById(R.id.background);
        swipeRefreshLayout = (SwipeRefreshLayout) (v.findViewById(R.id.swipe));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadCoursesFromDatabase(AUTUMN);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void drawBackground() {
        try {
            for (int i = 1; i < 13; i++) {
                View view = new View(this.getContext());
                view.setBackground(getResources().getDrawable(R.color.grey300));
                view.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
                view.setX(0);
                view.setY(getResources().getDimension(R.dimen.class_table_item_height) * i);
                background.addView(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawClass() {
        reloadCoursesFromDatabase(type);
    }

    private void showDetail(int color) {
        MainActivity mainActivity = (MainActivity) getActivity();
    }

    private void reloadCoursesFromDatabase(int semesterType) {
        String[] semesterSelected = {"", ""};
        switch (semesterType) {
            case AUTUMN:
                semesterSelected[0] = "秋";
                semesterSelected[1] = "秋冬";
                break;
            case SPRING:
                semesterSelected[0] = "春";
                semesterSelected[1] = "春夏";
                break;
            case SUMMER:
                semesterSelected[0] = "夏";
                semesterSelected[1] = "春夏";
                break;
            case WINTER:
                semesterSelected[0] = "冬";
                semesterSelected[1] = "秋冬";
                break;

        }
        String id = getContext().getSharedPreferences("data", Context.MODE_PRIVATE).getString("userId", "");
        Log.d("CoursesTable", "reloadCoursesFromDatabase: " + id);
        List<TimePlace> timePlaces = DataSupport.where("studentId=? and startCourse>? and (semester=? or semester=?)",
                id, "0", semesterSelected[0], semesterSelected[1]).find(TimePlace.class);

        if (timePlaces.size() != 0) {
            timeTableLayout.removeAllViews();
            for (TimePlace timePlace : timePlaces) {
                Log.d(TAG, "reloadCoursesFromDatabase: " + timePlace.getStartCourse() + timePlace.getStudentId());
                View view = LayoutInflater.from(this.getContext()).inflate(R.layout.table_item_layout, null, false);
                TextView textView = (TextView) view.findViewById(R.id.text);
                textView.setText(timePlace.getName() + "@" + timePlace.getPlace());
                Log.d("timeplaces", "drawClass: " + timePlace.getName());
                timeTableLayout.addItem(view, timePlace.getStartCourse() - 1, timePlace.getDayOfWeek() - 1,
                        timePlace.getEndCourse() - timePlace.getStartCourse() + 1, 1);
            }
        } else {
            mHandler.sendEmptyMessageDelayed(RELOAD_COURSES_FROM_DATABASE, 200);
        }
    }
}
