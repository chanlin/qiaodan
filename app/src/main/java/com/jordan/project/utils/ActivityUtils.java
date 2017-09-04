package com.jordan.project.utils;

import android.app.Activity;
import android.content.Intent;

import com.jordan.project.config.ActivityActionConfig;

import java.io.ByteArrayOutputStream;

/**
 * Created by icean on 2017/1/21.
 */

public final class ActivityUtils {

    public static boolean startLoginActivitySafe(Activity activity, String user_phone, String user_password) {
        Intent start_login_activity_intent = new Intent(ActivityActionConfig.ACTION_TO_LOGIN);
        start_login_activity_intent.putExtra(ActivityActionConfig.KEY_LOGIN_USER_NAME, user_phone);
        start_login_activity_intent.putExtra(ActivityActionConfig.KEY_LOGIN_USER_PASSWORD, user_password);
        try {
            activity.startActivityForResult(start_login_activity_intent, ActivityActionConfig.REQUEST_CODE_LOGIN);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean startRegisterActivitySafe(Activity activity) {
        Intent start_register_activity_intent = new Intent(ActivityActionConfig.ACTION_TO_REGISTER);
        try {
            activity.startActivityForResult(start_register_activity_intent, ActivityActionConfig.REQUEST_CODE_REGISTER);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean startForgetPasswordActivitySafe(Activity activity, String phone_number) {
        Intent start_fp_activity_intent = new Intent(ActivityActionConfig.ACTION_TO_FORGET_PASSWORD);
        start_fp_activity_intent.putExtra(ActivityActionConfig.KEY_LOGIN_USER_NAME, phone_number);
        try {
            activity.startActivityForResult(start_fp_activity_intent, ActivityActionConfig.REQUEST_CODE_FORGET_PASSWORD);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getInputStrInfo(Intent input_intent, String input_key) {
        if (input_intent.hasExtra(input_key)) {
            String input_value = input_intent.getStringExtra(input_key);
            return input_value;
        } else {
            return null;
        }
    }

    public static long getInputLongInfo(Intent input_intent, String input_key) {
        if (input_intent.hasExtra(input_key)) {
            long input_value = input_intent.getLongExtra(input_key, -1L);
            return input_value;
        } else {
            return -1L;
        }
    }

    public static String getSNFromBroadcastRecord(byte[] broadcast_record){
        String a = bytesToHex(broadcast_record);
        boolean ifFF = a.contains("FF");
        if(ifFF){
            int index = a.indexOf("FF");
            int leng = Integer.parseInt(a.substring(index-2, index), 16);
            a = a.substring(index+2, index+2*leng);
            System.out.println(a);
        }else{
            return "";
        }
        String snAndmac = pullBrocast(a);
        return snAndmac;
    }

    private static final String hexString = "0123456789ABCDEF";
    private static final char[] hexArray = hexString.toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static String pullBrocast(String a){
        System.out.println(a);
        //415330313735313845434730303034810080CAEA80
        String str1 = a.substring(0,30);
        String str  = decode(str1);//将16进制数字解码成字符串,适用于所有字符
        System.out.println(str);
        String mac = exchange(a.substring(30));//每个16进制字节（2个字符串）倒叙一下 转换
        System.out.println(mac);
        return str+"/"+mac;
    }

    private static String decode(String bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
        return new String(baos.toByteArray());
    }

    private static String exchange(String str){
        StringBuilder a= new StringBuilder();
        a.append(str.substring(10, 12)).append(":");
        a.append(str.substring(8, 10)).append(":");
        a.append(str.substring(6, 8)).append(":");
        a.append(str.substring(4, 6)).append(":");
        a.append(str.substring(2, 4)).append(":");
        a.append(str.substring(0, 2));
        return a.toString();
    }
}
