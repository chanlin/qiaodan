package com.jordan.project.content;

import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

public class UserDataObserver extends ContentObserver {
    private static String TAG = "UserDataObserver";
    public static final int DATABASE_UPDATE = 1002;
    private Handler mHandler;

    public UserDataObserver(Handler handler) {
        super(handler);
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        Log.i(TAG, "UserDataObserver");
        super.onChange(selfChange);
        try {
            mHandler.sendEmptyMessage(DATABASE_UPDATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
