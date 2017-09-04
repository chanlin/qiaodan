package com.jordan.project.content;

import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

public class BluetoothObserver extends ContentObserver {
    private static String TAG = "BluetoothObserver";
    public static final int DATABASE_UPDATE = 1004;
    private Handler mHandler;

    public BluetoothObserver(Handler handler) {
        super(handler);
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        Log.i(TAG, "BluetoothObserver");
        super.onChange(selfChange);
        try {
            mHandler.sendEmptyMessage(DATABASE_UPDATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
