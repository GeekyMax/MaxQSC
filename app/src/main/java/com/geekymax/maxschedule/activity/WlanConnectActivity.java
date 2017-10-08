package com.geekymax.maxschedule.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.geekymax.maxschedule.CircleCheckBox;
import com.geekymax.maxschedule.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import mehdi.sakout.fancybuttons.FancyButton;

public class WlanConnectActivity extends Activity implements View.OnClickListener {
    private ImageView cancelImage;
    private MaterialEditText idEdit;
    private MaterialEditText passwordEdit;
    private FancyButton connectBtn;
    private CircleCheckBox autoConnectCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wlan_connect);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        initViews();
    }

    private void initViews() {
        cancelImage = (ImageView) findViewById(R.id.cancel);
        idEdit = (MaterialEditText) findViewById(R.id.id);
        passwordEdit = (MaterialEditText) findViewById(R.id.password);
        connectBtn = (FancyButton) findViewById(R.id.connect);
        autoConnectCheckBox = (CircleCheckBox) findViewById(R.id.auto_connect);
        connectBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect:
                connect();
                break;
            case R.id.cancel:
                finish();
        }
    }

    private void connect() {
        // this is the main function!
    }
}
