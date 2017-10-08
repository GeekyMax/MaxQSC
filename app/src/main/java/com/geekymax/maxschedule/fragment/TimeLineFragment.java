package com.geekymax.maxschedule.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekymax.maxschedule.R;
import com.geekymax.maxschedule.adapter.TimeLineAdapter;

public class TimeLineFragment extends Fragment {
    private RecyclerView recyclerView;
    private TimeLineAdapter adapter = new TimeLineAdapter();
    private SwipeRefreshLayout swipeRefreshLayout;
    private final static int REFRESH_COMPLETE=1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REFRESH_COMPLETE:
                    swipeRefreshLayout.setRefreshing(false);
            }
        }
    };
    public TimeLineFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_line, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View v){
        swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE,2000);
            }
        });
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accent));
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    public void loadList(){
        adapter.loadList();
    }
}
