package com.jordan.usersystemlibrary.task;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.safari.httplibs.BaseTask;
import com.safari.httplibs.HttpUtils;
import com.safari.httplibs.config.InnerMessageConfig;
import com.safari.httplibs.utils.CommonUtils;
import com.jordan.usersystemlibrary.config.UserSystemConfig;
import com.jordan.usersystemlibrary.utils.UserSystemUtils;
import com.safari.core.protocol.RequestMessage;

/**
 * Created by icean on 2017/2/2.
 */

public final class MoveUploadlTask extends BaseTask {
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
    private String mHeader;
    private String mStadiumType;
    private String mUrl;

    public MoveUploadlTask(Context ctx, String remote_address, Handler main_handler,
                           String vipId , String sn , String footer , String longitude , String latitude , String address ,
                           String beginTime , String spend , String picture , String endTime , String totalDist , String totalStep ,
                           String totalHorDist , String totalVerDist , String totalTime , String totalActiveTime ,
                           String activeRate , String avgSpeed , String maxSpeed , String spurtCount , String breakinCount ,
                           String breakinAvgTime , String verJumpPoint , String verJumpCount , String verJumpAvgHigh ,
                           String verJumpMaxHigh , String avgHoverTime , String avgTouchAngle , String touchType , String perfRank ,
                           String runRank , String breakRank , String bounceRank , String avgShotDist , String maxShotDist ,
                           String handle , String calorie , String trail,String header,String stadiumType, String user_token,
                           boolean is_granted) {
        super(ctx, remote_address, user_token, main_handler, is_granted);
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
        mUrl = mRemoteServerAddress + UserSystemConfig.URI_MOVE_UPLOAD;
    }

    @Override
    public String doTask() {
        String main_json_str = UserSystemUtils.createMoveUploadMainRequest(
                mVipId, mSn, mFooter, mLongitude, mLatitude, mAddress, mBeginTime,
                mSpend, mPicture, mEndTime, mTotalDist,mTotalStep, mTotalHorDist, mTotalVerDist,
                mTotalTime, mTotalActiveTime, mActiveRate, mAvgSpeed, mMaxSpeed,
                mSpurtCount, mBreakinCount, mBreakinAvgTime, mVerJumpPoint, mVerJumpCount, 
                mVerJumpAvgHigh, mVerJumpMaxHigh, mAvgHoverTime, mAvgTouchAngle,
                mTouchType, mPerfRank, mRunRank, mBreakRank, mBounceRank,
                mAvgShotDist, mMaxShotDist, mHandle, mCalorie, mTrail,mHeader,mStadiumType);
        RequestMessage.Request request_proto = CommonUtils.createRequest(mContext, main_json_str, mUserToken, mIsGranted);
        String result_json = HttpUtils.sendHttpRequest(mUrl, request_proto);
        return result_json;
    }

    @Override
    public void onSuccess(String result_json) {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_MOVE_UPLOAD_MESSAGE_SUCCESS);
            success_message.obj = result_json;
            success_message.sendToTarget();
        }
    }

    @Override
    public void onFalse(String false_json) {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_MOVE_UPLOAD_MESSAGE_FALSE);
            success_message.obj = false_json;
            success_message.sendToTarget();
        }
    }

    @Override
    public void onException() {
        if (null != mMainHandler) {
            Message success_message = mMainHandler.obtainMessage(InnerMessageConfig.USER_MOVE_UPLOAD_MESSAGE_EXCEPTION);
            success_message.sendToTarget();
        }
    }
}
