package com.jordan.project.utils;

import android.content.Context;

import com.jordan.project.JordanApplication;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.BluetoothData;

import java.util.ArrayList;

public class SettingSharedPerferencesUtil {

    public static final String FRIST_LOGIN_USERNAME_PATH = "filepath_login_username_";
    private static final String FRIST_LOGIN_USERNAME_CONFIG = "config_login_username_";
    public static final String FRIST_LOGIN_PASSWORD_PATH = "filepath_login_password_";
    private static final String FRIST_LOGIN_PASSWORD_CONFIG = "config_login_password_";
    public static final String FRIST_LOGIN_VIP_ID_PATH = "filepath_login_vip_id_";
    private static final String FRIST_LOGIN_VIP_ID_CONFIG = "config_login_vip_id_";

    public static final String EDIT_LOGIN_USERNAME_PATH = "filepath_edit_login_username_";
    private static final String EDIT_LOGIN_USERNAME_CONFIG = "config_edit_login_username_";

    public static final String OVER_MOTION_TIME_PATH = "filepath_over_motion_time_";
    private static final String OVER_MOTION_TIME_CONFIG = "config_over_motion_time_";

    public static final String REMARK_PASSWORD_PATH = "filepath_remark_password_";
    private static final String REMARK_PASSWORD_CONFIG = "config_remark_password_";

    public static final String FRIST_START_PATH = "filepath_frist_start_";
    private static final String FRIST_START_CONFIG = "config_frist_start_";

    public static final String FRIST_STADIUM_PATH = "filepath_frist_stadium_";
    private static final String FRIST_STADIUM_CONFIG = "config_frist_stadium_";

    public static final String FRIST_LOGIN_PATH = "filepath_frist_login_";
    private static final String FRIST_LOGIN_CONFIG = "config_frist_login_";

    public static final String BLUTOOTH_FOOT_PATH = "filepath_bluetooth_foot_";
    private static final String BLUTOOTH_FOOT_CONFIG = "config_bluetooth_foot_";

    public static final String BALL_TYPE_PATH = "filepath_ball_type_";
    private static final String BALL_TYPE_CONFIG = "config_ball_type_";

    public static final String CHOIES_BLUETOOT_PATH = "filepath_choies_bluetooth";
    private static final String CHOIES_BLUETOOT_CONFIG = "config_choies_bluetooth";

    public static boolean SetChoiesBluetoothValue(Context context, String username, String choiesBluetooth) {
        if (choiesBluetooth.contains(":"))
            choiesBluetooth = choiesBluetooth.replace(":", "");
        return PrefsHelper.save(context, CHOIES_BLUETOOT_CONFIG + username, choiesBluetooth, CHOIES_BLUETOOT_PATH + username);
    }

    public static String GetChoiesBluetoothConfig(Context context, String username) {
        String choiesBluetooth = "";
        try {
            choiesBluetooth = PrefsHelper.read(context, CHOIES_BLUETOOT_CONFIG + username, CHOIES_BLUETOOT_PATH + username);
            if (choiesBluetooth.contains(":"))
                choiesBluetooth = choiesBluetooth.replace(":", "");
            LogUtils.showLog("bluetoothfoot", "GetChoiesBluetoothConfig" + choiesBluetooth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (choiesBluetooth.equals("")) {
            //直接查看数据库第一条
            ArrayList<BluetoothData> mBluetoothList = DatabaseService.findBluetoothList(
                    JordanApplication.getUsername(context));
            if (mBluetoothList.size() > 0) {
                choiesBluetooth = mBluetoothList.get(0).getSn() + "|" + mBluetoothList.get(0).getMac();
                if (choiesBluetooth.contains(":"))
                    choiesBluetooth = choiesBluetooth.replace(":", "");
                SetChoiesBluetoothValue(context, username, choiesBluetooth);
            }
        }
        return choiesBluetooth;
    }

    public static boolean SetBallTypeValue(Context context, String username, String ballType) {
        return PrefsHelper.save(context, BALL_TYPE_CONFIG + username, ballType, BALL_TYPE_PATH + username);
    }

    public static String GetBallTypeValueConfig(Context context, String username) {
        String balltype = "";
        try {
            balltype = PrefsHelper.read(context, BALL_TYPE_CONFIG + username, BALL_TYPE_PATH + username);
            LogUtils.showLog("bluetoothfoot", "GetBluetoothFootValueConfig" + balltype);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (balltype.equals("")) {
            balltype = "1";
        }
        return balltype;
    }

    public static boolean SetBluetoothFootValue(Context context, String username, String foot) {
        return PrefsHelper.save(context, BLUTOOTH_FOOT_CONFIG + username, foot, BLUTOOTH_FOOT_PATH + username);
    }

    public static String GetBluetoothFootValueConfig(Context context, String username) {
        String foot = "";
        try {
            foot = PrefsHelper.read(context, BLUTOOTH_FOOT_CONFIG + username, BLUTOOTH_FOOT_PATH + username);
            LogUtils.showLog("bluetoothfoot", "GetBluetoothFootValueConfig" + foot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (foot.equals("")) {
            foot = "右脚";
        }
        return foot;
    }

    public static boolean SetFristLoginValue(Context context, String fristlogin) {
        return PrefsHelper.save(context, FRIST_LOGIN_CONFIG, fristlogin, FRIST_LOGIN_PATH);
    }

    public static String GetFristLoginValueConfig(Context context) {
        String fristlogin = "";
        try {
            fristlogin = PrefsHelper.read(context, FRIST_LOGIN_CONFIG, FRIST_LOGIN_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fristlogin;
    }

    public static boolean SetFristStadiumValue(Context context, String friststadium) {
        return PrefsHelper.save(context, FRIST_STADIUM_CONFIG, friststadium, FRIST_STADIUM_PATH);
    }

    public static String GetFristStadiumValueConfig(Context context) {
        String fristStadium = "";
        try {
            fristStadium = PrefsHelper.read(context, FRIST_STADIUM_CONFIG, FRIST_STADIUM_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fristStadium;
    }

    public static boolean SetFristStartValue(Context context, String friststart) {
        return PrefsHelper.save(context, FRIST_START_CONFIG, friststart, FRIST_START_PATH);
    }

    public static String GetFristStartValueConfig(Context context) {
        String friststart = "";
        try {
            friststart = PrefsHelper.read(context, FRIST_START_CONFIG, FRIST_START_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friststart;
    }

    public static boolean SetRemarkPasswordValue(Context context, String remarkpassword) {
        return PrefsHelper.save(context, REMARK_PASSWORD_CONFIG, remarkpassword, REMARK_PASSWORD_PATH);
    }

    public static String GetRemarkPasswordValueConfig(Context context) {
        String remarkpassword = "";
        try {
            remarkpassword = PrefsHelper.read(context, REMARK_PASSWORD_CONFIG, REMARK_PASSWORD_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remarkpassword;
    }


    public static boolean SetOverMotionTimeValue(Context context, String sn, String username, String endTime) {
        return PrefsHelper.save(context, OVER_MOTION_TIME_CONFIG + username + sn, endTime, OVER_MOTION_TIME_PATH + username + sn);
    }

    public static String GetOverMotionTimeValueConfig(Context context, String sn, String username) {
        String endTime = "";
        try {
            endTime = PrefsHelper.read(context, OVER_MOTION_TIME_CONFIG + username + sn, OVER_MOTION_TIME_PATH + username + sn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return endTime;
    }

    public static boolean SetEditLoginUsernameValue(Context context, String username) {
        return PrefsHelper.save(context, EDIT_LOGIN_USERNAME_CONFIG, username, EDIT_LOGIN_USERNAME_PATH);
    }

    public static String GetEditLoginUsernameValueConfig(Context context) {
        String username = "";
        try {
            username = PrefsHelper.read(context, EDIT_LOGIN_USERNAME_CONFIG, EDIT_LOGIN_USERNAME_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }


    public static boolean SetVipIdValue(Context context, String vipID) {
        return PrefsHelper.save(context, FRIST_LOGIN_VIP_ID_CONFIG, vipID, FRIST_LOGIN_VIP_ID_PATH);
    }

    public static String GetVipIdValueConfig(Context context) {
        String vipID = "";
        try {
            vipID = PrefsHelper.read(context, FRIST_LOGIN_VIP_ID_CONFIG, FRIST_LOGIN_VIP_ID_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vipID;
    }

    public static boolean SetLoginUsernameValue(Context context, String username) {
        return PrefsHelper.save(context, FRIST_LOGIN_USERNAME_CONFIG, username, FRIST_LOGIN_USERNAME_PATH);
    }

    public static String GetLoginUsernameValueConfig(Context context) {
        String username = "";
        try {
            username = PrefsHelper.read(context, FRIST_LOGIN_USERNAME_CONFIG, FRIST_LOGIN_USERNAME_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    public static boolean SetPasswordUsernameValue(Context context, String password) {
        return PrefsHelper.save(context, FRIST_LOGIN_PASSWORD_CONFIG, password, FRIST_LOGIN_PASSWORD_PATH);
    }

    public static String GetPasswordUsernameValueConfig(Context context) {
        String password = "";
        try {
            password = PrefsHelper.read(context, FRIST_LOGIN_PASSWORD_CONFIG, FRIST_LOGIN_PASSWORD_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

}
