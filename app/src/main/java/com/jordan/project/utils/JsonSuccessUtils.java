package com.jordan.project.utils;


import android.content.Context;
import android.util.Log;

import com.jordan.project.JordanApplication;
import com.jordan.project.data.MotionCountData;
import com.jordan.project.data.MotionDetailData;
import com.jordan.project.data.MotionRedarData;
import com.jordan.project.data.TrainDictData;
import com.jordan.project.data.TrainListData;
import com.jordan.project.database.DatabaseService;
import com.jordan.project.entity.BluetoothData;
import com.jordan.project.entity.MoveListData;
import com.jordan.project.entity.MyShoesData;
import com.jordan.project.entity.PlayBallListData;
import com.jordan.project.entity.ReachDetailData;
import com.jordan.project.entity.UserInfoData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 昕 on 2017/2/23.
 */

public class JsonSuccessUtils {
    public static String getVersion(String json) throws JSONException {
        String getVersion = "";
        JSONObject dataJson = new JSONObject(json);
        getVersion = dataJson.getString("newVer");
        return getVersion;
    }

    public static String getVersionLink(String json) throws JSONException {
        String getVersionLink = "";
        JSONObject dataJson = new JSONObject(json);
        getVersionLink = dataJson.getString("link");
        return getVersionLink;
    }

    public static String getImgId(String json) throws JSONException {
        String imgId = "";
        JSONObject dataJson = new JSONObject(json);
        imgId = dataJson.getString("id");
        return imgId;
    }

    public static ReachDetailData getReachDetail(String json) throws JSONException {
        ReachDetailData reachDetailData = new ReachDetailData();
        JSONObject dataJson = new JSONObject(json);
        JSONObject reachJson = dataJson.getJSONObject("reach");
        reachDetailData.setId(reachJson.getString("id"));
        reachDetailData.setCourtId(reachJson.getString("courtId"));
        reachDetailData.setVipId(reachJson.getString("vipId"));
        reachDetailData.setVipImg(reachJson.getString("vipImg"));
        reachDetailData.setTime(reachJson.getString("time"));
        reachDetailData.setDuration(reachJson.getString("duration"));
        reachDetailData.setPeople(reachJson.getString("people"));
        reachDetailData.setType(reachJson.getString("type"));
        reachDetailData.setPicture(reachJson.getString("picture"));
        reachDetailData.setLongitude(reachJson.getString("longitude"));
        reachDetailData.setLatitude(reachJson.getString("latitude"));
        reachDetailData.setProvince(reachJson.getString("province"));
        reachDetailData.setCity(reachJson.getString("city"));
        reachDetailData.setDistrict(reachJson.getString("district"));
        reachDetailData.setStreet(reachJson.getString("street"));
        reachDetailData.setAddress(reachJson.getString("address"));
        reachDetailData.setMobile(reachJson.getString("mobile"));
        reachDetailData.setContact(reachJson.getString("contact"));
        reachDetailData.setJoin(reachJson.getString("join"));
        reachDetailData.setDistance(reachJson.getString("distance"));
        reachDetailData.setCreateTime(reachJson.getString("publish"));
        reachDetailData.setSlogan(reachJson.getString("slogan"));
        reachDetailData.setRemarks(reachJson.getString("remarks"));

        JSONArray joinsArrayJson = dataJson.getJSONArray("joins");
        ArrayList<String> joinImglist = new ArrayList<String>();
        ArrayList<String> joinMobileList = new ArrayList<String>();
        ArrayList<String> joinNickList = new ArrayList<String>();
        for (int a = 0; a < joinsArrayJson.length(); a++) {
            JSONObject joinsJson = joinsArrayJson.getJSONObject(a);
            if (joinsJson.has("joinImg")) {
                joinImglist.add(joinsJson.getString("joinImg"));
            } else {
                joinImglist.add("");
            }
            if (joinsJson.has("joinNick")) {
                joinNickList.add(joinsJson.getString("joinNick"));
            } else {
                joinNickList.add("");
            }
            if (joinsJson.has("joinMobile")) {
                joinMobileList.add(joinsJson.getString("joinMobile"));
            } else {
                joinMobileList.add("");
            }
        }
        reachDetailData.setJoinImgs(joinImglist);
        reachDetailData.setJoinNicks(joinNickList);
        reachDetailData.setJoinMobiles(joinMobileList);
        return reachDetailData;
    }

    public static String getVipId(String json) throws JSONException {
        String id = "";
        JSONObject dataJson = new JSONObject(json);
        JSONObject infoJson = dataJson.getJSONObject("info");
        id = infoJson.getString("id");
        return id;
    }

    public static UserInfoData getUserData(String json) throws JSONException {
        UserInfoData userInfoData = new UserInfoData();
        JSONObject dataJson = new JSONObject(json);
        userInfoData.setAge(dataJson.getString("age"));
        userInfoData.setBirthday(dataJson.getString("birthday"));
        userInfoData.setEmail(dataJson.getString("email"));
        userInfoData.setGender(dataJson.getString("gender"));
        userInfoData.setHeight(dataJson.getString("height"));
        userInfoData.setImg(dataJson.getString("img"));
        userInfoData.setImgId(dataJson.getString("imgId"));
        userInfoData.setMobile(dataJson.getString("mobile"));
        userInfoData.setName(dataJson.getString("name"));
        userInfoData.setNick(dataJson.getString("nick"));
        userInfoData.setPosition(dataJson.getString("position"));
        userInfoData.setQq(dataJson.getString("qq"));
        userInfoData.setUsername(dataJson.getString("username"));
        userInfoData.setWeight(dataJson.getString("weight"));
        return userInfoData;
    }

    public static ArrayList<BluetoothData> getBluetoothList(String json, Context context) throws JSONException {
        ArrayList<BluetoothData> mBluetoothList = new ArrayList<BluetoothData>();
        JSONObject dataJson = new JSONObject(json);
        JSONArray bhtsArrayJson = dataJson.getJSONArray("bhts");
        for (int a = 0; a < bhtsArrayJson.length(); a++) {
            JSONObject bhtsJson = bhtsArrayJson.getJSONObject(a);
            BluetoothData bluetoothData = new BluetoothData();
            bluetoothData.setCreateTime(bhtsJson.getString("createTime"));
            bluetoothData.setId(bhtsJson.getString("id"));
            bluetoothData.setMac(bhtsJson.getString("mac"));
            bluetoothData.setName(bhtsJson.getString("name"));
            bluetoothData.setSn(bhtsJson.getString("sn"));
            LogUtils.showLog("Result", "mBluetoothList" + a + ":" + bluetoothData.toString());
            mBluetoothList.add(bluetoothData);
            DatabaseService.createBluetoothData(bluetoothData.getId(), bluetoothData.getSn(), bluetoothData.getMac(),
                    bluetoothData.getName(), JordanApplication.getUsername(context));
        }
        return mBluetoothList;
    }

    public static ArrayList<PlayBallListData> getJoinList(String json) throws JSONException {
        ArrayList<PlayBallListData> mPlayBallList = new ArrayList<PlayBallListData>();

        JSONObject dataJson = new JSONObject(json);
        JSONArray resultsArrayJson = dataJson.getJSONArray("results");
        for (int a = 0; a < resultsArrayJson.length(); a++) {
            LogUtils.showLog("Result", "getJoinList resultsArrayJson.length():" + resultsArrayJson.length());
            JSONObject resultsJson = resultsArrayJson.getJSONObject(a);
            if (resultsJson.has("reachBall")) {
                JSONObject ballJson = resultsJson.getJSONObject("reachBall");
                PlayBallListData playBallListData = new PlayBallListData();
                playBallListData.setAddress(ballJson.getString("address"));
                playBallListData.setCity(ballJson.getString("city"));
                playBallListData.setContact(ballJson.getString("contact"));
                playBallListData.setCourtId(ballJson.getString("courtId"));
                playBallListData.setDistance(ballJson.getString("distance"));
                playBallListData.setDistrict(ballJson.getString("district"));
                playBallListData.setDuration(ballJson.getString("duration"));
                playBallListData.setId(ballJson.getString("id"));
                playBallListData.setJoin(ballJson.getString("join"));
                playBallListData.setLatitude(ballJson.getString("latitude"));
                playBallListData.setLongitude(ballJson.getString("longitude"));
                playBallListData.setMobile(ballJson.getString("mobile"));
                playBallListData.setPeople(ballJson.getString("people"));
                playBallListData.setPicture(ballJson.getString("picture"));
                playBallListData.setProvince(ballJson.getString("province"));
                playBallListData.setSlogan(ballJson.getString("slogan"));
                playBallListData.setRemarks(ballJson.getString("remarks"));
                playBallListData.setStreet(ballJson.getString("street"));
                playBallListData.setTime(ballJson.getString("time"));
                playBallListData.setType(ballJson.getString("type"));
                playBallListData.setVipId(ballJson.getString("vipId"));
                playBallListData.setVipImg(ballJson.getString("vipImg"));
                mPlayBallList.add(playBallListData);
                LogUtils.showLog("playBallListData", a + ":" + playBallListData.toString());
                LogUtils.showLog("Result", "mPlayBallList" + a + ":" + playBallListData.toString());

            }
        }
        return mPlayBallList;
    }

    public static ArrayList<PlayBallListData> getMotionList(String json) throws JSONException {
        ArrayList<PlayBallListData> mPlayBallList = new ArrayList<PlayBallListData>();

        JSONObject dataJson = new JSONObject(json);
        JSONArray resultsArrayJson = dataJson.getJSONArray("results");
        for (int a = 0; a < resultsArrayJson.length(); a++) {
            JSONObject resultsJson = resultsArrayJson.getJSONObject(a);

            PlayBallListData playBallListData = new PlayBallListData();
            playBallListData.setAddress(resultsJson.getString("address"));
            playBallListData.setCity(resultsJson.getString("city"));
            playBallListData.setContact(resultsJson.getString("contact"));
            playBallListData.setCourtId(resultsJson.getString("courtId"));
            playBallListData.setDistance(resultsJson.getString("distance"));
            playBallListData.setDistrict(resultsJson.getString("district"));
            playBallListData.setDuration(resultsJson.getString("duration"));
            playBallListData.setId(resultsJson.getString("id"));
            playBallListData.setJoin(resultsJson.getString("join"));
            playBallListData.setLatitude(resultsJson.getString("latitude"));
            playBallListData.setLongitude(resultsJson.getString("longitude"));
            playBallListData.setMobile(resultsJson.getString("mobile"));
            playBallListData.setPeople(resultsJson.getString("people"));
            playBallListData.setPicture(resultsJson.getString("picture"));
            playBallListData.setProvince(resultsJson.getString("province"));
            playBallListData.setRemarks(resultsJson.getString("remarks"));
            playBallListData.setStreet(resultsJson.getString("street"));
            String time = resultsJson.getString("time") + "000";
            playBallListData.setTime(time);
            playBallListData.setType(resultsJson.getString("type"));
            playBallListData.setVipId(resultsJson.getString("vipId"));
            playBallListData.setVipImg(resultsJson.getString("vipImg"));
            LogUtils.showLog("Result", "mPlayBallList-" + a + ":" + mPlayBallList.toString());
            mPlayBallList.add(playBallListData);
        }
        return mPlayBallList;
    }

    public static MotionCountData getMoveCount(String json) throws JSONException {
        MotionCountData motionCountData = new MotionCountData();

        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy/MM/dd");

        JSONObject dataJson = new JSONObject(json);
        motionCountData.setCount(dataJson.getString("count"));
        JSONArray resultsArrayJson = dataJson.getJSONArray("results");
        for (int a = 0; a < resultsArrayJson.length(); a++) {
            JSONObject resultsJson = resultsArrayJson.getJSONObject(a);
            motionCountData.setId(resultsJson.getString("id"));
            long time = Long.valueOf(resultsJson.getString("beginTime")) * 1000;
            Date date = new Date(time);
            motionCountData.setTime(sdfYear.format(date));
        }
        return motionCountData;
    }

    public static ArrayList<MoveListData> getMoveList(String json) throws JSONException {
        ArrayList<MoveListData> mMoveListData = new ArrayList<MoveListData>();

        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");

        JSONObject dataJson = new JSONObject(json);
        JSONArray resultsArrayJson = dataJson.getJSONArray("results");
        for (int a = 0; a < resultsArrayJson.length(); a++) {
            JSONObject resultsJson = resultsArrayJson.getJSONObject(a);

            MoveListData moveListData = new MoveListData();
            long time = 0;
            if (!resultsJson.getString("beginTime").equals("")) {
                time = Long.valueOf(resultsJson.getString("beginTime")) * 1000;
            }
            Date date = new Date(time);
            moveListData.setTimeYear(sdfYear.format(date));
            moveListData.setTimeHour(sdfHour.format(date));
            moveListData.setId(resultsJson.getString("id"));
            moveListData.setTotalDist(resultsJson.getString("totalDist"));
            moveListData.setTotalTime(resultsJson.getString("totalTime"));
            LogUtils.showLog("Result", "mMoveListData-" + a + ":" + moveListData.toString());
            mMoveListData.add(moveListData);
        }
        return mMoveListData;
    }

    public static ArrayList<PlayBallListData> getCreateList(String json) throws JSONException {
        ArrayList<PlayBallListData> mPlayBallList = new ArrayList<PlayBallListData>();

        JSONObject dataJson = new JSONObject(json);
        JSONArray resultsArrayJson = dataJson.getJSONArray("results");
        for (int a = 0; a < resultsArrayJson.length(); a++) {
            JSONObject resultsJson = resultsArrayJson.getJSONObject(a);

            PlayBallListData playBallListData = new PlayBallListData();
            playBallListData.setAddress(resultsJson.getString("address"));
            playBallListData.setCity(resultsJson.getString("city"));
            playBallListData.setContact(resultsJson.getString("contact"));
            playBallListData.setCourtId(resultsJson.getString("courtId"));
            playBallListData.setDistance(resultsJson.getString("distance"));
            playBallListData.setDistrict(resultsJson.getString("district"));
            playBallListData.setDuration(resultsJson.getString("duration"));
            playBallListData.setId(resultsJson.getString("id"));
            playBallListData.setJoin(resultsJson.getString("join"));
            playBallListData.setLatitude(resultsJson.getString("latitude"));
            playBallListData.setLongitude(resultsJson.getString("longitude"));
            playBallListData.setMobile(resultsJson.getString("mobile"));
            playBallListData.setPeople(resultsJson.getString("people"));
            playBallListData.setPicture(resultsJson.getString("picture"));
            playBallListData.setProvince(resultsJson.getString("province"));
            playBallListData.setSlogan(resultsJson.getString("slogan"));
            playBallListData.setRemarks(resultsJson.getString("remarks"));
            playBallListData.setStreet(resultsJson.getString("street"));
            String time = resultsJson.getString("time") + "000";
            playBallListData.setTime(time);
            playBallListData.setType(resultsJson.getString("type"));
            playBallListData.setVipId(resultsJson.getString("vipId"));
            playBallListData.setVipImg(resultsJson.getString("vipImg"));
            LogUtils.showLog("Result", "mPlayBallList-" + a + ":" + mPlayBallList.toString());
            mPlayBallList.add(playBallListData);
        }
        return mPlayBallList;
    }

    public static MotionDetailData getMotionDetail(String json) throws JSONException {
        Log.i("fieldType", "get json:" + json);
        MotionDetailData motionDetailData = new MotionDetailData();
        JSONObject dataJson = new JSONObject(json);
        JSONObject sportJson = dataJson.getJSONObject("sport");
        motionDetailData.setServiceID(sportJson.getString("id"));
        motionDetailData.setLongitude(sportJson.getString("longitude"));
        motionDetailData.setLatitude(sportJson.getString("latitude"));
        motionDetailData.setAddress(sportJson.getString("address"));
        motionDetailData.setBeginTime(sportJson.getString("beginTime"));
        motionDetailData.setSpend(sportJson.getString("spend"));
        motionDetailData.setPicture(sportJson.getString("picture"));
        motionDetailData.setEndTime(sportJson.getString("endTime"));
        motionDetailData.setTotalDist(sportJson.getString("totalDist"));
        motionDetailData.setTotalStep(sportJson.getString("totalStep"));
        motionDetailData.setTotalHorDist(sportJson.getString("totalHorDist"));
        motionDetailData.setTotalVerDist(sportJson.getString("totalVerDist"));
        motionDetailData.setTotalTime(sportJson.getString("totalTime"));
        motionDetailData.setTotalActiveTime(sportJson.getString("totalActiveTime"));
        motionDetailData.setActiveRate(sportJson.getString("activeRate"));
        motionDetailData.setAvgSpeed(sportJson.getString("avgSpeed"));
        motionDetailData.setMaxSpeed(sportJson.getString("maxSpeed"));
        motionDetailData.setSpurtCount(sportJson.getString("spurtCount"));
        motionDetailData.setFooter(sportJson.getString("footer"));
        motionDetailData.setBreakinCount(sportJson.getString("breakinCount"));
        motionDetailData.setBreakinAvgTime(sportJson.getString("breakinAvgTime"));
        motionDetailData.setVerJumpCount(sportJson.getString("verJumpCount"));
        motionDetailData.setVerJumpAvgHigh(sportJson.getString("verJumpAvgHigh"));
        motionDetailData.setAvgHoverTime(sportJson.getString("avgHoverTime"));
        motionDetailData.setAvgTouchAngle(sportJson.getString("avgTouchAngle"));
        motionDetailData.setTouchType(sportJson.getString("touchType"));
        motionDetailData.setPerfRank(sportJson.getString("perfRank"));
        motionDetailData.setRunRank(sportJson.getString("runRank"));
        motionDetailData.setBreakRank(sportJson.getString("breakRank"));
        motionDetailData.setBounceRank(sportJson.getString("bounceRank"));
        motionDetailData.setAvgShotDist(sportJson.getString("avgShotDist"));
        motionDetailData.setMaxShotDist(sportJson.getString("maxShotDist"));
        motionDetailData.setHandle(sportJson.getString("handle"));
        motionDetailData.setCrlorie(sportJson.getString("calorie"));
        motionDetailData.setTrail(sportJson.getString("trail"));
        motionDetailData.setBallType(sportJson.getString("type"));
        motionDetailData.setVerJumpPoint(sportJson.getString("verJumpPoint"));
        String stadiumTpye = "水泥";
        String fieldType = sportJson.getString("fieldType");
        Log.i("fieldType", "get fieldType:" + fieldType);
        if (fieldType.equals("1")) {
            stadiumTpye = "水泥";
        } else if (fieldType.equals("2")) {
            stadiumTpye = "塑胶";
        } else if (fieldType.equals("3")) {
            stadiumTpye = "木地板";
        }
        Log.i("fieldType", "get stadiumTpye:" + stadiumTpye);
        motionDetailData.setStadiumType(stadiumTpye);
        return motionDetailData;
    }

    public static MotionRedarData getMotionRedar(String json) throws JSONException {
        MotionRedarData motionRedarData = new MotionRedarData();
        JSONObject dataJson = new JSONObject(json);
        motionRedarData.setAddSpurt(Float.valueOf(dataJson.getString("addSpurt")));
        motionRedarData.setAgile(Float.valueOf(dataJson.getString("agile")));
        motionRedarData.setExplosiveForce(Float.valueOf(dataJson.getString("explosiveForce")));
        motionRedarData.setLateralShearDirection(Float.valueOf(dataJson.getString("lateralShearDirection")));
        motionRedarData.setVerJump(Float.valueOf(dataJson.getString("verJump")));
        motionRedarData.setHasData(false);
        JSONObject trainJson = dataJson.getJSONObject("train");
        if (trainJson.has("id")) {
            motionRedarData.setId(trainJson.getString("id"));
            motionRedarData.setHasData(true);
        }
        if (trainJson.has("title")) {
            motionRedarData.setTitle(trainJson.getString("title"));
            motionRedarData.setHasData(true);
        }
        if (trainJson.has("intro")) {
            motionRedarData.setIntro(trainJson.getString("intro"));
            motionRedarData.setHasData(true);
        }
        if (trainJson.has("link")) {
            motionRedarData.setLink(trainJson.getString("link"));
            motionRedarData.setHasData(true);
        }
        if (trainJson.has("thumb")) {
            motionRedarData.setThumb(trainJson.getString("thumb"));
            motionRedarData.setHasData(true);
        }
        if (trainJson.has("count")) {
            motionRedarData.setCount(trainJson.getString("count"));
            if(motionRedarData.getCount().equals(""))
                motionRedarData.setCount("0");
            motionRedarData.setHasData(true);
        }
        LogUtils.showLog("Result", "getMotionRedar:" + motionRedarData.toString());
        return motionRedarData;
    }

    public static ArrayList<TrainDictData> getTrainList(String json) throws JSONException {
        ArrayList<TrainDictData> list = new ArrayList<TrainDictData>();
        JSONObject dataJson = new JSONObject(json);
        JSONArray dictsArrayJson = dataJson.getJSONArray("dict");
        for (int a = 0; a < dictsArrayJson.length(); a++) {
            TrainDictData trainDictData = new TrainDictData();
            JSONObject dict = dictsArrayJson.getJSONObject(a);
            trainDictData.setId(dict.getString("id"));
            trainDictData.setName(dict.getString("name"));
            trainDictData.setType(Integer.parseInt(dict.getString("type")));
            list.add(trainDictData);
        }
        return list;
    }

    public static ArrayList<TrainListData> getTrainListData(String json) throws JSONException {
        ArrayList<TrainListData> list = new ArrayList<TrainListData>();
        JSONObject dataJson = new JSONObject(json);
        JSONArray resultsArrayJson = dataJson.getJSONArray("results");
        for (int a = 0; a < resultsArrayJson.length(); a++) {
            TrainListData trainListData = new TrainListData();
            JSONObject results = resultsArrayJson.getJSONObject(a);
            trainListData.setId(results.getString("id"));
            trainListData.setContent(results.getString("content"));
            trainListData.setCount(results.getString("count"));
            trainListData.setIntro(results.getString("intro"));
            trainListData.setPosition(results.getString("position"));
            trainListData.setLink(results.getString("link"));
            trainListData.setTdId(results.getString("tdId"));
            trainListData.setThumb(results.getString("thumb"));
            trainListData.setTitle(results.getString("title"));
            trainListData.setType(results.getString("type"));
            list.add(trainListData);
        }
        return list;
    }

    public static ArrayList<MyShoesData> getBoxShoesList(String json) throws JSONException {
        ArrayList<MyShoesData> list = new ArrayList<MyShoesData>();
        JSONObject dataJson = new JSONObject(json);
        JSONArray boxArrayJson = dataJson.getJSONArray("box");
        for (int a = 0; a < boxArrayJson.length(); a++) {
            JSONObject boxJson = boxArrayJson.getJSONObject(a);
            MyShoesData myShoesData = new MyShoesData();
            myShoesData.setId(boxJson.getString("id"));
            myShoesData.setShoesId(boxJson.getString("shoesId"));
            myShoesData.setCode(boxJson.getString("code"));
            myShoesData.setName(boxJson.getString("name"));
            myShoesData.setPrice(boxJson.getString("price"));
            myShoesData.setColor(boxJson.getString("color"));
            myShoesData.setSize(boxJson.getString("size"));
            myShoesData.setStyle(boxJson.getString("style"));
            myShoesData.setPicture(boxJson.getString("picture"));
            myShoesData.setBuyTime(boxJson.getString("buyTime"));
            if (boxJson.has("intro")) {
                myShoesData.setIntro(boxJson.getString("intro"));
            } else {
                myShoesData.setIntro("");
            }
            myShoesData.setType(1);
            LogUtils.showLog("Result", "list-" + a + ":" + myShoesData.toString());
            list.add(myShoesData);
        }
        return list;
    }

    public static ArrayList<MyShoesData> getRecoShoesList(String json) throws JSONException {
        ArrayList<MyShoesData> list = new ArrayList<MyShoesData>();
        JSONObject dataJson = new JSONObject(json);
        JSONArray recosArrayJson = dataJson.getJSONArray("recos");
        for (int a = 0; a < recosArrayJson.length(); a++) {
            JSONObject recosJson = recosArrayJson.getJSONObject(a);
            MyShoesData myShoesData = new MyShoesData();
            myShoesData.setId(recosJson.getString("id"));
            myShoesData.setCode(recosJson.getString("code"));
            myShoesData.setName(recosJson.getString("name"));
            myShoesData.setPrice(recosJson.getString("price"));
            myShoesData.setColor(recosJson.getString("color"));
            myShoesData.setSize(recosJson.getString("size"));
            myShoesData.setStyle(recosJson.getString("style"));
            myShoesData.setPicture(recosJson.getString("thumb"));
            myShoesData.setIntro(recosJson.getString("intro"));
            myShoesData.setStyleNumber(recosJson.getString("styleNumber"));
            myShoesData.setMarketTime(recosJson.getString("marketTime"));
            myShoesData.setForPeople(recosJson.getString("forPeople"));
            myShoesData.setForSpace(recosJson.getString("forSpace"));
            myShoesData.setForPosition(recosJson.getString("forPosition"));
            myShoesData.setFunction(recosJson.getString("function"));
            myShoesData.setStat(recosJson.getString("stat"));
            myShoesData.setPicDesc(recosJson.getString("picDesc"));
            myShoesData.setTextDesc(recosJson.getString("textDesc"));
            myShoesData.setType(2);
            LogUtils.showLog("Result", "list-" + a + ":" + myShoesData.toString());
            list.add(myShoesData);
        }
        return list;
    }

    public static ArrayList<MyShoesData> getShoesList(String json) throws JSONException {
        ArrayList<MyShoesData> list = new ArrayList<MyShoesData>();
        JSONObject dataJson = new JSONObject(json);
        JSONArray resultsArrayJson = dataJson.getJSONArray("results");
        for (int a = 0; a < resultsArrayJson.length(); a++) {
            JSONObject resultsJson = resultsArrayJson.getJSONObject(a);
            MyShoesData myShoesData = new MyShoesData();
            myShoesData.setId(resultsJson.getString("id"));
            myShoesData.setCode(resultsJson.getString("code"));
            myShoesData.setName(resultsJson.getString("name"));
            myShoesData.setPrice(resultsJson.getString("price"));
            myShoesData.setColor(resultsJson.getString("color"));
            myShoesData.setSize(resultsJson.getString("size"));
            myShoesData.setStyle(resultsJson.getString("style"));
            myShoesData.setPicture(resultsJson.getString("thumb"));
            myShoesData.setIntro(resultsJson.getString("intro"));
            myShoesData.setStyleNumber(resultsJson.getString("styleNumber"));
            myShoesData.setMarketTime(resultsJson.getString("marketTime"));
            myShoesData.setForPeople(resultsJson.getString("forPeople"));
            myShoesData.setForSpace(resultsJson.getString("forSpace"));
            myShoesData.setForPosition(resultsJson.getString("forPosition"));
            myShoesData.setFunction(resultsJson.getString("function"));
            myShoesData.setStat(resultsJson.getString("stat"));
            myShoesData.setPicDesc(resultsJson.getString("picDesc"));
            myShoesData.setTextDesc(resultsJson.getString("textDesc"));
            myShoesData.setType(0);
            LogUtils.showLog("Result", "list-" + a + ":" + myShoesData.toString());
            list.add(myShoesData);
        }
        return list;
    }

    public static String parsePosition(String englishPostion) {
        String position = englishPostion;
        //(得分后卫:SG， 控球后卫:PG， 小前锋:SF， 大前锋:PF，中锋:C)
        if (englishPostion.equals("SG")) {
            position = "得分后卫";
        } else if (englishPostion.equals("PG")) {
            position = "控球后卫";
        } else if (englishPostion.equals("SF")) {
            position = "小前锋";
        } else if (englishPostion.equals("PF")) {
            position = "大前锋";
        } else if (englishPostion.equals("C")) {
            position = "中锋";
        }
        return position;
    }

    public static String decodeEnglishPosition(String position) {
        String englishPostion = position;
        //(得分后卫:SG， 控球后卫:PG， 小前锋:SF， 大前锋:PF，中锋:C)
        if (englishPostion.equals("得分后卫")) {
            englishPostion = "SG";
        } else if (englishPostion.equals("控球后卫")) {
            englishPostion = "PG";
        } else if (englishPostion.equals("小前锋")) {
            englishPostion = "SF";
        } else if (englishPostion.equals("大前锋")) {
            englishPostion = "PF";
        } else if (englishPostion.equals("中锋")) {
            englishPostion = "C";
        }
        return englishPostion;
    }
}
