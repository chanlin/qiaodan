package com.jordan.usersystemlibrary.data;

import android.util.Log;

import com.safari.httplibs.utils.data.JsonInfo;
import com.jordan.usersystemlibrary.config.UserSystemConfig;

import org.json.JSONObject;

/**
 * Created by icean on 2017/2/2.
 */

public final class MoveUploadInfo extends JsonInfo {
    private String mVipId;//	会员ID
    private String mSn;//		蓝牙SN号
    private String mFooter;//		左右脚 R右脚 L左脚
    private String mLongitude;//		经度
    private String mLatitude;//		纬度
    private String mAddress;//		地址
    private String mBeginTime;//		开始时间(时间戳)
    private String mSpend;//		运动时长
    private String mPicture;//		图片
    private String mEndTime;//		结束时间(时间戳)
    private String mTotalDist;//		总距离
    private String mTotalStep;//		总步数
    private String mTotalHorDist;//		横向总距离
    private String mTotalVerDist;//		纵向总距离
    private String mTotalTime;//		总时间
    private String mTotalActiveTime;//		活跃总时间
    private String mActiveRate;//		活跃时间占比
    private String mAvgSpeed;//		平均移动速度
    private String mMaxSpeed;//		最大移动速度
    private String mSpurtCount;//		冲向次数
    private String mBreakinCount;//		变向次数
    private String mBreakinAvgTime;//		变向平均触底时间
    private String mVerJumpPoint;//		纵跳点(纵跳的高度的集合，以”,”分隔)
    private String mVerJumpCount;//		纵跳次数
    private String mVerJumpAvgHigh;//		纵跳平均高度
    private String mVerJumpMaxHigh;//		纵跳最大高度
    private String mAvgHoverTime;//		平均滞空时间
    private String mAvgTouchAngle;//		平均着地旋转角
    private String mTouchType;//		着地类型
    private String mPerfRank;//		本场表现
    private String mRunRank;//		跑动等级
    private String mBreakRank;//		突破等级
    private String mBounceRank;//		弹跳等级
    private String mAvgShotDist;//		平均出手距离
    private String mMaxShotDist;//		最大出手距离
    private String mHandle;//		手感
    private String mCalorie;//		消耗卡路里
    private String mTrail;//		运动轨迹
    private String mBallType;
    private String mHeader;// 头100个字节原始数据
    private String mStadiumType;

    public MoveUploadInfo(){

    }
    public MoveUploadInfo(
            String vipId , String sn , String footer , String longitude , String latitude , String address ,
            String beginTime , String spend , String picture , String endTime , String totalDist , String totalStep ,
            String totalHorDist , String totalVerDist , String totalTime , String totalActiveTime ,
            String activeRate , String avgSpeed , String maxSpeed , String spurtCount , String breakinCount ,
            String breakinAvgTime , String verJumpPoint , String verJumpCount , String verJumpAvgHigh ,
            String verJumpMaxHigh , String avgHoverTime , String avgTouchAngle , String touchType , String perfRank ,
            String runRank , String breakRank , String bounceRank , String avgShotDist , String maxShotDist ,
            String handle , String calorie , String trail , String header ,String stadiumType) {
        mVipId = vipId;//	会员ID
        mSn = sn;//		蓝牙SN号
        mFooter = footer;//		左右脚 R右脚 L左脚
        mLongitude = longitude;//		经度
        mLatitude = latitude;//		纬度
        mAddress = address;//		地址
        mBeginTime = beginTime;//		开始时间(时间戳)
        mSpend = spend;//		运动时长
        mPicture = picture;//		图片
        mEndTime = endTime;//		结束时间(时间戳)
        mTotalDist = totalDist;//		总距离
        mTotalStep = totalStep;//		总步数
        mTotalHorDist = totalHorDist;//		横向总距离
        mTotalVerDist = totalVerDist;//		纵向总距离
        mTotalTime = totalTime;//		总时间
        mTotalActiveTime = totalActiveTime;//		活跃总时间
        mActiveRate = activeRate;//		活跃时间占比
        mAvgSpeed = avgSpeed;//		平均移动速度
        mMaxSpeed = maxSpeed;//		最大移动速度
        mSpurtCount = spurtCount;//		冲向次数
        mBreakinCount = breakinCount;//		变向次数
        mBreakinAvgTime = breakinAvgTime;//		变向平均触底时间
        mVerJumpPoint = verJumpPoint;//		纵跳点(纵跳的高度的集合，以”,”分隔)
        mVerJumpCount = verJumpCount;//		纵跳次数
        mVerJumpAvgHigh = verJumpAvgHigh;//		纵跳平均高度
        mVerJumpMaxHigh = verJumpMaxHigh;//		纵跳最大高度
        mAvgHoverTime = avgHoverTime;//		平均滞空时间
        mAvgTouchAngle = avgTouchAngle;//		平均着地旋转角
        mTouchType = touchType;//		着地类型
        mPerfRank = perfRank;//		本场表现
        mRunRank = runRank;//		跑动等级
        mBreakRank = breakRank;//		突破等级
        mBounceRank = bounceRank;//		弹跳等级
        mAvgShotDist = avgShotDist;//		平均出手距离
        mMaxShotDist = maxShotDist;//		最大出手距离
        mHandle = handle;//		手感
        mCalorie = calorie;//		消耗卡路里
        mTrail = trail;//		运动轨迹
        mHeader = header;
        mStadiumType = stadiumType;
    }

    public String getmStadiumType() {
        return mStadiumType;
    }

    public void setmStadiumType(String mStadiumType) {
        this.mStadiumType = mStadiumType;
    }

    public String getmVipId() {
        return mVipId;
    }

    public String getmSn() {
        return mSn;
    }

    public String getmFooter() {
        return mFooter;
    }

    public String getmLongitude() {
        return mLongitude;
    }

    public String getmLatitude() {
        return mLatitude;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmBeginTime() {
        return mBeginTime;
    }

    public String getmSpend() {
        return mSpend;
    }

    public String getmPicture() {
        return mPicture;
    }

    public String getmEndTime() {
        return mEndTime;
    }

    public String getmTotalDist() {
        return mTotalDist;
    }

    public String getmTotalStep() {
        return mTotalStep;
    }

    public String getmTotalHorDist() {
        return mTotalHorDist;
    }

    public String getmTotalVerDist() {
        return mTotalVerDist;
    }

    public String getmTotalTime() {
        return mTotalTime;
    }

    public String getmTotalActiveTime() {
        return mTotalActiveTime;
    }

    public String getmActiveRate() {
        return mActiveRate;
    }

    public String getmAvgSpeed() {
        return mAvgSpeed;
    }

    public String getmMaxSpeed() {
        return mMaxSpeed;
    }

    public String getmSpurtCount() {
        return mSpurtCount;
    }

    public String getmBreakinCount() {
        return mBreakinCount;
    }

    public String getmBreakinAvgTime() {
        return mBreakinAvgTime;
    }

    public String getmVerJumpPoint() {
        return mVerJumpPoint;
    }

    public String getmVerJumpCount() {
        return mVerJumpCount;
    }

    public String getmVerJumpAvgHigh() {
        return mVerJumpAvgHigh;
    }

    public String getmVerJumpMaxHigh() {
        return mVerJumpMaxHigh;
    }

    public String getmAvgHoverTime() {
        return mAvgHoverTime;
    }

    public String getmAvgTouchAngle() {
        return mAvgTouchAngle;
    }

    public String getmTouchType() {
        return mTouchType;
    }

    public String getmPerfRank() {
        return mPerfRank;
    }

    public String getmRunRank() {
        return mRunRank;
    }

    public String getmBreakRank() {
        return mBreakRank;
    }

    public String getmBounceRank() {
        return mBounceRank;
    }

    public String getmAvgShotDist() {
        return mAvgShotDist;
    }

    public String getmMaxShotDist() {
        return mMaxShotDist;
    }

    public String getmHandle() {
        return mHandle;
    }

    public String getmCalorie() {
        return mCalorie;
    }

    public String getmTrail() {
        return mTrail;
    }

    public String getmBallType() {
        return mBallType;
    }

    public void setmBallType(String mBallType) {
        this.mBallType = mBallType;
    }

    @Override
    public String toJsonStr() {
        try {
            JSONObject json_object = new JSONObject();
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_VIP_ID,mVipId);//	会员ID
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_SN,mSn);//		蓝牙SN号
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_FOOTER,mFooter);//		左右脚 R右脚 L左脚
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_LONGITUDE,mLongitude);//		经度
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_LATITUDE,mLatitude);//		纬度
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_ADDRESS,mAddress);//		地址
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_BEGIN_TIME,mBeginTime);//		开始时间(时间戳)
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_SPEND,mSpend);//		运动时长
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_PICTURE,mPicture);//		图片
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_ENDTIME,mEndTime);//		结束时间(时间戳)
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_TOTAL_DIST,mTotalDist);//		总距离
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_TOTAL_STEP,mTotalStep);//		总步数
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_TOTAL_HOR_DIST,mTotalHorDist);//		横向总距离
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_TOTAL_VER_DIST,mTotalVerDist);//		纵向总距离
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_TOTAL_TIME,mTotalTime);//		总时间
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_TOTAL_ACTIVE,mTotalActiveTime);//		活跃总时间
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_ACTIVE_RATE,mActiveRate);//		活跃时间占比
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_AVG_SPEED,mAvgSpeed);//		平均移动速度
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_MAX_SPEED,mMaxSpeed);//		最大移动速度
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_SPURT_COUNT,mSpurtCount);//		冲向次数
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_BREAK_IN_COUNT,mBreakinCount);//		变向次数
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_BREAK_IN_AVG_TIME,mBreakinAvgTime);//		变向平均触底时间
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_VER_JUMP_POINT,mVerJumpPoint);//		纵跳点(纵跳的高度的集合，以”,”分隔)
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_VER_JUMP_COUNT,mVerJumpCount);//		纵跳次数
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_VER_JUMP_AVG_HIGH,mVerJumpAvgHigh);//		纵跳平均高度
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_VER_JUMP_MAX_HIGH,mVerJumpMaxHigh);//		纵跳最大高度
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_AVG_HOVER_TIME,mAvgHoverTime);//		平均滞空时间
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_AVG_TOUCH_ANGLE,mAvgTouchAngle);//		平均着地旋转角
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_TOUCH_TYPE,mTouchType);//		着地类型
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_PERF_RANK,mPerfRank);//		本场表现
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_RUN_RANK,mRunRank);//		跑动等级
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_BREAK_RANK,mBreakRank);//		突破等级
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_BOUNCE_RANK,mBounceRank);//		弹跳等级
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_AVG_SHOT_DIST,mAvgShotDist);//		平均出手距离
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_MAX_SHOT_DIST,mMaxShotDist);//		最大出手距离
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_HANDLE,mHandle);//		手感
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_CALORIE,mCalorie);//		消耗卡路里
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_TRAIL,mTrail);//		运动轨迹
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_HEADER,mHeader);//		运动轨迹
            String fieldType = "1";
            if(mStadiumType.equals("水泥")){
                fieldType="1";
            }else if(mStadiumType.equals("塑胶")){
                fieldType="2";
            }else if(mStadiumType.equals("木地板")){
                fieldType="3";
            }
            json_object.put(UserSystemConfig.GetMoveUploadConfig.JSON_REQUEST_PAGE_FIELD_TYPE,fieldType);//		运动轨迹
            String result_json = json_object.toString();
            Log.i("Photo", "MoveUploadInfo result_json: " + result_json);
            return  result_json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setmVipId(String mVipId) {
        this.mVipId = mVipId;
    }

    public void setmSn(String mSn) {
        this.mSn = mSn;
    }

    public void setmFooter(String mFooter) {
        this.mFooter = mFooter;
    }

    public void setmLongitude(String mLongitude) {
        this.mLongitude = mLongitude;
    }

    public void setmLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public void setmBeginTime(String mBeginTime) {
        this.mBeginTime = mBeginTime;
    }

    public void setmSpend(String mSpend) {
        this.mSpend = mSpend;
    }

    public void setmPicture(String mPicture) {
        this.mPicture = mPicture;
    }

    public void setmEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public void setmTotalDist(String mTotalDist) {
        this.mTotalDist = mTotalDist;
    }

    public void setmTotalStep(String mTotalStep) {
        this.mTotalStep = mTotalStep;
    }

    public void setmTotalHorDist(String mTotalHorDist) {
        this.mTotalHorDist = mTotalHorDist;
    }

    public void setmTotalVerDist(String mTotalVerDist) {
        this.mTotalVerDist = mTotalVerDist;
    }

    public void setmTotalTime(String mTotalTime) {
        this.mTotalTime = mTotalTime;
    }

    public void setmTotalActiveTime(String mTotalActiveTime) {
        this.mTotalActiveTime = mTotalActiveTime;
    }

    public void setmActiveRate(String mActiveRate) {
        this.mActiveRate = mActiveRate;
    }

    public void setmAvgSpeed(String mAvgSpeed) {
        this.mAvgSpeed = mAvgSpeed;
    }

    public void setmMaxSpeed(String mMaxSpeed) {
        this.mMaxSpeed = mMaxSpeed;
    }

    public void setmSpurtCount(String mSpurtCount) {
        this.mSpurtCount = mSpurtCount;
    }

    public void setmBreakinCount(String mBreakinCount) {
        this.mBreakinCount = mBreakinCount;
    }

    public void setmBreakinAvgTime(String mBreakinAvgTime) {
        this.mBreakinAvgTime = mBreakinAvgTime;
    }

    public void setmVerJumpPoint(String mVerJumpPoint) {
        this.mVerJumpPoint = mVerJumpPoint;
    }

    public void setmVerJumpCount(String mVerJumpCount) {
        this.mVerJumpCount = mVerJumpCount;
    }

    public void setmVerJumpAvgHigh(String mVerJumpAvgHigh) {
        this.mVerJumpAvgHigh = mVerJumpAvgHigh;
    }

    public void setmVerJumpMaxHigh(String mVerJumpMaxHigh) {
        this.mVerJumpMaxHigh = mVerJumpMaxHigh;
    }

    public void setmAvgHoverTime(String mAvgHoverTime) {
        this.mAvgHoverTime = mAvgHoverTime;
    }

    public void setmAvgTouchAngle(String mAvgTouchAngle) {
        this.mAvgTouchAngle = mAvgTouchAngle;
    }

    public void setmTouchType(String mTouchType) {
        this.mTouchType = mTouchType;
    }

    public void setmPerfRank(String mPerfRank) {
        this.mPerfRank = mPerfRank;
    }

    public void setmRunRank(String mRunRank) {
        this.mRunRank = mRunRank;
    }

    public void setmBreakRank(String mBreakRank) {
        this.mBreakRank = mBreakRank;
    }

    public void setmBounceRank(String mBounceRank) {
        this.mBounceRank = mBounceRank;
    }

    public void setmAvgShotDist(String mAvgShotDist) {
        this.mAvgShotDist = mAvgShotDist;
    }

    public void setmMaxShotDist(String mMaxShotDist) {
        this.mMaxShotDist = mMaxShotDist;
    }

    public void setmHandle(String mHandle) {
        this.mHandle = mHandle;
    }

    public void setmCalorie(String mCalorie) {
        this.mCalorie = mCalorie;
    }

    public void setmTrail(String mTrail) {
        this.mTrail = mTrail;
    }

    public String getmHeader() {
        return mHeader;
    }

    public void setmHeader(String mHeader) {
        this.mHeader = mHeader;
    }

    @Override
    public String toString() {
        return "MoveUploadInfo{" +
                "mVipId='" + mVipId + '\'' +
                ", mSn='" + mSn + '\'' +
                ", mFooter='" + mFooter + '\'' +
                ", mLongitude='" + mLongitude + '\'' +
                ", mLatitude='" + mLatitude + '\'' +
                ", mAddress='" + mAddress + '\'' +
                ", mBeginTime='" + mBeginTime + '\'' +
                ", mSpend='" + mSpend + '\'' +
                ", mPicture='" + mPicture + '\'' +
                ", mEndTime='" + mEndTime + '\'' +
                ", mTotalDist='" + mTotalDist + '\'' +
                ", mTotalStep='" + mTotalStep + '\'' +
                ", mTotalHorDist='" + mTotalHorDist + '\'' +
                ", mTotalVerDist='" + mTotalVerDist + '\'' +
                ", mTotalTime='" + mTotalTime + '\'' +
                ", mTotalActiveTime='" + mTotalActiveTime + '\'' +
                ", mActiveRate='" + mActiveRate + '\'' +
                ", mAvgSpeed='" + mAvgSpeed + '\'' +
                ", mMaxSpeed='" + mMaxSpeed + '\'' +
                ", mSpurtCount='" + mSpurtCount + '\'' +
                ", mBreakinCount='" + mBreakinCount + '\'' +
                ", mBreakinAvgTime='" + mBreakinAvgTime + '\'' +
                ", mVerJumpPoint='" + mVerJumpPoint + '\'' +
                ", mVerJumpCount='" + mVerJumpCount + '\'' +
                ", mVerJumpAvgHigh='" + mVerJumpAvgHigh + '\'' +
                ", mVerJumpMaxHigh='" + mVerJumpMaxHigh + '\'' +
                ", mAvgHoverTime='" + mAvgHoverTime + '\'' +
                ", mAvgTouchAngle='" + mAvgTouchAngle + '\'' +
                ", mTouchType='" + mTouchType + '\'' +
                ", mPerfRank='" + mPerfRank + '\'' +
                ", mRunRank='" + mRunRank + '\'' +
                ", mBreakRank='" + mBreakRank + '\'' +
                ", mBounceRank='" + mBounceRank + '\'' +
                ", mAvgShotDist='" + mAvgShotDist + '\'' +
                ", mMaxShotDist='" + mMaxShotDist + '\'' +
                ", mHandle='" + mHandle + '\'' +
                ", mCalorie='" + mCalorie + '\'' +
                ", mTrail='" + mTrail + '\'' +
                ", mBallType='" + mBallType + '\'' +
                ", mHeader='" + mHeader + '\'' +
                ", mStadiumType='" + mStadiumType + '\'' +
                '}';
    }
}
