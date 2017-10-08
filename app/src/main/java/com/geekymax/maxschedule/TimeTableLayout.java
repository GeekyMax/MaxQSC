package com.geekymax.maxschedule;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by max on 2017/9/15.
 */

public class TimeTableLayout extends FrameLayout {
    private final static String TAG = "timeTableLayout";
    int mColumnCount = 7;
    int mRowCount = 13;
    float mRowHeight = 150;
    float mColumnWidth = 0;

    public TimeTableLayout(Context context) {
        super(context);
    }

    public TimeTableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeTableLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void addItem(View view, int row, int column, int rowSpan, int columnSpan) {
        updateSize();
        Log.d(TAG, "addItem: " + mRowHeight);
        ViewGroup.LayoutParams layoutParams =
                new LinearLayoutCompat.LayoutParams(
                        (int) mColumnWidth * columnSpan, (int) mRowHeight * rowSpan, 1);
        Log.d(TAG, "addItem: "+(int) mRowHeight * rowSpan);
        view.setY(row*mRowHeight);
        view.setX(column*mColumnWidth);
        addView(view, layoutParams);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void updateSize() {
        int width =  getResources().getDisplayMetrics().widthPixels;
        mColumnWidth = width * 14 / 15 / mColumnCount;
        mRowHeight = getResources().getDimensionPixelSize(R.dimen.class_table_item_height);
    }
}
