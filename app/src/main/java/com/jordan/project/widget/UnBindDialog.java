package com.jordan.project.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.jordan.project.R;


public class UnBindDialog extends Dialog {
    Context context;

    public UnBindDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;

    }

    public UnBindDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setCancelable(false);// 设置点击屏幕Dialog不消失
        this.setContentView(R.layout.un_bind_dialog);

    }

}