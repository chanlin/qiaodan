package com.jordan.usersystemlibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.jordan.usersystemlibrary.config.UserSystemConfig;
import com.jordan.usersystemlibrary.data.MoveUploadInfo;
import com.jordan.usersystemlibrary.task.CheckAccountTask;
import com.jordan.usersystemlibrary.task.CreateListTask;
import com.jordan.usersystemlibrary.task.CreateReachTask;
import com.jordan.usersystemlibrary.task.ForgetPasswordTask;
import com.jordan.usersystemlibrary.task.GetAccountDataTask;
import com.jordan.usersystemlibrary.task.GetUserDataTask;
import com.jordan.usersystemlibrary.task.JoinListTask;
import com.jordan.usersystemlibrary.task.LoginTask;
import com.jordan.usersystemlibrary.task.LogoutTask;
import com.jordan.usersystemlibrary.task.ModifyAllUserDataTask;
import com.jordan.usersystemlibrary.task.ModifyPasswordTask;
import com.jordan.usersystemlibrary.task.ModifySingleUserDataTask;
import com.jordan.usersystemlibrary.task.MoveDetailTask;
import com.jordan.usersystemlibrary.task.MoveListTask;
import com.jordan.usersystemlibrary.task.MoveRedarTask;
import com.jordan.usersystemlibrary.task.MoveUploadlTask;
import com.jordan.usersystemlibrary.task.MoveUploadsTask;
import com.jordan.usersystemlibrary.task.ReachDetailTask;
import com.jordan.usersystemlibrary.task.ReachJoinTask;
import com.jordan.usersystemlibrary.task.ReachListTask;
import com.jordan.usersystemlibrary.task.RegisterTask;
import com.jordan.usersystemlibrary.task.ShoesBindTask;
import com.jordan.usersystemlibrary.task.ShoesBoxTask;
import com.jordan.usersystemlibrary.task.ShoesListTask;
import com.jordan.usersystemlibrary.task.ShoesRecoTask;
import com.jordan.usersystemlibrary.task.ShoesUnBindTask;
import com.jordan.usersystemlibrary.task.TrainCountTask;
import com.jordan.usersystemlibrary.task.TrainDetailTask;
import com.jordan.usersystemlibrary.task.TrainDictTask;
import com.jordan.usersystemlibrary.task.TrainListTask;
import com.jordan.usersystemlibrary.task.TrainUploadTask;

import java.util.ArrayList;


/**
 * Created by icean on 2017/2/2.
 */

public final class UserManager {
    private Context mContext;
    private Handler mMainThreadHandler;
    private String mRemoteAddress;
    private boolean mIsGranted;
    private String mUserToken;

    private CheckAccountTask mCheckAccountTask;
    private ForgetPasswordTask mForgetPasswordTask;
    private GetAccountDataTask mGetAccountDataTask;
    private GetUserDataTask mGetUserDataTask;
    private LoginTask mLoginTask;
    private LogoutTask mLogoutTask;
    private ModifyAllUserDataTask mModifyAllUserDataTask;
    private ModifyPasswordTask mModifyPasswordTask;
    private ModifySingleUserDataTask mModifySingleUserDataTask;
    private RegisterTask mRegisterTask;
    private ReachListTask mReachListTask;
    private CreateListTask mCreateListTask;
    private JoinListTask mJoinListTask;
    private MoveDetailTask mMoveDetailTask;
    private CreateReachTask mCreateReachTask;
    private ReachDetailTask mReachDetailTask;
    private ReachJoinTask mReachJoinTask;
    private ShoesBindTask mShoesBindTask;
    private MoveListTask mMoveListTask;
    private MoveUploadlTask mMoveUploadTask;
    private MoveUploadsTask mMoveUploadsTask;
    private MoveRedarTask mMoveRedarTask;
    private TrainListTask mTrainListTask;
    private TrainDetailTask mTrainDetailTask;
    private TrainDictTask mTrainDictTask;
    private ShoesBoxTask mShoesBoxTask;
    private ShoesRecoTask mShoesRecoTask;
    private ShoesListTask mShoesListTask;
    private ShoesUnBindTask mShoesUnBindTask;
    private TrainCountTask mTrainCountTask;
    private TrainUploadTask mTrainUploadTask;

    public UserManager(Context context, Handler main_thread_handler, String user_system_address) {
        mContext = context;
        mMainThreadHandler = main_thread_handler;
        mRemoteAddress = user_system_address;
        mIsGranted = false;
        mUserToken = getOfflineUserToken();
    }

    public UserManager(Context context, Handler main_thread_handler) {
        this(context, main_thread_handler, UserSystemConfig.USER_SYSTEM_DEFAULT_ADDRESS);
    }

    public void setIsGranted(boolean is_granted) {
        mIsGranted = is_granted;
    }

    public void setUserToken(String user_token){
        mUserToken = user_token;
        Log.i("token","setUserToken user_token:"+user_token);
        Log.i("token","setUserToken mUserToken:"+mUserToken);
    }

    public void checkAccount(String user_account, String account_type) {
        if (null != mCheckAccountTask) {
            mCheckAccountTask.cancel(true);
            mCheckAccountTask = null;
        }

        mCheckAccountTask = new CheckAccountTask(mContext, mRemoteAddress, mMainThreadHandler, user_account, account_type, mUserToken, mIsGranted);
        mCheckAccountTask.execute("Begin");
    }
    public void shoesUnBind(String ids) {
        if (null != mShoesUnBindTask) {
            mShoesUnBindTask.cancel(true);
            mShoesUnBindTask = null;
        }

        mShoesUnBindTask = new ShoesUnBindTask(mContext, mRemoteAddress, mMainThreadHandler,ids, mUserToken, mIsGranted);
        mShoesUnBindTask.execute("Begin");
    }
    public void shoesBind(String type,String shoesId,String code,String name,
                          String price,String color,String size,String style,
                          String picture,String buyTime) {
        if (null != mShoesBindTask) {
            mShoesBindTask.cancel(true);
            mShoesBindTask = null;
        }

        mShoesBindTask = new ShoesBindTask(mContext, mRemoteAddress, mMainThreadHandler,
                type,shoesId,code,name,
                price,color,size,style,
                picture,buyTime, mUserToken, mIsGranted);
        mShoesBindTask.execute("Begin");
    }

    public void forgetPassword(String mobile, String password, String verification_type, String code) {
        if (null != mForgetPasswordTask) {
            mForgetPasswordTask.cancel(true);
            mForgetPasswordTask = null;
        }

        mForgetPasswordTask = new ForgetPasswordTask(mContext, mRemoteAddress, mMainThreadHandler, mobile, password, verification_type, code, mUserToken, mIsGranted);
        mForgetPasswordTask.execute("Begin");
    }

    public void reachList(String beginTime,String endTime,String type,String longitude,
                          String latitude,String province,String city,String district,
                          String limited,String pageNo,String pageSize,String sort) {
        if (null != mReachListTask) {
            mReachListTask.cancel(true);
            mReachListTask = null;
        }

        mReachListTask = new ReachListTask(mContext, mRemoteAddress, mMainThreadHandler,
                beginTime,endTime,type,longitude,latitude,province,city,district,
                limited,pageNo,pageSize,sort,mUserToken, mIsGranted);
        mReachListTask.execute("Begin");
    }
    public void trainCount(String id) {
        if (null != mTrainCountTask) {
            mTrainCountTask.cancel(true);
            mTrainCountTask = null;
        }

        mTrainCountTask = new TrainCountTask(mContext, mRemoteAddress, mMainThreadHandler,
                id,mUserToken, mIsGranted);
        mTrainCountTask.execute("Begin");
    }
    public void trainUpload(String source,String type,String platform,String say,
                            String img,String urls) {
        if (null != mTrainUploadTask) {
            mTrainUploadTask.cancel(true);
            mTrainUploadTask = null;
        }

        mTrainUploadTask = new TrainUploadTask(mContext, mRemoteAddress, mMainThreadHandler,
                source,type,platform,say,
                img,urls,mUserToken, mIsGranted);
        mTrainUploadTask.execute("Begin");
    }
    public void trainList(String id,String pageNo,String pageSize) {
        if (null != mTrainListTask) {
            mTrainListTask.cancel(true);
            mTrainListTask = null;
        }

        mTrainListTask = new TrainListTask(mContext, mRemoteAddress, mMainThreadHandler,
                id,pageNo,pageSize,mUserToken, mIsGranted);
        mTrainListTask.execute("Begin");
    }
    public void trainDetail(String id) {
        if (null != mTrainDetailTask) {
            mTrainDetailTask.cancel(true);
            mTrainDetailTask = null;
        }

        mTrainDetailTask = new TrainDetailTask(mContext, mRemoteAddress, mMainThreadHandler,
                id,mUserToken, mIsGranted);
        mTrainDetailTask.execute("Begin");
    }
    public void shoesBox() {
        if (null != mShoesBoxTask) {
            mShoesBoxTask.cancel(true);
            mShoesBoxTask = null;
        }

        mShoesBoxTask = new ShoesBoxTask(mContext, mRemoteAddress, mMainThreadHandler,
                mUserToken, mIsGranted);
        mShoesBoxTask.execute("Begin");
    }
    public void shoesReco() {
        if (null != mShoesRecoTask) {
            mShoesRecoTask.cancel(true);
            mShoesRecoTask = null;
        }

        mShoesRecoTask = new ShoesRecoTask(mContext, mRemoteAddress, mMainThreadHandler,
                mUserToken, mIsGranted);
        mShoesRecoTask.execute("Begin");
    }
    public void shoesList(String pageNo,String pageSize) {
        if (null != mShoesListTask) {
            mShoesListTask.cancel(true);
            mShoesListTask = null;
        }

        mShoesListTask = new ShoesListTask(mContext, mRemoteAddress, mMainThreadHandler,
                pageNo,pageSize,mUserToken, mIsGranted);
        mShoesListTask.execute("Begin");
    }
    public void trainDict() {
        if (null != mTrainDictTask) {
            mTrainDictTask.cancel(true);
            mTrainDictTask = null;
        }

        mTrainDictTask = new TrainDictTask(mContext, mRemoteAddress, mMainThreadHandler,
                mUserToken, mIsGranted);
        mTrainDictTask.execute("Begin");
    }
    public void reachDetail(String id) {
        if (null != mReachDetailTask) {
            mReachDetailTask.cancel(true);
            mReachDetailTask = null;
        }

        mReachDetailTask = new ReachDetailTask(mContext, mRemoteAddress, mMainThreadHandler,
                id,mUserToken, mIsGranted);
        mReachDetailTask.execute("Begin");
    }
    public void reachJoin(String id) {
        if (null != mReachJoinTask) {
            mReachJoinTask.cancel(true);
            mReachJoinTask = null;
        }

        mReachJoinTask = new ReachJoinTask(mContext, mRemoteAddress, mMainThreadHandler,
                id,mUserToken, mIsGranted);
        mReachJoinTask.execute("Begin");
    }
    public void createList(String pageNo,String pageSize) {
        if (null != mCreateListTask) {
            mCreateListTask.cancel(true);
            mCreateListTask = null;
        }

        mCreateListTask = new CreateListTask(mContext, mRemoteAddress, mMainThreadHandler,pageNo,pageSize,mUserToken, mIsGranted);
        mCreateListTask.execute("Begin");
    }
    public void createReach(String time,String duration,String people,String type,
                            String picture,String longitude,String latitude,String province,
                            String city,String district,String street,String address,
                            String slogan,String remarks) {
        if (null != mCreateReachTask) {
            mCreateReachTask.cancel(true);
            mCreateReachTask = null;
        }

        mCreateReachTask = new CreateReachTask(mContext, mRemoteAddress, mMainThreadHandler,time,duration,people,type,
                picture,longitude,latitude,province,
                city,district,street,address,slogan,remarks,mUserToken, mIsGranted);
        mCreateReachTask.execute("Begin");
    }
    public void joinList(String pageNo,String pageSize) {
        if (null != mJoinListTask) {
            mJoinListTask.cancel(true);
            mJoinListTask = null;
        }

        mJoinListTask = new JoinListTask(mContext, mRemoteAddress, mMainThreadHandler,pageNo,pageSize,mUserToken, mIsGranted);
        mJoinListTask.execute("Begin");
    }
    public void moveList(String beginTime, String endTime,String pageNo,String pageSize) {
        if (null != mMoveListTask) {
            mMoveListTask.cancel(true);
            mMoveListTask = null;
        }

        mMoveListTask = new MoveListTask(mContext, mRemoteAddress, mMainThreadHandler,beginTime,endTime,pageNo,pageSize,mUserToken, mIsGranted);
        mMoveListTask.execute("Begin");
    }
    public void moveUploads(ArrayList<MoveUploadInfo> list) {
        if (null != mMoveUploadsTask) {
            mMoveUploadsTask.cancel(true);
            mMoveUploadsTask = null;
        }

        mMoveUploadsTask = new MoveUploadsTask(mContext, mRemoteAddress, mMainThreadHandler,
                list,mUserToken, mIsGranted);
        mMoveUploadsTask.execute("Begin");
    }
    public void moveUpload( String vipId , String sn , String footer , String longitude , String latitude , String address ,
                            String beginTime , String spend , String picture , String endTime , String totalDist , String totalStep ,
                            String totalHorDist , String totalVerDist , String totalTime , String totalActiveTime ,
                            String activeRate , String avgSpeed , String maxSpeed , String spurtCount , String breakinCount ,
                            String breakinAvgTime , String verJumpPoint , String verJumpCount , String verJumpAvgHigh ,
                            String verJumpMaxHigh , String avgHoverTime , String avgTouchAngle , String touchType , String perfRank ,
                            String runRank , String breakRank , String bounceRank , String avgShotDist , String maxShotDist ,
                            String handle , String calorie , String trail,String header,String stadiumType) {
        if (null != mMoveUploadTask) {
            mMoveUploadTask.cancel(true);
            mMoveUploadTask = null;
        }

        mMoveUploadTask = new MoveUploadlTask(mContext, mRemoteAddress, mMainThreadHandler,
                vipId ,sn ,footer ,longitude ,latitude ,address ,
                beginTime ,spend ,picture ,endTime ,totalDist ,totalStep ,
                totalHorDist ,totalVerDist ,totalTime ,totalActiveTime ,
                activeRate ,avgSpeed ,maxSpeed ,spurtCount ,breakinCount ,
                breakinAvgTime ,verJumpPoint ,verJumpCount ,verJumpAvgHigh ,
                verJumpMaxHigh ,avgHoverTime ,avgTouchAngle ,touchType ,perfRank ,
                runRank ,breakRank ,bounceRank ,avgShotDist ,maxShotDist ,
                handle ,calorie ,trail,header,stadiumType,mUserToken, mIsGranted);
        mMoveUploadTask.execute("Begin");
    }
    public void moveRedar(String id) {
        if (null != mMoveRedarTask) {
            mMoveRedarTask.cancel(true);
            mMoveRedarTask = null;
        }

        mMoveRedarTask = new MoveRedarTask(mContext, mRemoteAddress, mMainThreadHandler,id,mUserToken, mIsGranted);
        mMoveRedarTask.execute("Begin");
    }
    public void moveDetail(String id) {
        if (null != mMoveDetailTask) {
            mMoveDetailTask.cancel(true);
            mMoveDetailTask = null;
        }

        mMoveDetailTask = new MoveDetailTask(mContext, mRemoteAddress, mMainThreadHandler,id,mUserToken, mIsGranted);
        mMoveDetailTask.execute("Begin");
    }

    public void getAccountData(String user_account) {
        if (null != mGetAccountDataTask) {
            mGetAccountDataTask.cancel(true);
            mGetAccountDataTask = null;
        }

        mGetAccountDataTask = new GetAccountDataTask(mContext, mRemoteAddress, mMainThreadHandler, user_account, mUserToken, mIsGranted);
        mGetAccountDataTask.execute("Begin");
    }

    public void getUserData(String user_account) {
        if (null != mGetUserDataTask) {
            mGetUserDataTask.cancel(true);
            mGetUserDataTask = null;
        }

        Log.i("token","getUserData user_token:"+mUserToken);
        mGetUserDataTask = new GetUserDataTask(mContext, mRemoteAddress, mMainThreadHandler, user_account, mUserToken, mIsGranted);
        mGetUserDataTask.execute("Begin");
    }

    public void login(String user_account, String password, String login_type, String code){
        if (null != mLoginTask) {
            mLoginTask.cancel(true);
            mLoginTask = null;
        }

        mLoginTask = new LoginTask(mContext, mRemoteAddress, mMainThreadHandler, user_account, login_type, password, code, mUserToken, mIsGranted);
        mLoginTask.execute("Begin");
    }

    public void register(String user_account, String password, String account_type, String code){
        if (null != mRegisterTask) {
            mRegisterTask.cancel(true);
            mRegisterTask = null;
        }

        mRegisterTask = new RegisterTask(mContext, mRemoteAddress, mMainThreadHandler, user_account, password, account_type, code, mUserToken, mIsGranted);
        mRegisterTask.execute("Begin");
    }

    public void modifyAllUserData(String name, String nick, String gender, String age,
                                  String birthday, String position, String weight, String height, String QQ, String img) {
        if (null != mModifyAllUserDataTask) {
            mModifyAllUserDataTask.cancel(true);
            mModifyAllUserDataTask = null;
        }

        Log.i("token","modifyAllUserData mUserToken:"+mUserToken);
        mModifyAllUserDataTask = new ModifyAllUserDataTask(mContext, mRemoteAddress, mMainThreadHandler,
                name, nick, gender, age, birthday, position, weight, height, QQ, img, mUserToken, mIsGranted);
        mModifyAllUserDataTask.execute("Begin");
    }

    public void modifyPassword(String old_password, String new_password){
        if (null != mModifyPasswordTask) {
            mModifyPasswordTask.cancel(true);
            mModifyPasswordTask = null;
        }

        mModifyPasswordTask = new ModifyPasswordTask(mContext, mRemoteAddress, mMainThreadHandler, old_password, new_password, mUserToken, mIsGranted);
        mModifyPasswordTask.execute("Begin");
    }

    public void modifySingleUserData(String input_key, String input_value) {
        if (null != mModifySingleUserDataTask) {
            mModifySingleUserDataTask.cancel(true);
            mModifySingleUserDataTask = null;
        }

        mModifySingleUserDataTask = new ModifySingleUserDataTask(mContext, mRemoteAddress, mMainThreadHandler, input_key, input_value, mUserToken, mIsGranted);
        mModifySingleUserDataTask.execute("Begin");
    }

    public void logout(){
        if (null != mLogoutTask) {
            mLogoutTask.cancel(true);
            mLogoutTask = null;
        }

        mLogoutTask = new LogoutTask(mContext, mRemoteAddress, mMainThreadHandler, mUserToken, mIsGranted);
        mLogoutTask.execute("Begin");
    }

    public void saveUserData(String user_token, String user_json) {
        mUserToken = user_token;
        SharedPreferences.Editor editor = mContext.getSharedPreferences(UserSystemConfig.USER_SYSTEM_OFFLINE_FILE, Context.MODE_PRIVATE).edit();
        editor.putString(UserSystemConfig.OfflineUserConfigFile.KEY_TOKEN, mUserToken);
        editor.putString(UserSystemConfig.OfflineUserConfigFile.KEY_USER_JSON, user_json);
        editor.putLong(UserSystemConfig.OfflineUserConfigFile.KEY_LOGIN_DATETIME, System.currentTimeMillis());
        editor.commit();
    }

    public String getUserToken(){
        if (TextUtils.isEmpty(mUserToken)) {
            SharedPreferences sf = mContext.getSharedPreferences(UserSystemConfig.USER_SYSTEM_OFFLINE_FILE, Context.MODE_PRIVATE);
            mUserToken = sf.getString(UserSystemConfig.OfflineUserConfigFile.KEY_TOKEN, "");
            return mUserToken;
        } else {
            return mUserToken;
        }
    }

    public String getUserJson(){
        SharedPreferences sf = mContext.getSharedPreferences(UserSystemConfig.USER_SYSTEM_OFFLINE_FILE, Context.MODE_PRIVATE);
        String user_json = sf.getString(UserSystemConfig.OfflineUserConfigFile.KEY_USER_JSON, "");
        return user_json;
    }

    public long getUserLoginDatetime(){
        SharedPreferences sf = mContext.getSharedPreferences(UserSystemConfig.USER_SYSTEM_OFFLINE_FILE, Context.MODE_PRIVATE);
        long login_datetime = sf.getLong(UserSystemConfig.OfflineUserConfigFile.KEY_LOGIN_DATETIME, -1);
        return login_datetime;
    }

    public String getOfflineUserToken(){
        SharedPreferences sf = mContext.getSharedPreferences(UserSystemConfig.OFFLINE_USER_FILE, Context.MODE_PRIVATE);
        String user_token = sf.getString(UserSystemConfig.OFFLINE_USER_TOKEN, "");
        Log.i("token","getOfflineUserToken user_token:"+user_token);
        return  user_token;
    }

    public String getOfflineUserInfoJson(){
        SharedPreferences sf = mContext.getSharedPreferences(UserSystemConfig.OFFLINE_USER_FILE, Context.MODE_PRIVATE);
        String user_info = sf.getString(UserSystemConfig.OFFLINE_USER_INFO, "");
        return  user_info;
    }
}
