package com.geekymax.maxschedule.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.geekymax.maxschedule.R;
import com.geekymax.maxschedule.database.Course;
import com.geekymax.maxschedule.database.Deadline;
import com.geekymax.maxschedule.database.Grade;
import com.geekymax.maxschedule.database.TimePlace;
import com.geekymax.maxschedule.database.TotalGrade;
import com.geekymax.maxschedule.database.UserSession;
import com.geekymax.maxschedule.qsc.Courses;
import com.geekymax.maxschedule.qsc.Exams;
import com.geekymax.maxschedule.qsc.Grades;
import com.geekymax.maxschedule.qsc.QscService;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.litepal.crud.DataSupport;

import mehdi.sakout.fancybuttons.FancyButton;

public class LoginActivity extends AppCompatActivity {
    private Switch rememberSwitch;
    private MaterialEditText idEdit;
    private MaterialEditText passwordEdit;
    private FancyButton loginBtn;
    private ProgressDialog progressDialog;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        CardView cardView = (CardView) findViewById(R.id.card_view);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1.5f, 1f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        imageView.setAnimation(scaleAnimation);
        scaleAnimation.setDuration(4000);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.start();
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, -1200f, 0f);
        translateAnimation.setStartOffset(2000);
        translateAnimation.setDuration(1000);
        translateAnimation.setFillAfter(true);
        cardView.setAnimation(translateAnimation);
        translateAnimation.start();
        initViews();

    }

    private void initViews() {
        // firstly clear the database
        DataSupport.deleteAll(Course.class);
        DataSupport.deleteAll(TotalGrade.class);
        DataSupport.deleteAll(Grade.class);
        DataSupport.deleteAll(TimePlace.class);
        DataSupport.deleteAll(UserSession.class);
        DataSupport.deleteAll(Deadline.class);
        //
        rememberSwitch = (Switch) findViewById(R.id.remember_switch);
        idEdit = (MaterialEditText) findViewById(R.id.id);
        passwordEdit = (MaterialEditText) findViewById(R.id.password);
        loginBtn = (FancyButton) findViewById(R.id.login);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("login...");
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                id = idEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                QscService.getUserSession(id, password, new QscService.SessionCallBack() {
                    @Override
                    public void callBack(final UserSession session) {
                        if (session.getSessionId() == null || session.getSessionId().equals("null") || session.getSessionId().equals("")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    onLogFailed();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    onLogSuccessFully(session);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                        onConnectFailure();
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private void onLogSuccessFully(UserSession session) {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putBoolean("login", true);
        editor.putString("userId", session.getUserId());
        editor.apply();
        QscService.getResources(session, new QscService.ResourcesCallBack() {
            @Override
            public void onFailure(Exception e) {
                onConnectFailure();
            }

            @Override
            public void coursesCallBack(Courses courses) {

            }

            @Override
            public void gradeCallBack(Grades grades) {
            }

            @Override
            public void examsCallBack(Exams exams) {

            }

            @Override
            public void allCallBack(Courses courses, Grades grades, Exams exams) {
                QscService.handleCourses(courses,id);
                QscService.handleGrade(grades);
                progressDialog.dismiss();
                finish();
            }
        });
    }

    private void onLogFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "学号或密码错误，请重试", Toast.LENGTH_LONG).show();
                passwordEdit.setText("");
                passwordEdit.requestFocus();
            }
        });

    }

    private void onConnectFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "连接失败，请稍后重试", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
