package com.jordan.project.activities.ble.handle;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;

import com.google.zxing.Result;
import com.jordan.project.activities.ble.view.ViewfinderView;

/**
 * Created by icean on 2017/5/12.
 */

public abstract class CaptureActivity extends Activity implements SurfaceHolder.Callback {

    private ViewfinderView viewfinderView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public abstract void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor);
}
