package com.jordan.project.utils;

import android.app.Activity;
import android.content.Context;

/**
 * Created by 昕 on 2017/5/16.
 */

public class WindosUtils {
    public static int windowsWidth = 0;
    public static void getWindowsWidth(Activity activity){
        windowsWidth=activity.getWindowManager().getDefaultDisplay().getWidth();
    }
}
