package com.geekymax.maxschedule.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.geekymax.maxschedule.R;

/**
 * Created by max on 2017/9/17.
 */

public class AddHomeworkDialog extends Dialog {
    private ImageView cancelImage;
    public AddHomeworkDialog(@NonNull Context context) {
        super(context,R.style.DialogTheme);
    }

    public AddHomeworkDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected AddHomeworkDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view =  inflater.inflate(R.layout.add_homework_layout, null);
        setContentView(view);
        initViews(view);
    }

    private void initViews(View view) {
        setCanceledOnTouchOutside(false);
        cancelImage = (ImageView)view.findViewById(R.id.cancel);
        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getContext().getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = d.widthPixels;
//        lp.height = d.heightPixels;
        dialogWindow.setAttributes(lp);
    }
}
