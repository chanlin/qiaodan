package com.jordan.commonlibrary.utils;

import android.util.Log;

import com.jordan.commonlibrary.config.CommonSystemConfig;
import com.jordan.commonlibrary.data.BluetoothBindInfo;
import com.jordan.commonlibrary.data.BluetoothListInfo;
import com.jordan.commonlibrary.data.BluetoothUnBindInfo;
import com.jordan.commonlibrary.data.BluetoothUpdateInfo;
import com.jordan.commonlibrary.data.CheckCodeInfo;
import com.jordan.commonlibrary.data.CheckVersionInfo;
import com.jordan.commonlibrary.data.SendCodeInfo;
import com.jordan.commonlibrary.data.UploadMediaInfo;
import com.jordan.commonlibrary.data.ViewMediasInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by icean on 2017/1/27.
 */

public class CommonSystemUtils {

    public static String createUploadMediaMainRequest(String type, String file_full_path) {
        UploadMediaInfo current_file_obj = new UploadMediaInfo(type, file_full_path);
        String upload_file_obj = current_file_obj.toJsonStr();

        Log.i("Photo","current_file_obj.toString():"+current_file_obj.toString());
        return upload_file_obj;
    }

    public static String createUploadMediasMainRequest(ArrayList<UploadMediaInfo> upload_media_files) {
        if (null != upload_media_files && upload_media_files.size() > 0) {
            try {
                JSONArray file_json_array = new JSONArray();
                for (UploadMediaInfo current_file_obj : upload_media_files){
                    file_json_array.put(current_file_obj.toJsonStr());
                }
                JSONObject all_file_json_obj = new JSONObject();
                all_file_json_obj.put(CommonSystemConfig.MediasMessageConfig.JSON_MEDIA_ROOT, file_json_array);
                String all_file_json_str = all_file_json_obj.toString();
                return all_file_json_str;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String createViewMediasMainRequest(String[] view_ids) {
        ViewMediasInfo view_medias_info = new ViewMediasInfo(view_ids);
        String view_medias_info_str = view_medias_info.toJsonStr();
        return view_medias_info_str;
    }

    public static String createCheckVersionMainRequest(String device_type) {
        CheckVersionInfo info = new CheckVersionInfo(device_type);
        String check_version_str = info.toJsonStr();
        return  check_version_str;
    }

    public static String createGetCodeMainRequest(String user_account, String apply_type, String genre) {
        SendCodeInfo info = new SendCodeInfo(user_account, apply_type, genre);
        String get_code_str = info.toJsonStr();
        return get_code_str;
    }

    public static String createCheckCodeMainRequest(String user_account, String apply_type, String code) {
        CheckCodeInfo info = new CheckCodeInfo(user_account, apply_type, code);
        String check_code_str = info.toJsonStr();
        return check_code_str;
    }
    public static String createBluetoothBindMainRequest(String sn, String mac) {
        BluetoothBindInfo info = new BluetoothBindInfo(sn, mac);
        String bluetooth_bind_str = info.toJsonStr();
        return bluetooth_bind_str;
    }
    public static String createBluetoothUpdateMainRequest(String id, String name) {
        BluetoothUpdateInfo info = new BluetoothUpdateInfo(id, name);
        String bluetooth_bind_str = info.toJsonStr();
        return bluetooth_bind_str;
    }
    public static String createBluetoothUnBindMainRequest(String ids) {
        BluetoothUnBindInfo info = new BluetoothUnBindInfo(ids);
        String bluetooth_un_bind_str = info.toJsonStr();
        return bluetooth_un_bind_str;
    }
    public static String createBluetoothListMainRequest() {
        BluetoothListInfo info = new BluetoothListInfo();
        String bluetooth_list_str = info.toJsonStr();
        return bluetooth_list_str;
    }
}
