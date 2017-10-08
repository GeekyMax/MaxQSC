package com.geekymax.maxschedule.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekymax.maxschedule.database.Deadline;
import com.geekymax.maxschedule.R;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import mehdi.sakout.fancybuttons.FancyButton;

public class AddDeadlineActivity extends Activity implements View.OnClickListener{
    private MaterialEditText titleEditText;
    private MaterialEditText detailEditText;
    private ConstraintLayout addEventLayout;
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
    public static final int SAVED = 1;
    public static final int CANCELED =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deadline);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        initAddItem();
    }
    private void initAddItem() {
        addEventLayout = (ConstraintLayout) findViewById(R.id.add_event_layout);
        addEventLayout.setOnClickListener(this);


        //init add panel
        detailEditText = (MaterialEditText) findViewById(R.id.detail);
        titleEditText = (MaterialEditText) findViewById(R.id.id);
        saveButton = (FancyButton) findViewById(R.id.save_btn);
        calendarImage = (ImageView) findViewById(R.id.calendar);
        alarmImage = (ImageView) findViewById(R.id.alarm);
        cancelImage = (ImageView) findViewById(R.id.cancel);
        locationImage = (ImageView) findViewById(R.id.location);
        labelImage = (ImageView) findViewById(R.id.label_image);

        calendarImage.setOnClickListener(this);
        alarmImage.setOnClickListener(this);
        locationImage.setOnClickListener(this);
        cancelImage.setOnClickListener(this);
        labelImage.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        additionalTextView = (TextView) findViewById(R.id.additional_text);
        additionalTextView.setText(additionalText);
    }
    @Override
    public void onClick(View v) {
        {
            switch (v.getId()) {
                case R.id.save_btn:
                    deadline = new Deadline();
                    deadline.setDate(pickedDate);
                    deadline.setEvent(titleEditText.getText().toString());
                    deadline.setDetail(detailEditText.getText().toString());
                    deadline.setLocation(locationText);
                    deadline.save();
                    dismiss(SAVED);
                    break;
                case R.id.label_image:
                    break;

                case R.id.alarm:
                    break;
                case R.id.calendar:
//                    DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                            calendar.set(Calendar.YEAR, year);
//                            calendar.set(Calendar.MONTH, monthOfYear);
//                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                            SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
//                            dateText = sdf.format(calendar.getTime());
//                            refreshAdditionalText();
//                            calendarImage.setColorFilter(getResources().getColor(R.color.accent));
//
//                        }
//                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//                    datePickerDialog.show();
                    new SingleDateAndTimePickerDialog.Builder(AddDeadlineActivity.this)
                            .defaultDate(pickedDate)
                            .mainColor(getResources().getColor(R.color.accent))
                            .title("截止日期")
                            .bottomSheet()
                            .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                                @Override
                                public void onDisplayed(SingleDateAndTimePicker picker) {
                                    //retrieve the SingleDateAndTimePicker
                                }
                            })
                            .listener(new SingleDateAndTimePickerDialog.Listener() {
                                @Override
                                public void onDateSelected(Date date) {
                                    pickedDate = date;
                                    SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 hh:mm ");
                                    dateText = sdf.format(date);
                                    refreshAdditionalText();
                                    calendarImage.setColorFilter(getResources().getColor(R.color.accent));
                                }
                            }).display();
                    break;
                case R.id.location:
                    Log.d(TAG, "onClick: ");
                    final View view = LayoutInflater.from(this).inflate(R.layout.location_dialog_layout, null, false);
                    final MaterialEditText materialEditText = new MaterialEditText(AddDeadlineActivity.this);
                    materialEditText.setHideUnderline(true);
                    materialEditText.setHint("location");
                    materialEditText.setBaseColor(R.color.primary_text);
                    final AlertDialog.Builder inputDialog =
                            new AlertDialog.Builder(AddDeadlineActivity.this);
                    inputDialog.setView(view);
                    inputDialog.setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    locationDialog.dismiss();
                                }
                            });
                    inputDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    locationText = ((MaterialEditText) view.findViewById(R.id.material_edit_text)).getText().toString();
                                    refreshAdditionalText();
                                    if (!locationText.equals("")) {
                                        locationImage.setColorFilter(getResources().getColor(R.color.accent));
                                    } else {
                                        locationImage.setColorFilter(getResources().getColor(R.color.grey));
                                    }
                                }
                            });
                    locationDialog = inputDialog.show();

                    break;
                case R.id.cancel:
                    dismiss(CANCELED);
                default:
            }
        }
    }
    private void dismiss(int state){
        Intent intent = new Intent();
        intent.putExtra("state",state);
        setResult(RESULT_OK,intent);
        finish();
    }
    private void refreshAdditionalText() {
        additionalText = "";
        if (!dateText.equals("")) {
            additionalText += (dateText + " ");
        }
        if (!timeText.equals("")) {
            additionalText += (timeText + " ");
        }
        if (!locationText.equals("")) {
            additionalText += ("@" + locationText);
        }
        additionalTextView.setText(additionalText);
    }

}
