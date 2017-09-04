package com.jordan.project.content;

import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

public class PlayBallObserver extends ContentObserver {
    private static String TAG = "PlayBallObserver";
    public static final int DATABASE_UPDATE = 1003;
    private Handler mHandler;

    public PlayBallObserver(Handler handler) {
        super(handler);
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        Log.i(TAG, "PlayBallObserver");
        super.onChange(selfChange);
        try {
            mHandler.sendEmptyMessage(DATABASE_UPDATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
