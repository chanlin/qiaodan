package com.jordan.project.utils;

/**
 * Created by 昕 on 2017/5/4.
 */

public class TimeUtils {
    public static String formatDateByTimeMS(double ms){
        String seconds = "";
        String minutes = "";
        int s = (int) ms;
        int second = s%60;
        int minute = s/60;
        if(second<10){
            seconds = "0"+second;
        }else{
            seconds = String.valueOf(second);
        }
        if(minute<10){
            minutes = "0"+minute;
        }else{
            minutes = String.valueOf(minute);
        }
        return minutes+":"+seconds;
    }
}
