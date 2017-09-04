package com.jordan.project.widget.slidedatetimepicker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.jordan.project.R;


public class ChooesBallTypeDialog extends Dialog {
    Context context;

    public ChooesBallTypeDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;

    }

    public ChooesBallTypeDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.chooes_ball_type_dialog);

    }

}