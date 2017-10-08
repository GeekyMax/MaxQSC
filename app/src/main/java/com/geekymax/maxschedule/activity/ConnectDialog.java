package com.geekymax.maxschedule.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.geekymax.maxschedule.CircleCheckBox;
import com.geekymax.maxschedule.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by max on 2017/9/20.
 */

public class ConnectDialog extends Dialog implements View.OnClickListener{
    private Context mContext;
    private View view;
    private ImageView cancelImage;
    private MaterialEditText idEdit;
    private MaterialEditText passwordEdit;
    private FancyButton connectBtn;
    private CircleCheckBox autoConnectCheckBox;

    public ConnectDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);
        mContext = context;
    }

    public ConnectDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view =  inflater.inflate(R.layout.activity_wlan_connect, null);
        this.view = view;
        setContentView(view);
        initViews();
    }
    private void initViews() {
        cancelImage = (ImageView) view.findViewById(R.id.cancel);
        idEdit = (MaterialEditText) view.findViewById(R.id.id);
        passwordEdit = (MaterialEditText) view.findViewById(R.id.password);
        cancelImage.setOnClickListener(this);
        connectBtn = (FancyButton) view.findViewById(R.id.connect);
        autoConnectCheckBox = (CircleCheckBox) view.findViewById(R.id.auto_connect);
        connectBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                dismiss();
                break;
        }
    }
}
