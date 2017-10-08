package com.geekymax.maxschedule.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.geekymax.maxschedule.R;
import com.geekymax.maxschedule.qsc.QscService;

/**
 * Created by max on 2017/9/22.
 */

public class GradeFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout gradeListContainer;

    public GradeFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grade, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = (SwipeRefreshLayout) (view.findViewById(R.id.swipe));
        gradeListContainer = (LinearLayout) (view.findViewById(R.id.grade_list_container));
        for (int i = 0; i < 10; i++) {
            gradeListContainer.addView(LayoutInflater.from(view.getContext()).inflate(R.layout.grade_list_item_layout, null, false));
        }
    }
}
