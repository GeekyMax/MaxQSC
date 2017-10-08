package com.geekymax.maxschedule.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekymax.maxschedule.adapter.CoursesListAdapter;
import com.geekymax.maxschedule.R;

public class CoursesListFragment extends Fragment{
    private RecyclerView recyclerView;
    private CoursesListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public CoursesListFragment(){
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_courses_list,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view){
        swipeRefreshLayout = (SwipeRefreshLayout)(view.findViewById(R.id.swipe));
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.loadCourses(adapter.semesterType);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CoursesListAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }
}