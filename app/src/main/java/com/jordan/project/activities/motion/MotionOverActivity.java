package com.jordan.project.activities.motion;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.jordan.project.R;

public class MotionOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_motion_over);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.motion_detail_title);
    }
}
