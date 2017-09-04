package com.jordan.project.utils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by 昕 on 2017/2/23.
 */

public class JsonFalseUtils {

    public static String onlyErrorCodeResult(String json) throws JSONException {
        String errorMsg = null;
        String errorCode = null;
        if(json==null||json.equals("")){
            return "网络请求失败";
        }else {
            JSONObject dataJson = new JSONObject(json);
            errorMsg = dataJson.getString("msg");
            errorCode = dataJson.getString("code");
            return errorMsg;
        }
    }

}
