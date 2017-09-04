package com.jordan.commonlibrary.data;

import com.jordan.commonlibrary.config.CommonSystemConfig;
import com.safari.httplibs.utils.data.JsonInfo;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/1.
 */

public final class ViewMediasInfo extends JsonInfo{
    private String mAllIds;

    public ViewMediasInfo(String[] view_ids) {
        if (null == view_ids || view_ids.length <= 0) {
            throw new RuntimeException("input is invalid");
        }
        StringBuffer ids_buffer = new StringBuffer();
        int ids_length = view_ids.length;
        for (int index = 0; index < view_ids.length; index ++) {
            ids_buffer.append(view_ids[index]);
            if (index < ids_length -1) {//this element is not last one
                ids_buffer.append(",");
            }
        }
        mAllIds = ids_buffer.toString();
    }

    public String getAllIds() {
        return mAllIds;
    }


    @Override
    public String toString() {
        return "ViewMediasInfo{" +
                "mAllIds='" + mAllIds + '\'' +
                '}';
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject view_medias_obj = new JSONObject();
            view_medias_obj.put(CommonSystemConfig.ViewMediasMessageConfig.JSON_REQUEST_KEY_IDS, mAllIds);
            String result_json_str = view_medias_obj.toString();
            return result_json_str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
