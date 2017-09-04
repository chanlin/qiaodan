package com.jordan.project.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.jordan.project.JordanApplication;
import com.jordan.project.activities.ExceptionShowActivity;

/**
 * Created by 昕 on 2017/6/16.
 */
public class UnceHandler implements Thread.UncaughtExceptionHandler {
            private Thread.UncaughtExceptionHandler mDefaultHandler;
            public static final String TAG = "UnceHandler";
            JordanApplication application;

            public UnceHandler(JordanApplication application){
                 //获取系统默认的UncaughtException处理器
                 mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
                 this.application = application;
            }

            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
//                if(!handleException(ex) && mDefaultHandler != null){
//                    //如果用户没有处理则让系统默认的异常处理器来处理
//                    mDefaultHandler.uncaughtException(thread, ex);
//                }else{
                    try{
                        Thread.sleep(2000);
                        LogUtils.showLog("uncaughtException","Thread.sleep(2000)");
                    }catch (InterruptedException e){
                        LogUtils.showLog("uncaughtException","InterruptedException");
                    }
                    LogUtils.showLog("uncaughtException","startActivity");
                    LogUtils.showLog("uncaughtException","uncaughtException:"+Log.getStackTraceString(ex));

                    FileUtils.writeLog(TAG,Log.getStackTraceString(ex));
                    //崩溃后弹日志
                    Intent intent = new Intent(application.getApplicationContext(), ExceptionShowActivity.class);
                    intent.putExtra("exception", Log.getStackTraceString(ex));
                    //崩溃后重启
                    //Intent intent = new Intent(application.getApplicationContext(), NavigationActivity.class);
                    PendingIntent restartIntent = PendingIntent.getActivity(
                            application.getApplicationContext(), 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    //退出程序
                    AlarmManager mgr = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                            restartIntent);//1秒钟后重启应用
                    application.finishActivity();
                    LogUtils.showLog("uncaughtException","over");
                }
//            }

            /**
      * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
      *
      * @param ex
      * @return true:如果处理了该异常信息;否则返回false.
      */
            private boolean handleException(Throwable ex) {
                if (ex == null) {
                    return false;
                }
                //使用Toast来显示异常信息
                new Thread(){
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(application.getApplicationContext(), "很抱歉,程序出现异常,即将退出.",
                                Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }.start();
                return true;
            }
        }