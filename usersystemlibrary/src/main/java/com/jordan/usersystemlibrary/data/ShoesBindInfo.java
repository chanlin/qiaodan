package com.jordan.usersystemlibrary.data;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class ShoesBindInfo extends JsonInfo {
    private String mType;
    private String mShoesId;
    private String mCode;
    private String mName;
    private String mPrice;
    private String mColor;
    private String mSize;
    private String mStyle;
    private String mPicture;
    private String mBuyTime;

    public ShoesBindInfo(String type,String shoesId,String code,String name,
                         String price,String color,String size,String style,
                         String picture,String buyTime) {
        mType=type;
        mShoesId=shoesId;
        mCode=code;
        mName=name;
        mPrice=price;
        mColor=color;
        mSize=size;
        mStyle=style;
        mPicture=picture;
        mBuyTime=buyTime;
    }



    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.GetShoesBindConfig.JSON_REQUEST_PAGE_TYPE,mType);
            json_object.put(UserSystemConfig.GetShoesBindConfig.JSON_REQUEST_PAGE_SHOES_ID, mShoesId);
            json_object.put(UserSystemConfig.GetShoesBindConfig.JSON_REQUEST_PAGE_CODE,mCode);
            json_object.put(UserSystemConfig.GetShoesBindConfig.JSON_REQUEST_PAGE_NAME,mName);
            json_object.put(UserSystemConfig.GetShoesBindConfig.JSON_REQUEST_PAGE_PRICE,mPrice);
            json_object.put(UserSystemConfig.GetShoesBindConfig.JSON_REQUEST_PAGE_COLOR,mColor);
            json_object.put(UserSystemConfig.GetShoesBindConfig.JSON_REQUEST_PAGE_SIZE,mSize);
            json_object.put(UserSystemConfig.GetShoesBindConfig.JSON_REQUEST_PAGE_STYLE,mStyle);
            json_object.put(UserSystemConfig.GetShoesBindConfig.JSON_REQUEST_PAGE_PICTURE,mPicture);
            json_object.put(UserSystemConfig.GetShoesBindConfig.JSON_REQUEST_PAGE_BUY_TIME,mBuyTime);
            String result_json = json_object.toString();
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getmType() {
        return mType;
    }

    public String getmShoesId() {
        return mShoesId;
    }

    public String getmCode() {
        return mCode;
    }

    public String getmName() {
        return mName;
    }

    public String getmPrice() {
        return mPrice;
    }

    public String getmColor() {
        return mColor;
    }

    public String getmSize() {
        return mSize;
    }

    public String getmStyle() {
        return mStyle;
    }

    public String getmPicture() {
        return mPicture;
    }

    public String getmBuyTime() {
        return mBuyTime;
    }

    @Override
    public String toString() {
        return "ShoesBindInfo{" +
                "mType='" + mType + '\'' +
                ", mShoesId='" + mShoesId + '\'' +
                ", mCode='" + mCode + '\'' +
                ", mName='" + mName + '\'' +
                ", mPrice='" + mPrice + '\'' +
                ", mColor='" + mColor + '\'' +
                ", mSize='" + mSize + '\'' +
                ", mStyle='" + mStyle + '\'' +
                ", mPicture='" + mPicture + '\'' +
                ", mBuyTime='" + mBuyTime + '\'' +
                '}';
    }
}
