package com.geekymax.maxschedule.adapter;

import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekymax.maxschedule.CircleCheckBox;
import com.geekymax.maxschedule.R;
import com.geekymax.maxschedule.content.CourseStartTime;
import com.geekymax.maxschedule.database.Deadline;
import com.geekymax.maxschedule.database.TimePlace;
import com.geekymax.maxschedule.util.DatabaseUtil;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by max on 2017/9/16.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<TimeLineItem> timeLineItemList = new ArrayList<>();

    int maxId = 0;
    private static final int TODAY = 20;
    private static final int TODAY_COURSE = 21;
    private final static String TAG = "TimeLineAdapter";
    private SimpleDateFormat courseTimeFormat;

    public TimeLineAdapter() {
        super();
        timeLineItemList.clear();
        timeLineItemList.add(new TimeLineItem(TODAY));
        timeLineItemList.add(new TimeLineItem(TODAY_COURSE));
        courseTimeFormat = new SimpleDateFormat("hh:mm");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TodayViewHolder) {
            ((TodayViewHolder) holder).refreshTime();
        } else if (holder instanceof TodayCourseViewHolder){

        }
    }

    @Override
    public int getItemViewType(int position) {
        return timeLineItemList.get(position).itemType;
    }

    @Override
    public int getItemCount() {
        return timeLineItemList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = null;
        switch (viewType) {
            case TODAY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_layout, null, false);
                viewHolder = new TodayViewHolder(view);
                break;
            case TODAY_COURSE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_course_layout, null, false);
                viewHolder = new TodayCourseViewHolder(view);
                break;
            default:
                viewHolder = null;
        }
        return viewHolder;
    }

    public class DeadlineViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView locationTextView;
        private TextView detailTextView;
        private TextView dateTextView;
        private TextView timeTextView;

        public DeadlineViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            locationTextView = (TextView) itemView.findViewById(R.id.place_text_view);
            detailTextView = (TextView) itemView.findViewById(R.id.teacher_text_view);
            dateTextView = (TextView) itemView.findViewById(R.id.date_text_view);
            timeTextView = (TextView) itemView.findViewById(R.id.time_text_view);
        }
    }

    public class SimpleDeadlineViewHolder extends RecyclerView.ViewHolder {
        CircleCheckBox circleCheckBox;
        TextView timeTextView;
        TextView titleTextView;

        public SimpleDeadlineViewHolder(View itemView) {
            super(itemView);
            circleCheckBox = (CircleCheckBox) itemView.findViewById(R.id.auto_connect);
            circleCheckBox.setOnCheckedChangeListener(new CircleCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CircleCheckBox view, boolean isChecked) {

                }
            });
            timeTextView = (TextView) itemView.findViewById(R.id.time_text_view);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
        }
    }

    public class TodayViewHolder extends RecyclerView.ViewHolder {
        private static final int REFRESH_TIME = 1;
        private static final int RESET_TEXT = 2;
        private String mTimeText;
        private android.os.Handler handler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case REFRESH_TIME:
                        refreshTime();
                        break;
                    case RESET_TEXT:
                        timeText.setText(mTimeText);
                        break;
                }
            }
        };
        private View view;
        private ImageView plantImage;
        private ImageView wifiImage;
        private ImageView homeworkImage;
        private TextView titleText;
        private TextView subtitleText;
        private TextView timeText;
        private TextView timeHelperText;
        private TextView subHelperText;

        private class CourseTime {
            public TimePlace timePlace;
            public Calendar startTime;
            public Calendar endTime;
        }

        private List<CourseTime> courseTimeList = new ArrayList<>();

        public TodayViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "TodayViewHolder: ");
            view = itemView;
            plantImage = (ImageView) itemView.findViewById(R.id.tree);
            homeworkImage = (ImageView) itemView.findViewById(R.id.homework);
            wifiImage = (ImageView) itemView.findViewById(R.id.wifi);
            timeText = (TextView) itemView.findViewById(R.id.time);
        }

        public void refreshTime() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Calendar calendar = Calendar.getInstance();
                    int dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + 6) % 7;
                    List<TimePlace> timePlaces = DataSupport
                            .where("dayOfWeek=?", "" + dayOfWeek)
                            .order("startCourse")
                            .find(TimePlace.class);
                    Calendar nextCourseStartTime = Calendar.getInstance();
                    for (TimePlace timePlace : timePlaces) {
                        try {
                            Calendar startTime = Calendar.getInstance();
                            startTime.setTime(courseTimeFormat.parse(CourseStartTime.CLASS_START_TIME[timePlace.getStartCourse() - 1]));
                            if (startTime.after(Calendar.getInstance())) {
                                nextCourseStartTime = startTime;
                                int hour = nextCourseStartTime.get(Calendar.HOUR) - calendar.get(Calendar.HOUR);
                                int minute = nextCourseStartTime.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE);
                                mTimeText = ("" + hour + ":" + minute);
                                handler.sendEmptyMessage(RESET_TEXT);
                                break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    handler.sendEmptyMessageDelayed(REFRESH_TIME, 1000);
                }
            }).start();
        }

    }

    public class TodayCourseViewHolder extends RecyclerView.ViewHolder {
        LinearLayout courseContainer;

        public TodayCourseViewHolder(View itemView) {
            super(itemView);
            courseContainer = (LinearLayout) (itemView.findViewById(R.id.course_container));
            for (int i = 0; i < 5; i++) {
                View view = LayoutInflater.from(itemView.getContext()).inflate(R.layout.today_course_item_layout, null, false);
                courseContainer.addView(view);
            }

        }
    }

    public class TimeLineItem {
        public int itemType;
        public Deadline deadLine;

        public TimeLineItem(int type) {
            itemType = type;
        }

        public TimeLineItem() {

        }
    }

    public void loadList() {

    }

    public void refreshList() {
        List<Deadline> list = DatabaseUtil.getAllDeadline();
        for (Deadline deadline : list) {
        }
    }
}
