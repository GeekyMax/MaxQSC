package com.geekymax.maxschedule.activity;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.geekymax.maxschedule.database.Course;
import com.geekymax.maxschedule.database.Deadline;
import com.geekymax.maxschedule.R;
import com.geekymax.maxschedule.database.TimePlace;
import com.geekymax.maxschedule.database.UserSession;
import com.geekymax.maxschedule.fragment.GradeFragment;
import com.geekymax.maxschedule.fragment.HomeFragment;
import com.geekymax.maxschedule.fragment.MyCoursesFragment;
import com.geekymax.maxschedule.fragment.TimeLineFragment;
import com.geekymax.maxschedule.fragment.CoursesTableFragment;
import com.geekymax.maxschedule.qsc.Courses;
import com.geekymax.maxschedule.qsc.Exams;
import com.geekymax.maxschedule.qsc.Grades;
import com.geekymax.maxschedule.qsc.QscService;
import com.geekymax.maxschedule.view.AddHomeworkDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private ConstraintLayout outerLayout;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private Toolbar toolbar;
    public TimeLineFragment timeLineFragment = new TimeLineFragment();
    public CoursesTableFragment timeTableFragment = new CoursesTableFragment();
    private ConstraintLayout detailBackground;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton deadlineButton;
    private FloatingActionButton homeworkButton;
    //init add event
    private MaterialEditText titleEditText;
    private MaterialEditText detailEditText;
    private ConstraintLayout addEventLayout;
    private Calendar calendar;
    private final static String TAG = "TestActivity";
    private ImageView calendarImage;
    private ImageView alarmImage;
    private ImageView locationImage;
    private ImageView cancelImage;
    private ImageView labelImage;
    private AlertDialog locationDialog;
    private String additionalText = "";
    private TextView additionalTextView;
    private String dateText = "";
    private String locationText = "";
    private String timeText = "";
    private Date pickedDate = new Date();
    private Deadline deadline;
    private FancyButton saveButton;
    private Fragment fragment = null;
    private HomeFragment homeFragment = null;
    private MyCoursesFragment myCoursesFragment = null;
    private FragmentManager fragmentManager;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ConstraintLayout accountBtn;
    private final static int LOAD_DONE = 0;
    private final static int RELOAD_FROM_QSC = 1;
    private String userId = null;
    private String userPassword = null;
    private ProgressDialog loadingDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RELOAD_FROM_QSC:
                    loadData(userId, userPassword);
                    break;
                case LOAD_DONE:
                    loadingDialog.dismiss();
                    break;
            }
        }
    };
    private ConstraintLayout headerView;

    private TextView headerIdText;
    private TextView headerMajorText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = (ConstraintLayout) (navigationView.inflateHeaderView(R.layout.nav_header_main));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        initViews();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view
        switch (item.getItemId()) {
            case R.id.home:
                toolbar.setTitle("首页");
                floatingActionMenu.showMenu(false);
                homeFragment = new HomeFragment();
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, homeFragment);
                transaction.commit();
                break;
            case R.id.courses:
                toolbar.setTitle("本学期课程");
                MenuItem menuItem = toolbar.getMenu().findItem(R.id.switch_semester);
                menuItem.setVisible(true);
                menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                SubMenu subMenu = menuItem.getSubMenu();
                subMenu.add(0,0,0,"subitem");
                subMenu.add(0,0,0,"subitem");
                subMenu.add(0,0,0,"subitem");
                subMenu.add(0,0,0,"subitem");
                subMenu.add(0,0,0,"subitem");

                floatingActionMenu.hideMenu(false);
                myCoursesFragment = new MyCoursesFragment();
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                transaction1.replace(R.id.main_container, myCoursesFragment);
                transaction1.commit();
                break;
            case R.id.grades:

                toolbar.setTitle("我的成绩");
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction2 = fragmentManager.beginTransaction();
                transaction2.replace(R.id.main_container,new GradeFragment());
                transaction2.commit();
                break;
            case R.id.zjuwlan:

                ConnectDialog connectDialog = new ConnectDialog(this);
                connectDialog.show();
                break;
            case R.id.setting:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initViews() {

        //init the loading dialog
        loadingDialog = new ProgressDialog(MainActivity.this);
        loadingDialog.setMessage("loading data..");
        fragmentManager = getSupportFragmentManager();
        initFloatButtons();

        //init the nav header
        accountBtn = (ConstraintLayout) (headerView.findViewById(R.id.account_btn));
        accountBtn.setOnClickListener(this);
        headerMajorText = (TextView)(headerView.findViewById(R.id.major));
        headerIdText = (TextView)(headerView.findViewById(R.id.id_text));
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","");
        headerIdText.setText(userId);
        headerMajorText.setText("浙江大学");

        //load the home fragment
        homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, homeFragment);
        transaction.commit();
    }


    private void initFloatButtons() {
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.fam);
        deadlineButton = (FloatingActionButton) findViewById(R.id.ddl_button);
        homeworkButton = (FloatingActionButton) findViewById(R.id.homework_button);
        homeworkButton.setOnClickListener(this);
        deadlineButton.setOnClickListener(this);
    }

    private void loadData(String id, String password) {
        loadingDialog.show();
        QscService.getUserSession(id, password, new QscService.SessionCallBack() {
            @Override
            public void onFailure(Exception e) {
                loadingDialog.dismiss();
                new AlertDialog.Builder(MainActivity.this)
                        .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                handler.sendEmptyMessage(RELOAD_FROM_QSC);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("error")
                        .setMessage("fail to download your information from the internet,please try again!")
                        .show();

            }

            @Override
            public void callBack(UserSession session) {
                QscService.getResources(session, new QscService.ResourcesCallBack() {
                    @Override
                    public void onFailure(Exception e) {

                    }

                    @Override
                    public void coursesCallBack(Courses courses) {
                        QscService.handleCourses(courses, userId);
                        loadingDialog.dismiss();

                    }

                    @Override
                    public void gradeCallBack(Grades grades) {

                    }

                    @Override
                    public void examsCallBack(Exams exams) {

                    }

                    @Override
                    public void allCallBack(Courses courses, Grades grades, Exams exams) {

                    }
                });
            }
        });
    }


    @Override
    public void onClick(View v) {
        {
            switch (v.getId()) {
                case R.id.ddl_button:
                    floatingActionMenu.hideMenu(true);
                    addEventLayout.setVisibility(View.VISIBLE);
                    ObjectAnimator animator = ObjectAnimator.ofFloat(addEventLayout, "alpha", 0f, 1f);
                    animator.setDuration(300);
                    animator.start();
                    break;
                case R.id.homework_button:
                    initAddDeadlineDialog();
                    break;

                case R.id.account_btn:
                    PopupMenu popupMenu = new PopupMenu(MainActivity.this, accountBtn);
                    popupMenu.getMenuInflater().inflate(R.menu.account_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            SharedPreferences.Editor editor =getSharedPreferences("data",MODE_PRIVATE).edit();
                            editor.putBoolean("login",false);
                            editor.apply();
                            DataSupport.deleteAll(TimePlace.class);
                            DataSupport.deleteAll(Course.class);
                            finish();
                            return true;
                        }
                    });
                    popupMenu.show();
                    break;
                default:
            }
        }
    }
    private void initAddDeadlineDialog() {
        floatingActionMenu.close(true);
        Intent intent = new Intent(MainActivity.this, AddDeadlineActivity.class);
        startActivityForResult(intent, 1);
    }

    public void loadList() {
        timeLineFragment.loadList();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    loadList();
                }
        }
    }
}
