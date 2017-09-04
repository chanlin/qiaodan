package com.jordan.project.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.jordan.project.R;

public class ExceptionShowActivity extends Activity {
    private TextView mTvException;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_show);
        String exception = getIntent().getStringExtra("exception");
        mTvException = (TextView)findViewById(R.id.tv_exception);
        mTvException.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvException.setText(exception);
    }
}
