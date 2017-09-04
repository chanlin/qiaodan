package com.jordan.usersystemlibrary.data;

import android.util.Log;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by icean on 2017/2/2.
 */

public final class MoveUploadsInfo extends JsonInfo {
    private ArrayList<MoveUploadInfo> mList = new ArrayList<MoveUploadInfo>();


    public MoveUploadsInfo(ArrayList<MoveUploadInfo> list) {
        mList=list;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            JSONArray json_array = new JSONArray();
            for(int i=0;i<mList.size();i++) {
                MoveUploadInfo moveUploadInfo = mList.get(i);
                JSONObject json_sports = new JSONObject();
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_VIP_ID, moveUploadInfo.getmVipId());//	会员ID
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_SN, moveUploadInfo.getmSn());//		蓝牙SN号
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_FOOTER, moveUploadInfo.getmFooter());//		左右脚 R右脚 L左脚
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_LONGITUDE, moveUploadInfo.getmLongitude());//		经度
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_LATITUDE, moveUploadInfo.getmLatitude());//		纬度
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_ADDRESS, moveUploadInfo.getmAddress());//		地址
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_BEGIN_TIME, moveUploadInfo.getmBeginTime());//		开始时间(时间戳)
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_SPEND, moveUploadInfo.getmSpend());//		运动时长
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_PICTURE, moveUploadInfo.getmPicture());//		图片
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_ENDTIME, moveUploadInfo.getmEndTime());//		结束时间(时间戳)
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_TOTAL_DIST, moveUploadInfo.getmTotalDist());//		总距离
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_TOTAL_STEP, moveUploadInfo.getmTotalStep());//		总步数
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_TOTAL_HOR_DIST, moveUploadInfo.getmTotalHorDist());//		横向总距离
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_TOTAL_VER_DIST, moveUploadInfo.getmTotalVerDist());//		纵向总距离
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_TOTAL_TIME, moveUploadInfo.getmTotalTime());//		总时间
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_TOTAL_ACTIVE, moveUploadInfo.getmTotalActiveTime());//		活跃总时间
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_ACTIVE_RATE, moveUploadInfo.getmActiveRate());//		活跃时间占比
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_AVG_SPEED, moveUploadInfo.getmAvgSpeed());//		平均移动速度
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_MAX_SPEED, moveUploadInfo.getmMaxSpeed());//		最大移动速度
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_SPURT_COUNT, moveUploadInfo.getmSpurtCount());//		冲向次数
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_BREAK_IN_COUNT, moveUploadInfo.getmBreakinCount());//		变向次数
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_BREAK_IN_AVG_TIME, moveUploadInfo.getmBreakinAvgTime());//		变向平均触底时间
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_VER_JUMP_POINT, moveUploadInfo.getmVerJumpPoint());//		纵跳点(纵跳的高度的集合，以”,”分隔)
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_VER_JUMP_COUNT, moveUploadInfo.getmVerJumpCount());//		纵跳次数
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_VER_JUMP_AVG_HIGH, moveUploadInfo.getmVerJumpAvgHigh());//		纵跳平均高度
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_VER_JUMP_MAX_HIGH, moveUploadInfo.getmVerJumpMaxHigh());//		纵跳最大高度
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_AVG_HOVER_TIME, moveUploadInfo.getmAvgHoverTime());//		平均滞空时间
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_AVG_TOUCH_ANGLE, moveUploadInfo.getmAvgTouchAngle());//		平均着地旋转角
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_TOUCH_TYPE, moveUploadInfo.getmTouchType());//		着地类型
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_PERF_RANK, moveUploadInfo.getmPerfRank());//		本场表现
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_RUN_RANK, moveUploadInfo.getmRunRank());//		跑动等级
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_BREAK_RANK, moveUploadInfo.getmBreakRank());//		突破等级
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_BOUNCE_RANK, moveUploadInfo.getmBounceRank());//		弹跳等级
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_AVG_SHOT_DIST, moveUploadInfo.getmAvgShotDist());//		平均出手距离
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_MAX_SHOT_DIST, moveUploadInfo.getmMaxShotDist());//		最大出手距离
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_HANDLE, moveUploadInfo.getmHandle());//		手感
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_CALORIE, moveUploadInfo.getmCalorie());//		消耗卡路里
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_TRAIL, moveUploadInfo.getmTrail());//		运动轨迹
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_TYPE, moveUploadInfo.getmBallType());//运动场地类型
                json_sports.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_PAGE_HEADER, moveUploadInfo.getmHeader());//运动场地类型
                String fieldType = "1";
                if(moveUploadInfo.getmStadiumType().equals("水泥")){
                    fieldType="1";
                }else if(moveUploadInfo.getmStadiumType().equals("塑胶")){
                    fieldType="2";
                }else if(moveUploadInfo.getmStadiumType().equals("木地板")){
                    fieldType="3";
                }
                json_sports.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_FIELD_TYPE,fieldType);//		运动轨迹
                json_array.put(json_sports);
            }
            json_object.put(UserSystemConfig.GetMoveUploadsConfig.JSON_REQUEST_SPORTS, json_array);
            String result_json = json_object.toString();
            Log.i("Photo", "MoveUploadInfo result_json: " + result_json);
            Log.i("fieldType", "MoveUploadInfo result_json: " + result_json);
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<MoveUploadInfo> getmList() {
        return mList;
    }

    public void setmList(ArrayList<MoveUploadInfo> mList) {
        this.mList = mList;
    }

    @Override
    public String toString() {
        return "MoveUploadsInfo{" +
                "mList=" + mList +
                '}';
    }
}
