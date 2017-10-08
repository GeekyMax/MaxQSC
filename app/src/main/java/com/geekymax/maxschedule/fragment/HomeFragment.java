package com.geekymax.maxschedule.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekymax.maxschedule.R;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private final static String TAG = "HomeFragment";
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private FloatingActionMenu floatingActionMenu;
    private TimeLineFragment timeLineFragment;
    private CoursesTableFragment coursesTableFragment;
    private List<Fragment> fragments =new ArrayList<>();
    public HomeFragment() {
        super();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        try{
            Log.d(TAG, "initViews: ");
            timeLineFragment = new TimeLineFragment();
            coursesTableFragment = new CoursesTableFragment();
            fragments.add(timeLineFragment);
            fragments.add(coursesTableFragment);
            floatingActionMenu = (FloatingActionMenu) getActivity().findViewById(R.id.fam);
            mTabLayout = (TabLayout)getActivity().findViewById(R.id.tab_layout);
            mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
            mTabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
            List<String> titles = new ArrayList<>();
            titles.add("今日");
            titles.add("课程表");
            mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(),fragments,titles);
            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 1) {
                        floatingActionMenu.hideMenu(true);
                    } else {
                        floatingActionMenu.showMenu(true);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            mViewPager.setAdapter(mPagerAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{
        private List<String> tabTitles = new ArrayList<>();
        private List<Fragment> fragments = new ArrayList<>();
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }

        public ScreenSlidePagerAdapter(FragmentManager fm ,List<Fragment> fragments,List<String> titles) {
            super(fm);
            this.fragments = fragments;
            this.tabTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }


        @Override
        public int getCount() {
            return tabTitles.size();
        }
    }
}