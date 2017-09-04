package com.jordan.commonlibrary.data;

import com.jordan.commonlibrary.config.CommonSystemConfig;
import com.safari.httplibs.utils.CommonUtils;
import com.safari.httplibs.utils.data.JsonInfo;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by icean on 2017/2/1.
 */

public final class UploadMediaInfo extends JsonInfo{
    private String mFileType, mFullFilePath;
    private String mFileName, mFileContent;

    public UploadMediaInfo(String file_type, String full_file_path) {
        mFullFilePath = full_file_path;
        File current_file = new File(mFullFilePath);
        if (!current_file.exists())
            throw new RuntimeException("File not exists!");

        mFileType = file_type;
        mFileName = current_file.getName();
        mFileContent = CommonUtils.encodeToBase64(mFullFilePath);
    }

    public String getFileType() {
        return mFileType;
    }

    public String getFullFilePath() {
        return mFullFilePath;
    }

    public String getFileName() {
        return mFileName;
    }

    public String getFileContent() {
        return mFileContent;
    }

    @Override
    public String toString() {
        return "UploadMediaInfo{" +
                "mFileType='" + mFileType + '\'' +
                ", mFullFilePath='" + mFullFilePath + '\'' +
                ", mFileName='" + mFileName + '\'' +
                ", mFileContent='" + mFileContent + '\'' +
                '}';
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject file_json_obj = new JSONObject();
            file_json_obj.put(CommonSystemConfig.MediasMessageConfig.JSON_REQUEST_KEY_TYPE, mFileType);
            file_json_obj.put(CommonSystemConfig.MediasMessageConfig.JSON_REQUEST_KEY_NAME, mFileName);
            file_json_obj.put(CommonSystemConfig.MediasMessageConfig.JSON_REQUEST_KEY_FILE_CONTENT, mFileContent);
            String result_json_str = file_json_obj.toString();
            return result_json_str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
