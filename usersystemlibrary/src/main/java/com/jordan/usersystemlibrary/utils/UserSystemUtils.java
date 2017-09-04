package com.jordan.usersystemlibrary.utils;

import com.jordan.usersystemlibrary.data.CheckAccountInfo;
import com.jordan.usersystemlibrary.data.CreateListInfo;
import com.jordan.usersystemlibrary.data.CreateReachInfo;
import com.jordan.usersystemlibrary.data.ForgetPasswordInfo;
import com.jordan.usersystemlibrary.data.GetAccountDataInfo;
import com.jordan.usersystemlibrary.data.GetUserDataInfo;
import com.jordan.usersystemlibrary.data.JoinListInfo;
import com.jordan.usersystemlibrary.data.LoginInfo;
import com.jordan.usersystemlibrary.data.ModifyAllUserDataInfo;
import com.jordan.usersystemlibrary.data.ModifyPasswordInfo;
import com.jordan.usersystemlibrary.data.ModifySingleUserDataInfo;
import com.jordan.usersystemlibrary.data.MoveDetailInfo;
import com.jordan.usersystemlibrary.data.MoveListInfo;
import com.jordan.usersystemlibrary.data.MoveRedarInfo;
import com.jordan.usersystemlibrary.data.MoveUploadInfo;
import com.jordan.usersystemlibrary.data.MoveUploadsInfo;
import com.jordan.usersystemlibrary.data.ReachDetailInfo;
import com.jordan.usersystemlibrary.data.ReachJoinInfo;
import com.jordan.usersystemlibrary.data.ReachListInfo;
import com.jordan.usersystemlibrary.data.RegisterInfo;
import com.jordan.usersystemlibrary.data.ShoesBindInfo;
import com.jordan.usersystemlibrary.data.ShoesBoxInfo;
import com.jordan.usersystemlibrary.data.ShoesListInfo;
import com.jordan.usersystemlibrary.data.ShoesRecoInfo;
import com.jordan.usersystemlibrary.data.ShoesUnBindInfo;
import com.jordan.usersystemlibrary.data.TrainCountInfo;
import com.jordan.usersystemlibrary.data.TrainDetailInfo;
import com.jordan.usersystemlibrary.data.TrainDictInfo;
import com.jordan.usersystemlibrary.data.TrainListInfo;
import com.jordan.usersystemlibrary.data.TrainUploadInfo;

import java.util.ArrayList;

/**
 * Created by icean on 2017/2/2.
 */

public final class UserSystemUtils {

    public static String createCheckAccountMainRequest(String user_account, String account_type) {
        CheckAccountInfo info = new CheckAccountInfo(user_account, account_type);
        String info_str = info.toJsonStr();
        return info_str;
    }

    public static String createRegisterMainRequest(String user_account, String password, String account_type, String code){
        RegisterInfo info = new RegisterInfo(user_account, password, account_type,code);
        String info_str = info.toJsonStr();
        return info_str;
    }

    public static String createLoginMainRequest(String user_account, String login_type, String login_password, String login_code) {
        LoginInfo info = new LoginInfo(user_account, login_password, login_type, login_code);
        String info_str = info.toJsonStr();
        return  info_str;
    }

    public static String createGetPersonalDataMainRequest(String user_account) {
        GetUserDataInfo info = new GetUserDataInfo(user_account);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createReachDetailMainRequest(String id) {
        ReachDetailInfo info = new ReachDetailInfo(id);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createReachJoinInfoMainRequest(String id) {
        ReachJoinInfo info = new ReachJoinInfo(id);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createReachListMainRequest(String beginTime,String endTime,String type,String longitude,
                                                    String latitude,String province,String city,String district,
                                                    String limited,String pageNo,String pageSize,String sort) {
        ReachListInfo info = new ReachListInfo(beginTime,endTime,type,longitude,latitude,province,city,district,
                limited,pageNo,pageSize,sort);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createCreateReachMainRequest(String time,String duration,String people,String type,
                                                      String picture,String longitude,String latitude,String province,
                                                      String city,String district,String street,String address,
                                                      String slogan,String remarks) {
        CreateReachInfo info = new CreateReachInfo(time,duration,people,type,
                picture,longitude,latitude,province,
                city,district,street,address,slogan,remarks);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createTrainCountMainRequest(String id) {
        TrainCountInfo info = new TrainCountInfo(id);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createCreateListMainRequest(String pageNo,String pageSize) {
        CreateListInfo info = new CreateListInfo(pageNo,pageSize);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createJoinListMainRequest(String pageNo,String pageSize) {
        JoinListInfo info = new JoinListInfo(pageNo,pageSize);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createMoveListMainRequest(String beginTime, String endTime,String pageNo,String pageSize) {
        MoveListInfo info = new MoveListInfo(beginTime,endTime,pageNo,pageSize);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createShoesBindMainRequest(String type,String shoesId,String code,String name,
                                                    String price,String color,String size,String style,
                                                    String picture,String buyTime) {
        ShoesBindInfo info = new ShoesBindInfo(type,shoesId,code,name,
                price,color,size,style,
                picture,buyTime);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createShoesUnBindMainRequest(String ids) {
        ShoesUnBindInfo info = new ShoesUnBindInfo(ids);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createShoesBoxMainRequest() {
        ShoesBoxInfo info = new ShoesBoxInfo();
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createShoesListMainRequest(String pageNo,String pageSize) {
        ShoesListInfo info = new ShoesListInfo(pageNo,pageSize);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createShoesRecoMainRequest() {
        ShoesRecoInfo info = new ShoesRecoInfo();
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createMoveDetailMainRequest(String id) {
        MoveDetailInfo info = new MoveDetailInfo(id);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createMoveRedarMainRequest(String id) {
        MoveRedarInfo info = new MoveRedarInfo(id);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createMoveUploadsMainRequest(ArrayList<MoveUploadInfo> list) {
        MoveUploadsInfo info = new MoveUploadsInfo(list);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createMoveUploadMainRequest(
            String vipId , String sn , String footer , String longitude , String latitude , String address ,
            String beginTime , String spend , String picture , String endTime , String totalDist , String totalStep ,
            String totalHorDist , String totalVerDist , String totalTime , String totalActiveTime ,
            String activeRate , String avgSpeed , String maxSpeed , String spurtCount , String breakinCount ,
            String breakinAvgTime , String verJumpPoint , String verJumpCount , String verJumpAvgHigh ,
            String verJumpMaxHigh , String avgHoverTime , String avgTouchAngle , String touchType , String perfRank ,
            String runRank , String breakRank , String bounceRank , String avgShotDist , String maxShotDist ,
            String handle , String calorie , String trail,String header,String stadiumType) {
        MoveUploadInfo info = new MoveUploadInfo(
                vipId ,sn ,footer ,longitude ,latitude ,address ,
               beginTime ,spend ,picture ,endTime ,totalDist ,totalStep ,
               totalHorDist ,totalVerDist ,totalTime ,totalActiveTime ,
               activeRate ,avgSpeed ,maxSpeed ,spurtCount ,breakinCount ,
               breakinAvgTime ,verJumpPoint ,verJumpCount ,verJumpAvgHigh ,
               verJumpMaxHigh ,avgHoverTime ,avgTouchAngle ,touchType ,perfRank ,
               runRank ,breakRank ,bounceRank ,avgShotDist ,maxShotDist ,
               handle ,calorie ,trail,header,stadiumType);
        String info_str = info.toJsonStr();
        return info_str;
    }

    public static String createGetAccountDataMainRequest(String user_account) {
        GetAccountDataInfo info = new GetAccountDataInfo(user_account);
        String info_str = info.toJsonStr();
        return info_str;
    }

    public static String createModifySingleUserInfoMainRequest(String input_key, String input_value) {
        ModifySingleUserDataInfo info = new ModifySingleUserDataInfo(input_key, input_value);
        String info_str = info.toJsonStr();
        return info_str;
    }

    public static String createModifyAllUserInfoMainRequest(String name, String nick, String gender, String age,
                                                            String birthday, String position, String weight,
                                                            String height, String QQ, String img) {
        ModifyAllUserDataInfo info = new ModifyAllUserDataInfo(name, nick, gender, age, birthday, position, weight, height, QQ, img);
        String info_str = info.toJsonStr();
        return info_str;
    }

    public static String createModifyPasswordInfoMainRequest(String old_password, String new_password) {
        ModifyPasswordInfo info = new ModifyPasswordInfo(old_password, new_password);
        String info_str = info.toJsonStr();
        return info_str;
    }

    public static String createForgetPasswordInfoMainRequest(String vip_id, String user_password, String apply_type, String code) {
        ForgetPasswordInfo info = new ForgetPasswordInfo(vip_id, user_password, apply_type, code);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createTrainDetailInfoMainRequest(String id) {
        TrainDetailInfo info = new TrainDetailInfo(id);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createTrainUploadInfoMainRequest(String source,String type,String platform,String say,
                                                          String img,String urls) {
        TrainUploadInfo info = new TrainUploadInfo(source,type,platform,say,
                img,urls);
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createTrainDictInfoMainRequest() {
        TrainDictInfo info = new TrainDictInfo();
        String info_str = info.toJsonStr();
        return info_str;
    }
    public static String createTrainListInfoMainRequest(String id,String pageNo,String pageSize) {
        TrainListInfo info = new TrainListInfo(id,pageNo,pageSize);
        String info_str = info.toJsonStr();
        return info_str;
    }

    public static String createLogoutMainRequest() {
        return "";
    }
}
