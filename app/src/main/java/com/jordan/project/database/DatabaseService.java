package com.jordan.project.database;


import android.database.Cursor;

import com.jordan.project.data.MotionDetailData;
import com.jordan.project.data.MotionUploadData;
import com.jordan.project.entity.BluetoothData;
import com.jordan.project.entity.MoveListData;
import com.jordan.project.entity.PlayBallListData;
import com.jordan.project.entity.UserInfoData;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class DatabaseService {
	/**
	 * 存储运动缓存数据
	 */
	public static boolean createMotionBluetoothData(String vipId, String sn, String footer, String longitude, String latitude, String address,
													String beginTime, String spend, String picture, String endTime, String totalDist, String totalStep,
													String totalHorDist, String totalVerDist, String totalTime, String totalActiveTime,
													String activeRate, String avgSpeed, String maxSpeed, String spurtCount, String breakinCount,
													String breakinAvgTime, String verJumpPoint, String verJumpCount, String verJumpAvgHigh,
													String verJumpMaxHigh, String avgHoverTime, String avgTouchAngle, String touchType, String perfRank,
													String runRank, String breakRank, String bounceRank, String avgShotDist, String maxShotDist,
													String handle, String calorie, String trail,String ballType,String header,String stadiumType){

		LogUtils.showLog("database", "createMotionBluetoothData vipId:"+vipId);
		String where_cause = DatabaseObject.MotionBluetoothDataTable.FIELD_VIP_ID
				+ " =? And "
				+DatabaseObject.MotionBluetoothDataTable.FIELD_BEGIN_TIME
				+ " =? ";
		String[] where_args = new String[] { vipId,beginTime };

		Cursor cursor = null;
		try {
			cursor=DatabaseUtils.getRecordsFromTable(DatabaseObject.MOTION_BLUETOOTH_DATA,
					null, DatabaseObject.MotionBluetoothDataTable.PROJECT,where_cause,
					where_args, null);
			if (cursor != null && cursor.moveToFirst()) {
				//update?
				DatabaseUtils.updateRecordFromTable(DatabaseObject.MOTION_BLUETOOTH_DATA, null,
						DatabaseObject.MotionBluetoothDataTable.getContentValues(vipId, sn, footer, longitude, latitude, address,
								beginTime, spend, picture, endTime, totalDist, totalStep,
								totalHorDist, totalVerDist, totalTime, totalActiveTime,
								activeRate, avgSpeed, maxSpeed, spurtCount, breakinCount,
								breakinAvgTime, verJumpPoint, verJumpCount, verJumpAvgHigh,
								verJumpMaxHigh, avgHoverTime, avgTouchAngle, touchType, perfRank,
								runRank, breakRank, bounceRank, avgShotDist, maxShotDist,
								handle, calorie, trail,ballType,header,stadiumType),
						where_cause, where_args);
				LogUtils.showLog("database", "createMotionBluetoothData UPDATE");
				return true;
			} else {
				DatabaseUtils.insertRecordIntoTable(
						DatabaseObject.MotionBluetoothDataTable.getContentValues(vipId, sn, footer, longitude, latitude, address,
								beginTime, spend, picture, endTime, totalDist, totalStep,
								totalHorDist, totalVerDist, totalTime, totalActiveTime,
								activeRate, avgSpeed, maxSpeed, spurtCount, breakinCount,
								breakinAvgTime, verJumpPoint, verJumpCount, verJumpAvgHigh,
								verJumpMaxHigh, avgHoverTime, avgTouchAngle, touchType, perfRank,
								runRank, breakRank, bounceRank, avgShotDist, maxShotDist,
								handle, calorie, trail,ballType,header,stadiumType),
						DatabaseObject.MOTION_BLUETOOTH_DATA, null);
				LogUtils.showLog("database", "createMotionBluetoothData INSERT");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return false;
	}
	//存储MOTIONLIST
	public static boolean createMotionList(String serviceId,String username, String yearTime, String hourTime,
										   String type,String timeLong,String distance){

		LogUtils.showLog("database", "createMotionList serviceId:"+serviceId);
		String where_cause = DatabaseObject.MotionListTable.FIELD_MOTION_LIST_SERVICE_ID
				+ " =? And "
				+DatabaseObject.MotionListTable.FIELD_MOTION_LIST_USERNAME
				+ " =? And "
				+DatabaseObject.MotionListTable.FIELD_MOTION_LIST_TYPE
				+ " =? ";
		String[] where_args = new String[] { serviceId,username,type };

		Cursor cursor = null;
		try {
			cursor=DatabaseUtils.getRecordsFromTable(DatabaseObject.MOTION_LIST,
					null, DatabaseObject.MotionListTable.PROJECT,where_cause,
					where_args, null);
			if (cursor != null && cursor.moveToFirst()) {
				//update?
				DatabaseUtils.updateRecordFromTable(DatabaseObject.MOTION_LIST, null,
						DatabaseObject.MotionListTable.getContentValues(serviceId,username, yearTime, hourTime,
								type,timeLong,distance),
						where_cause, where_args);
				LogUtils.showLog("database", "createMotionList UPDATE");
				return true;
			} else {
				DatabaseUtils.insertRecordIntoTable(
						DatabaseObject.MotionListTable.getContentValues(serviceId,username, yearTime, hourTime,
								type,timeLong,distance),
						DatabaseObject.MOTION_LIST, null);
				LogUtils.showLog("database", "createMotionList INSERT");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return false;
	}
    //存储用户信息
    public static boolean createUserInfo(String username,
										 String name, String nick, String gender, String age, String birthday,
										 String position, String weight, String height, String qq, String mobile, String email,
										 String img,String imgId){

		LogUtils.showLog("database", "createUserInfo mobile:"+mobile);
		String where_cause = DatabaseObject.UserInfoTable.FIELD_USER_INFO_MOBILE
				+ " =? ";
		String[] where_args = new String[] { mobile };

		Cursor cursor = null;
		try {
			cursor=DatabaseUtils.getRecordsFromTable(DatabaseObject.USER_INFO,
					null, DatabaseObject.UserInfoTable.PROJECT,where_cause,
					where_args, null);
			if (cursor != null && cursor.moveToFirst()) {
				//update?
				DatabaseUtils.updateRecordFromTable(DatabaseObject.USER_INFO, null,
						DatabaseObject.UserInfoTable.getContentValues(username,name,nick,gender,
								age,birthday,position,weight,height,qq,mobile,email,img,imgId),
						where_cause, where_args);
				LogUtils.showLog("database", "createUserInfo UPDATE");
				return true;
			} else {
				DatabaseUtils.insertRecordIntoTable(DatabaseObject.UserInfoTable
						.getContentValues(username,name,nick,gender,
						age,birthday,position,weight,height,qq,mobile,email,img,imgId),
						DatabaseObject.USER_INFO, null);
				LogUtils.showLog("database", "createUserInfo INSERT");
				return true;
			}
		} catch (Exception e) {
			LogUtils.showLog("database", "createUserInfo Exception");
			e.printStackTrace();
		} finally {
			LogUtils.showLog("database", "createUserInfo finally");
			if (cursor != null)
				cursor.close();
		}
		return false;
	}

	/**
	 * 存储运动信息
	 */
	public static boolean createMotionDetail(
			String serviceID,String longitude,String latitude,String address,
			String beginTime,String spend,String picture,String endTime,String totalDist,String totalStep,
			String totalHorDist,String totalVerDist,String totalTime,String totalActiveTime,
			String activeRate,String avgSpeed,String maxSpeed,String spurtCount,String breakinCount,
			String breakinAvgTime,String verJumpCount,String verJumpAvgHigh,String avgHoverTime,
			String avgTouchAngle,String touchType,String perfRank,String runRank,String breakRank,
			String bounceRank,String avgShotDist,String maxShotDist,String handle,String crlorie,
			String trail,String verJumpPoint,String bluetoothFoot,String ballType,String stadiumType){

		LogUtils.showLog("database", "createMotionDetail serviceID:"+serviceID);
		String where_cause = DatabaseObject.MotionDetailTable.FIELD_MOTION_DETAIL_SERVICE_ID
				+ " =? ";
		String[] where_args = new String[] { serviceID };

		Cursor cursor = null;
		try {
			cursor=DatabaseUtils.getRecordsFromTable(DatabaseObject.MOTION_DETAIL,
					null, DatabaseObject.MotionDetailTable.PROJECT,where_cause,
					where_args, null);
			if (cursor != null && cursor.moveToFirst()) {
				//update?
				DatabaseUtils.updateRecordFromTable(DatabaseObject.MOTION_DETAIL, null,
						DatabaseObject.MotionDetailTable.getContentValues(
								serviceID,longitude,latitude,address,
								beginTime,spend,picture,endTime,totalDist,totalStep,
								totalHorDist,totalVerDist,totalTime,totalActiveTime,
								activeRate,avgSpeed,maxSpeed,spurtCount,breakinCount,
								breakinAvgTime,verJumpCount,verJumpAvgHigh,avgHoverTime,
								avgTouchAngle,touchType,perfRank,runRank,breakRank,
								bounceRank,avgShotDist,maxShotDist,handle,crlorie,trail,verJumpPoint,bluetoothFoot,ballType,stadiumType),
						where_cause, where_args);
				LogUtils.showLog("database", "createMotionDetail UPDATE");
				return true;
			} else {
				DatabaseUtils.insertRecordIntoTable(
						DatabaseObject.MotionDetailTable.getContentValues(
								serviceID,longitude,latitude,address,
								beginTime,spend,picture,endTime,totalDist,totalStep,
								totalHorDist,totalVerDist,totalTime,totalActiveTime,
								activeRate,avgSpeed,maxSpeed,spurtCount,breakinCount,
								breakinAvgTime,verJumpCount,verJumpAvgHigh,avgHoverTime,
								avgTouchAngle,touchType,perfRank,runRank,breakRank,
								bounceRank,avgShotDist,maxShotDist,handle,crlorie,trail,verJumpPoint,bluetoothFoot,ballType,stadiumType),
						DatabaseObject.MOTION_DETAIL, null);
				LogUtils.showLog("database", "createMotionDetail INSERT");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return false;
	}
	//存储约球记录
	public static boolean createReachDetailInfo(String serviceID,String courtID, String vipID, String time, String duration, String people,
										 String type, String picture, String longitude, String latitude, String province, String city, String district,
										 String street,String address,String mobile,String contact,String join,String distance,String remarks,String username,String vipImg,String slogan){

		LogUtils.showLog("databases", "createReachDetailInfo username:"+username);
		LogUtils.showLog("databases", "createReachDetailInfo serviceID:"+serviceID);
		LogUtils.showLog("databases", "createReachDetailInfo vipID:"+vipID);
		String where_cause = DatabaseObject.ReachDetailTable.FIELD_REACH_DETIAL_SERVICE_ID
				+ " =? And "
				+DatabaseObject.ReachDetailTable.FIELD_REACH_DETIAL_VIP_ID
				+ " =? And "
				+DatabaseObject.ReachDetailTable.FIELD_REACH_DETIAL_USERNAME
				+ " =? ";
		String[] where_args = new String[] { serviceID,vipID,username };

		Cursor cursor = null;
		try {
			cursor=DatabaseUtils.getRecordsFromTable(DatabaseObject.REACH_DETAIL,
					null, DatabaseObject.ReachDetailTable.PROJECT,where_cause,
					where_args, null);
			if (cursor != null && cursor.moveToFirst()) {
				//update?
				DatabaseUtils.updateRecordFromTable(DatabaseObject.REACH_DETAIL,
						null,DatabaseObject.ReachDetailTable.getContentValues(serviceID,courtID, vipID, time, duration, people,
						type, picture,longitude,latitude,province,city,district,
						street,address,mobile,contact,join,distance,remarks,username,vipImg,slogan),where_cause,where_args);
				LogUtils.showLog("databases", "createReachDetailInfo UPDATE");
				return true;
			} else {
				DatabaseUtils.insertRecordIntoTable(DatabaseObject.ReachDetailTable.getContentValues(serviceID,courtID, vipID, time, duration, people,
						type, picture,longitude,latitude,province,city,district,
						street,address,mobile,contact,join,district,remarks,username,vipImg,slogan),
						DatabaseObject.REACH_DETAIL, null);
				LogUtils.showLog("databases", "createReachDetailInfo INSERT");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return false;
	}

	//存储蓝牙信息
	public static boolean createBluetoothData(String serviceID,String sn, String mac,String time,String username){

		LogUtils.showLog("databases", "createBluetoothData username:"+username);
		LogUtils.showLog("databases", "createBluetoothData serviceID:"+serviceID);
		LogUtils.showLog("databases", "createBluetoothData sn:"+sn);
		LogUtils.showLog("databases", "createBluetoothData mac:"+mac);
		LogUtils.showLog("databases", "createBluetoothData time:"+time);
		String where_cause = DatabaseObject.BluetoothListTable.FIELD_BLUETOOTH_LIST_SERVICE_ID
				+ " =? And "
				+DatabaseObject.BluetoothListTable.FIELD_BLUETOOTH_LIST_SN
				+ " =? And "
				+DatabaseObject.BluetoothListTable.FIELD_BLUETOOTH_LIST_MAC
				+ " =? And "
				+DatabaseObject.BluetoothListTable.FIELD_BLUETOOTH_LIST_USERNAME
				+ " =? ";
		String[] where_args = new String[] { serviceID,sn,mac,username };

		Cursor cursor = null;
		try {
			cursor=DatabaseUtils.getRecordsFromTable(DatabaseObject.BLUETOOTH_LIST,
					null, DatabaseObject.BluetoothListTable.PROJECT,where_cause,
					where_args, null);
			if (cursor != null && cursor.moveToFirst()) {
				//update?
				DatabaseUtils.updateRecordFromTable(DatabaseObject.BLUETOOTH_LIST,
						null,DatabaseObject.BluetoothListTable.getContentValues(
								serviceID,sn,mac,time,username),where_cause,where_args);
				LogUtils.showLog("databases", "createBluetoothData UPDATE");
				return true;
			} else {
				DatabaseUtils.insertRecordIntoTable(DatabaseObject.BluetoothListTable.getContentValues(
						serviceID,sn,mac,time,username),
						DatabaseObject.BLUETOOTH_LIST, null);
				LogUtils.showLog("databases", "createBluetoothData INSERT");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return false;
	}

	//查询用户信息

	public static UserInfoData findUserInfo(String mobile){
		LogUtils.showLog("database", "findUserInfo mobile:"+mobile);
		UserInfoData userInfoData=null;
		String where_cause = DatabaseObject.UserInfoTable.FIELD_USER_INFO_MOBILE
				+ " =? ";
		String[] where_args = new String[] { mobile };

		Cursor cursor=null;
		try {
			cursor=DatabaseUtils.getRecordsFromTable(DatabaseObject.USER_INFO,
					null, DatabaseObject.UserInfoTable.PROJECT, where_cause,
					where_args, null);
			while(cursor.moveToNext()){
				userInfoData = new UserInfoData();
				userInfoData.setAge(cursor.getString(DatabaseObject.UserInfoTable.INDEX_USER_INFO_AGE));
				userInfoData.setBirthday(cursor.getString(DatabaseObject.UserInfoTable.INDEX_USER_INFO_BIRTHDAY));
				userInfoData.setEmail(cursor.getString(DatabaseObject.UserInfoTable.INDEX_USER_INFO_EMAIL));
				userInfoData.setGender(cursor.getString(DatabaseObject.UserInfoTable.INDEX_USER_INFO_GENDER));
				userInfoData.setHeight(cursor.getString(DatabaseObject.UserInfoTable.INDEX_USER_INFO_HEIGHT));
				userInfoData.setImg(cursor.getString(DatabaseObject.UserInfoTable.INDEX_USER_INFO_IMG));
				userInfoData.setMobile(cursor.getString(DatabaseObject.UserInfoTable.INDEX_USER_INFO_MOBILE));
				userInfoData.setName(cursor.getString(DatabaseObject.UserInfoTable.INDEX_USER_INFO_NAME));
				userInfoData.setNick(cursor.getString(DatabaseObject.UserInfoTable.INDEX_USER_INFO_NICK));
				String position = JsonSuccessUtils.parsePosition(cursor.getString(DatabaseObject.
						UserInfoTable.INDEX_USER_INFO_POSITION));
				userInfoData.setPosition(position);
				userInfoData.setQq(cursor.getString(DatabaseObject.UserInfoTable.INDEX_USER_INFO_QQ));
				userInfoData.setUsername(cursor.getString(DatabaseObject.UserInfoTable.INDEX_USER_INFO_USERNAME));
				userInfoData.setWeight(cursor.getString(DatabaseObject.UserInfoTable.INDEX_USER_INFO_WEIGHT));
				userInfoData.setImgId(cursor.getString(DatabaseObject.UserInfoTable.INDEX_USER_INFO_IMGID));
				LogUtils.showLog("database","findUserInfo userInfoData:"+userInfoData.toString());
			}
			LogUtils.showLog("database","findUserInfo over:");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.showLog("database","findUserInfo exception:");
		}finally{
			LogUtils.showLog("database","findUserInfo finally:");
			if(cursor != null ) cursor.close();
		}
		return userInfoData;
	}

	public static ArrayList<PlayBallListData> findNowPlayBallList(String username){
		ArrayList<PlayBallListData> mPlayBallList = new ArrayList<PlayBallListData>();
		LogUtils.showLog("database", "findPlayBallList username:"+username);
		String where_cause = DatabaseObject.ReachDetailTable.FIELD_REACH_DETIAL_USERNAME
				+ " =? ";
		String[] where_args = new String[] { username };

		Cursor cursor=null;
		try {
			cursor=DatabaseUtils.getRecordsFromTable(DatabaseObject.REACH_DETAIL,
					null, DatabaseObject.ReachDetailTable.PROJECT, where_cause,
					where_args, null);
			while(cursor.moveToNext()){
				//判断时间戳
				String time=cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_TIME);
				if(time!=null) {
					long longtime = Long.parseLong(time);
					long nowTime = new Date().getTime();
					long startTime = longtime*1000;
					LogUtils.showLog("database", "time:" + time);
					LogUtils.showLog("database", "startTime:" + startTime);
					LogUtils.showLog("database", "nowTime:" + nowTime);
					if (startTime > nowTime) {
						PlayBallListData mPlayBallListData = new PlayBallListData();
						mPlayBallListData.setAddress(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_ADDRESS));
						mPlayBallListData.setCity(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_CITY));
						mPlayBallListData.setContact(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_CONTACT));
						mPlayBallListData.setCourtId(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_COURT_ID));
						mPlayBallListData.setDistance(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_DISTANCE));
						mPlayBallListData.setDistrict(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_DISTRICT));
						mPlayBallListData.setDuration(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_DURATION));
						mPlayBallListData.setId(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_SERVICE_ID));
						mPlayBallListData.setJoin(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_JOIN));
						mPlayBallListData.setLatitude(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_LATITUDE));
						mPlayBallListData.setLongitude(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_LONGITUDE));
						mPlayBallListData.setMobile(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_MOBILE));
						mPlayBallListData.setPeople(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_PEOPLE));
						mPlayBallListData.setPicture(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_PICTURE));
						mPlayBallListData.setProvince(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_PROVINCE));
						mPlayBallListData.setRemarks(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_REMARKS));
						mPlayBallListData.setStreet(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_STREET));
						mPlayBallListData.setTime(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_TIME));
						mPlayBallListData.setType(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_TYPE));
						mPlayBallListData.setVipId(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_VIP_ID));
						mPlayBallListData.setVipImg(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_VIP_IMG));
						mPlayBallList.add(mPlayBallListData);
					}
				}
				LogUtils.showLog("database","findPlayBallList userdata:");
			}
			LogUtils.showLog("database","findPlayBallList over:");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.showLog("database","findPlayBallList exception:");
		}finally{
			LogUtils.showLog("database","findPlayBallList finally:");
			if(cursor != null ) cursor.close();
		}
		return mPlayBallList;
	}

	public static ArrayList<MoveListData> findMotionList(String username, String type){
		ArrayList<MoveListData> mMoveListData = new ArrayList<MoveListData>();
		LogUtils.showLog("database", "findMotionList username:"+username);
		LogUtils.showLog("database", "findMotionList type:"+type);
		String where_cause = DatabaseObject.MotionListTable.FIELD_MOTION_LIST_USERNAME
				+ " =? And "
				+DatabaseObject.MotionListTable.FIELD_MOTION_LIST_TYPE
				+ " =? ";
		String[] where_args = new String[] { username,type };

		Cursor cursor=null;
		try {
			cursor=DatabaseUtils.getRecordsFromTable(DatabaseObject.MOTION_LIST,
					null, DatabaseObject.MotionListTable.PROJECT, where_cause,
					where_args, null);
			while(cursor.moveToNext()){
				MoveListData moveListData = new MoveListData();
				moveListData.setId(cursor.getString(DatabaseObject.MotionListTable.INDEX_MOTION_LIST_SERVICE_ID));
				moveListData.setTimeHour(cursor.getString(DatabaseObject.MotionListTable.INDEX_MOTION_LIST_HOUR_TIME));
				moveListData.setTimeYear(cursor.getString(DatabaseObject.MotionListTable.INDEX_MOTION_LIST_YEAR_TIME));
				moveListData.setTotalTime(cursor.getString(DatabaseObject.MotionListTable.INDEX_MOTION_LIST_TIME_LONG));
				moveListData.setTotalDist(cursor.getString(DatabaseObject.MotionListTable.INDEX_MOTION_LIST_DISTANCE));
				mMoveListData.add(moveListData);
				LogUtils.showLog("database","findMotionList userdata:");
				LogUtils.showLog("database", "findMotionList:"+mMoveListData.toString());
			}
			LogUtils.showLog("database","findMotionList over:");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.showLog("database","findMotionList exception:");
		}finally{
			LogUtils.showLog("database","findMotionList finally:");
			if(cursor != null ) cursor.close();
		}
		return mMoveListData;
	}
	public static ArrayList<BluetoothData> findBluetoothList(String username){
		ArrayList<BluetoothData> mBluetoothList = new ArrayList<BluetoothData>();
		LogUtils.showLog("database", "findBluetoothList username:"+username);
		String where_cause = DatabaseObject.BluetoothListTable.FIELD_BLUETOOTH_LIST_USERNAME
				+ " =? ";
		String[] where_args = new String[] { username };

		Cursor cursor=null;
		try {
			cursor=DatabaseUtils.getRecordsFromTable(DatabaseObject.BLUETOOTH_LIST,
					null, DatabaseObject.BluetoothListTable.PROJECT, where_cause,
					where_args, null);
			while(cursor.moveToNext()){
				BluetoothData bluetoothData = new BluetoothData();
				bluetoothData.setName(cursor.getString(DatabaseObject.BluetoothListTable.INDEX_BLUETOOTH_LIST_CREATE_TIME));
				bluetoothData.setId(cursor.getString(DatabaseObject.BluetoothListTable.IINDEX_BLUETOOTH_LIST_SERVICE_ID));
				bluetoothData.setMac(cursor.getString(DatabaseObject.BluetoothListTable.INDEX_BLUETOOTH_LIST_MAC));
				bluetoothData.setSn(cursor.getString(DatabaseObject.BluetoothListTable.INDEX_BLUETOOTH_LIST_SN));
				mBluetoothList.add(bluetoothData);
				LogUtils.showLog("database","findBluetoothList userdata:");
				LogUtils.showLog("database", "findBluetoothList:"+bluetoothData.toString());
			}
			LogUtils.showLog("database","findBluetoothList over:");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.showLog("database","findBluetoothList exception:");
		}finally{
			LogUtils.showLog("database","findBluetoothList finally:");
			if(cursor != null ) cursor.close();
		}
		return mBluetoothList;
	}
	public static HashMap<String,BluetoothData> findBluetooths(String username){
		HashMap<String,BluetoothData> mBluetooths = new HashMap<String,BluetoothData>();
		LogUtils.showLog("database", "findBluetoothList username:"+username);
		String where_cause = DatabaseObject.BluetoothListTable.FIELD_BLUETOOTH_LIST_USERNAME
				+ " =? ";
		String[] where_args = new String[] { username };

		Cursor cursor=null;
		try {
			cursor=DatabaseUtils.getRecordsFromTable(DatabaseObject.BLUETOOTH_LIST,
					null, DatabaseObject.BluetoothListTable.PROJECT, where_cause,
					where_args, null);
			while(cursor.moveToNext()){
				BluetoothData bluetoothData = new BluetoothData();
				bluetoothData.setCreateTime(cursor.getString(DatabaseObject.BluetoothListTable.INDEX_BLUETOOTH_LIST_CREATE_TIME));
				bluetoothData.setId(cursor.getString(DatabaseObject.BluetoothListTable.IINDEX_BLUETOOTH_LIST_SERVICE_ID));
				bluetoothData.setMac(cursor.getString(DatabaseObject.BluetoothListTable.INDEX_BLUETOOTH_LIST_MAC));
				bluetoothData.setSn(cursor.getString(DatabaseObject.BluetoothListTable.INDEX_BLUETOOTH_LIST_SN));
				mBluetooths.put(bluetoothData.getSn(),bluetoothData);
				LogUtils.showLog("database","findBluetoothList userdata:");
				LogUtils.showLog("database", "findBluetoothList:"+bluetoothData.toString());
			}
			LogUtils.showLog("database","findBluetoothList over:");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.showLog("database","findBluetoothList exception:");
		}finally{
			LogUtils.showLog("database","findBluetoothList finally:");
			if(cursor != null ) cursor.close();
		}
		return mBluetooths;
	}
	public static ArrayList<PlayBallListData> findPlayBallList(String username){
		ArrayList<PlayBallListData> mPlayBallList = new ArrayList<PlayBallListData>();
		LogUtils.showLog("database", "findPlayBallList username:"+username);
		String where_cause = DatabaseObject.ReachDetailTable.FIELD_REACH_DETIAL_USERNAME
				+ " =? ";
		String[] where_args = new String[] { username };

		Cursor cursor=null;
		try {
			cursor=DatabaseUtils.getRecordsFromTable(DatabaseObject.REACH_DETAIL,
					null, DatabaseObject.ReachDetailTable.PROJECT, where_cause,
					where_args, null);
			while(cursor.moveToNext()){
				PlayBallListData mPlayBallListData= new PlayBallListData();
				mPlayBallListData.setAddress(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_ADDRESS));
				mPlayBallListData.setCity(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_CITY));
				mPlayBallListData.setContact(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_CONTACT));
				mPlayBallListData.setCourtId(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_COURT_ID));
				mPlayBallListData.setDistance(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_DISTANCE));
				mPlayBallListData.setDistrict(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_DISTRICT));
				mPlayBallListData.setDuration(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_DURATION));
				mPlayBallListData.setId(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_SERVICE_ID));
				mPlayBallListData.setJoin(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_JOIN));
				mPlayBallListData.setLatitude(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_LATITUDE));
				mPlayBallListData.setLongitude(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_LONGITUDE));
				mPlayBallListData.setMobile(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_MOBILE));
				mPlayBallListData.setPeople(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_PEOPLE));
				mPlayBallListData.setPicture(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_PICTURE));
				mPlayBallListData.setProvince(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_PROVINCE));
				mPlayBallListData.setRemarks(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_REMARKS));
				mPlayBallListData.setStreet(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_STREET));
				mPlayBallListData.setTime(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_TIME));
				mPlayBallListData.setType(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_TYPE));
				mPlayBallListData.setVipId(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_VIP_ID));
                mPlayBallListData.setVipImg(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_VIP_IMG));
				mPlayBallListData.setSlogan(cursor.getString(DatabaseObject.ReachDetailTable.INDEX_REACH_DETIAL_SLOGAN));
				mPlayBallList.add(mPlayBallListData);
				LogUtils.showLog("database","findPlayBallList userdata:");
				LogUtils.showLog("databases", "findPlayBallList:"+mPlayBallListData.toString());
			}
			LogUtils.showLog("database","findPlayBallList over:");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.showLog("database","findPlayBallList exception:");
		}finally{
			LogUtils.showLog("database","findPlayBallList finally:");
			if(cursor != null ) cursor.close();
		}
		return mPlayBallList;
	}
	public static MotionDetailData findMotionDetail(String serviceID){
		MotionDetailData motionDetailData=null;
		LogUtils.showLog("database", "findMotionDetail serviceID:"+serviceID);
		String where_cause = DatabaseObject.MotionDetailTable.FIELD_MOTION_DETAIL_SERVICE_ID
				+ " =? ";
		String[] where_args = new String[] { serviceID };

		Cursor cursor = null;
		try {
			cursor=DatabaseUtils.getRecordsFromTable(DatabaseObject.MOTION_DETAIL,
					null, DatabaseObject.MotionDetailTable.PROJECT, where_cause,
					where_args, null);
			while(cursor.moveToNext()){
				motionDetailData = new MotionDetailData();
				motionDetailData.setServiceID(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_SERVICE_ID));
				motionDetailData.setLongitude(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_LONGITUDE));
				motionDetailData.setLatitude(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_LATITUDE));
				motionDetailData.setAddress(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_ADDRESS));
				motionDetailData.setBeginTime(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_BEGIN_TIME));
				motionDetailData.setSpend(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_SPEND));
				motionDetailData.setPicture(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_PICTURE));
				motionDetailData.setEndTime(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_END_TIME));
				motionDetailData.setTotalDist(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_TOTAL_DIST));
				motionDetailData.setTotalStep(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_TOTAL_STEP));
				motionDetailData.setTotalHorDist(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_TOTAL_HOR_DIST));
				motionDetailData.setTotalVerDist(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_TOTAL_VER_DIST));
				motionDetailData.setTotalTime(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_TOTAL_TIME));
				if(motionDetailData.getTotalTime().equals(""))
					motionDetailData.setTotalTime("0");
				motionDetailData.setTotalActiveTime(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_TOTAL_ACTIVE_TIME));
				motionDetailData.setActiveRate(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_ACTIVE_RATE));
				motionDetailData.setAvgSpeed(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_AVG_SPEED));
				motionDetailData.setMaxSpeed(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_MAX_SPEED));
				motionDetailData.setSpurtCount(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_SPURT_COUNT));
				motionDetailData.setBreakinCount(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_BREAKIN_COUNT));
				motionDetailData.setBreakinAvgTime(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_BREAKIN_AVG_TIME));
				motionDetailData.setVerJumpCount(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_VER_JUMP_COUNT));
				motionDetailData.setVerJumpAvgHigh(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_VER_JUMP_AVG_HIGH));
				motionDetailData.setAvgHoverTime(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_AVG_HOVER_TIME));
				motionDetailData.setAvgTouchAngle(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_AVG_TOUCH_ANGLE));
				motionDetailData.setTouchType(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_TOUCH_TYPE));
				motionDetailData.setPerfRank(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_PERF_RANK));
				motionDetailData.setRunRank(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_RUN_RANK));
				motionDetailData.setBreakRank(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_BREAK_RANK));
				motionDetailData.setBounceRank(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_BOUNCE_RANK));
				motionDetailData.setAvgShotDist(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_AVG_SHOT_DIST));
				motionDetailData.setMaxShotDist(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_MAX_SHOT_DIST));
				motionDetailData.setHandle(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_HANDLE));
				motionDetailData.setCrlorie(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_CALORIE));
				motionDetailData.setTrail(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_TRAIL));
				motionDetailData.setVerJumpPoint(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_VER_JUMP_POINT));
				motionDetailData.setFooter(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_BLUETOOTH_FOOT));
				motionDetailData.setBallType(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_DETAIL_BALL_TYPE));
				motionDetailData.setStadiumType(cursor.getString(DatabaseObject.MotionDetailTable.INDEX_STADIUM_TYPE));
				LogUtils.showLog("database","findMotionDetail userdata:"+motionDetailData.toString());
			}
			LogUtils.showLog("database","findMotionDetail over:");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.showLog("database","findMotionDetail exception:");
		}finally{
			LogUtils.showLog("database","findMotionDetail finally:");
			if(cursor != null ) cursor.close();
		}
		return motionDetailData;
	}

	public static HashMap<String, MotionUploadData> findAllMotionUploadData(){
		HashMap<String, MotionUploadData> motionUploadDatas = new HashMap<String, MotionUploadData>();
		MotionUploadData motionUploadData=null;
		LogUtils.showLog("database", "findAllMotionData ");

		Cursor cursor = null;
		try {
			cursor=DatabaseUtils.getRecordsFromTable(DatabaseObject.MOTION_BLUETOOTH_DATA,
					null, DatabaseObject.MotionBluetoothDataTable.PROJECT, null,
					null, null);
			while(cursor.moveToNext()){
				motionUploadData = new MotionUploadData();
				motionUploadData.setVipId(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_VIP_ID));
				motionUploadData.setSn(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_SN));
				motionUploadData.setFooter(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_FOOTER));
				motionUploadData.setLongitude(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_LONGITUDE));
				motionUploadData.setLatitude(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_LATITUDE));
				motionUploadData.setAddress(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_ADDRESS));
				motionUploadData.setBeginTime(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_BEGIN_TIME));
				motionUploadData.setSpend(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_SPEND));
				motionUploadData.setPicture(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_PICTURE));
				motionUploadData.setEndTime(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_END_TIME));
				motionUploadData.setTotalDist(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_TOTAL_DIST));
				motionUploadData.setTotalStep(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_TOTAL_STEP));
				motionUploadData.setTotalHorDist(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_TOTAL_HOR_DIST));
				motionUploadData.setTotalVerDist(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_TOTAL_VER_DIST));
				motionUploadData.setTotalTime(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_TOTAL_TIME));
				motionUploadData.setTotalActiveTime(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_TOTAL_ACTIVE_TIME));
				motionUploadData.setActiveRate(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_ACTIVE_RATE));
				motionUploadData.setAvgSpeed(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_AVG_SPEED));
				motionUploadData.setMaxSpeed(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_MAX_SPEED));
				motionUploadData.setSpurtCount(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_SPURT_COUNT));
				motionUploadData.setBreakinCount(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_BREAKIN_COUNT));
				motionUploadData.setBreakinAvgTime(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_BREAKIN_AVG_TIME));
				motionUploadData.setVerJumpPoint(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_VER_JUMP_POINT));
				motionUploadData.setVerJumpCount(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_VER_JUMP_COUNT));
				motionUploadData.setVerJumpAvgHigh(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_VER_JUMP_AVG_HIGH));
				motionUploadData.setVerJumpMaxHigh(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_VER_JUMP_MAX_HIGH));
				motionUploadData.setAvgHoverTime(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_AVG_HOVER_TIME));
				motionUploadData.setAvgTouchAngle(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_AVG_TOUCH_ANGLE));
				motionUploadData.setTouchType(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_TOUCH_TYPE));
				motionUploadData.setPerfRank(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_PERF_RANK));
				motionUploadData.setRunRank(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_RUN_RANK));
				motionUploadData.setBreakRank(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_BREAK_RANK));
				motionUploadData.setBounceRank(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_BOUNCE_RANK));
				motionUploadData.setAvgShotDist(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_AVG_SHOT_DIST));
				motionUploadData.setMaxShotDist(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_MAX_SHOT_DIST));
				motionUploadData.setHandle(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_HANDLE));
				motionUploadData.setCalorie(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_CALORIE));
				motionUploadData.setTrail(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_TRAIL));
				motionUploadData.setBallType(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_BALL_TYPE));
				motionUploadData.setHeader(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_HEADER));
				motionUploadData.setStadiumType(cursor.getString(DatabaseObject.MotionBluetoothDataTable.INDEX_STADIUM_TYPE));
				if(motionUploadData.getStadiumType()==null){
					motionUploadData.setStadiumType("水泥");
				}
				motionUploadDatas.put(motionUploadData.getBeginTime(),motionUploadData);

				LogUtils.showLog("database","findAllMotionUploadData userdata:"+motionUploadData.toString());
			}
			LogUtils.showLog("database","findAllMotionUploadData over:");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.showLog("database","findAllMotionUploadData exception:");
		}finally{
			LogUtils.showLog("database","findAllMotionUploadData finally:");
			if(cursor != null ) cursor.close();
		}
		return motionUploadDatas;
	}
	public static boolean deleteMotionBluetoothData(){
		try {
			DatabaseUtils.deleteRecordFromTable(DatabaseObject.MOTION_BLUETOOTH_DATA, null, null
					, null);
			LogUtils.showLog("deleteMotionBluetoothData", "查询");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.showLog("deleteMotionBluetoothData", "异常");
		}finally{
			LogUtils.showLog("deleteMotionBluetoothData", "finally");
		}
		return false;
	}
    public static boolean deleteBluetoothListByID(String ids,String username){
        LogUtils.showLog("database", "findBluetoothList username:"+username);
        String where_cause = DatabaseObject.BluetoothListTable.FIELD_BLUETOOTH_LIST_SERVICE_ID
                + " =? And "
                +DatabaseObject.BluetoothListTable.FIELD_BLUETOOTH_LIST_USERNAME
                + " =? ";
        String[] where_args = new String[] { ids,username };

        try {
            DatabaseUtils.deleteRecordFromTable(DatabaseObject.BLUETOOTH_LIST, null, where_cause
                    , where_args);
            LogUtils.showLog("deleteMotionBluetoothData", "查询");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.showLog("deleteMotionBluetoothData", "异常");
        }finally{
            LogUtils.showLog("deleteMotionBluetoothData", "finally");
        }
        return false;
    }
	public static boolean deleteReachDetailByUsername(String username){
		LogUtils.showLog("database", "deleteReachDetailByUsername username:"+username);
		String where_cause = DatabaseObject.ReachDetailTable.FIELD_REACH_DETIAL_USERNAME
				+ " =? ";
		String[] where_args = new String[] { username };

		try {
			DatabaseUtils.deleteRecordFromTable(DatabaseObject.REACH_DETAIL, null, where_cause
					, where_args);
			LogUtils.showLog("deleteReachDetailByUsername", "查询");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.showLog("deleteReachDetailByUsername", "异常");
		}finally{
			LogUtils.showLog("deleteReachDetailByUsername", "finally");
		}
		return false;
	}
	public static boolean deleteMotionListByUsernameAndType(String username,String type){
		LogUtils.showLog("database", "deleteMotionListByUsernameAndType username:"+username);
		String where_cause = DatabaseObject.MotionListTable.FIELD_MOTION_LIST_USERNAME
				+ " =? And "
				+DatabaseObject.MotionListTable.FIELD_MOTION_LIST_TYPE
				+ " =? ";
		String[] where_args = new String[] { username,type };

		try {
			DatabaseUtils.deleteRecordFromTable(DatabaseObject.MOTION_LIST, null, where_cause
					, where_args);
			LogUtils.showLog("deleteMotionListByUsernameAndType", "查询");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.showLog("deleteMotionListByUsernameAndType", "异常");
		}finally{
			LogUtils.showLog("deleteMotionListByUsernameAndType", "finally");
		}
		return false;
	}
	public static boolean deleteReachDetailInfo(){
		try {
			DatabaseUtils.deleteRecordFromTable(DatabaseObject.REACH_DETAIL, null, null
					, null);
			LogUtils.showLog("deleteReachDetailInfo", "查询");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.showLog("deleteReachDetailInfo", "异常");
		}finally{
			LogUtils.showLog("deleteReachDetailInfo", "finally");
		}
		return false;
	}
}
