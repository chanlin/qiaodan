package com.jordan.project.content;

import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

public class MotionDetailObserver extends ContentObserver {
    private static String TAG = "MotionDetailObserver";
    public static final int DATABASE_UPDATE = 1001;
    private Handler mHandler; 

    public MotionDetailObserver(Handler handler) {
        super(handler);
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        Log.i(TAG, "MotionDetailObserver");
        super.onChange(selfChange);
        try {
            mHandler.sendEmptyMessage(DATABASE_UPDATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
