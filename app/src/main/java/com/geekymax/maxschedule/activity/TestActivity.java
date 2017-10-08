package com.geekymax.maxschedule.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.sax.TextElementListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker;
import com.geekymax.maxschedule.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import devlight.io.library.ArcProgressStackView;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private Calendar calendar;
    private final static String TAG = "TestActivity";
    private ImageView calendarImage;
    private ImageView alarmImage;
    private ImageView locationImage;
    private AlertDialog locationDialog;
    private int bgColors = R.color.primary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initViews();
    }

    private void initViews() {

    }

    @Override
    public void onClick(View v) {

    }
}
