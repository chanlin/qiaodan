package com.jordan.project.database;

import android.content.ContentValues;

public final class DatabaseObject {


    public static final String DATABASE_NAME = "jordan_database.db";
    public static final int DATABASE_VERSION = 1;


    public static final String USER_INFO = "user_info";

    /**
     * 用户信息表
     */
    public static final class UserInfoTable {


        //用户本地ID
        public static final String FIELD_USER_INFO_ID = "user_info_id";
        //用户名
        public static final String FIELD_USER_INFO_USERNAME = "user_info_username";
        //姓名
        public static final String FIELD_USER_INFO_NAME = "user_info_name";
        //昵称
        public static final String FIELD_USER_INFO_NICK = "user_info_nick";
        //性别
        public static final String FIELD_USER_INFO_GENDER = "user_info_gender";
        //年龄
        public static final String FIELD_USER_INFO_AGE = "user_info_age";
        //生日（时间戳）
        public static final String FIELD_USER_INFO_BIRTHDAY = "user_info_birthday";
        //擅长位置
        public static final String FIELD_USER_INFO_POSITION = "user_info_position";
        //体重
        public static final String FIELD_USER_INFO_WEIGHT = "user_info_weight";
        //身高
        public static final String FIELD_USER_INFO_HEIGHT = "user_info_height";
        //QQ号
        public static final String FIELD_USER_INFO_QQ = "user_info_qq";
        //手机号
        public static final String FIELD_USER_INFO_MOBILE = "user_info_mobile";
        //邮箱地址
        public static final String FIELD_USER_INFO_EMAIL = "user_info_email";
        //头像地址
        public static final String FIELD_USER_INFO_IMG = "user_info_img";
        //头像地址
        public static final String FIELD_USER_INFO_IMGID = "user_info_imgid";

        //用户本地ID
        public static final int INDEX_USER_INFO_ID = 0;
        //用户名
        public static final int INDEX_USER_INFO_USERNAME = 1;
        //姓名
        public static final int INDEX_USER_INFO_NAME = 2;
        //昵称
        public static final int INDEX_USER_INFO_NICK = 3;
        //性别
        public static final int INDEX_USER_INFO_GENDER = 4;
        //年龄
        public static final int INDEX_USER_INFO_AGE = 5;
        //生日（时间戳）
        public static final int INDEX_USER_INFO_BIRTHDAY = 6;
        //擅长位置
        public static final int INDEX_USER_INFO_POSITION = 7;
        //体重
        public static final int INDEX_USER_INFO_WEIGHT = 8;
        //身高
        public static final int INDEX_USER_INFO_HEIGHT = 9;
        //QQ号
        public static final int INDEX_USER_INFO_QQ = 10;
        //手机号
        public static final int INDEX_USER_INFO_MOBILE = 11;
        //邮箱地址
        public static final int INDEX_USER_INFO_EMAIL = 12;
        //头像地址
        public static final int INDEX_USER_INFO_IMG = 13;
        //头像地址
        public static final int INDEX_USER_INFO_IMGID = 14;

        public static final String[] PROJECT = new String[]{
                FIELD_USER_INFO_ID, FIELD_USER_INFO_USERNAME, FIELD_USER_INFO_NAME,
                FIELD_USER_INFO_NICK, FIELD_USER_INFO_GENDER, FIELD_USER_INFO_AGE,
                FIELD_USER_INFO_BIRTHDAY, FIELD_USER_INFO_POSITION, FIELD_USER_INFO_WEIGHT,
                FIELD_USER_INFO_HEIGHT, FIELD_USER_INFO_QQ, FIELD_USER_INFO_MOBILE,
                FIELD_USER_INFO_EMAIL, FIELD_USER_INFO_IMG,FIELD_USER_INFO_IMGID};

        public static String CREATE_SQL =
                "CREATE TABLE IF NOT EXISTS  " + USER_INFO + "("
                        + FIELD_USER_INFO_ID + " integer primary key autoincrement, "
                        + FIELD_USER_INFO_USERNAME + " varchar(200),"
                        + FIELD_USER_INFO_NAME + " varchar(200),"
                        + FIELD_USER_INFO_NICK + " varchar(200),"
                        + FIELD_USER_INFO_GENDER + " varchar(200),"
                        + FIELD_USER_INFO_AGE + " varchar(200),"
                        + FIELD_USER_INFO_BIRTHDAY + " varchar(200),"
                        + FIELD_USER_INFO_POSITION + " varchar(200),"
                        + FIELD_USER_INFO_WEIGHT + " varchar(200),"
                        + FIELD_USER_INFO_HEIGHT + " varchar(200),"
                        + FIELD_USER_INFO_QQ + " varchar(200),"
                        + FIELD_USER_INFO_MOBILE + " varchar(200),"
                        + FIELD_USER_INFO_EMAIL + " varchar(200),"
                        + FIELD_USER_INFO_IMG + " varchar(200),"
                        + FIELD_USER_INFO_IMGID + " varchar(200));";

        public static final String DROP_SQL = "DROP TABLE IF EXISTS "
                + USER_INFO + ";";

        public static ContentValues getContentValues(String username,
                                                     String name, String nick, String gender, String age, String birthday,
                                                     String position, String weight, String height, String qq, String mobile,
                                                     String email, String img,String imgId) {
            ContentValues values = new ContentValues();
            values.put(FIELD_USER_INFO_USERNAME, username);
            values.put(FIELD_USER_INFO_NAME, name);
            values.put(FIELD_USER_INFO_NICK, nick);
            values.put(FIELD_USER_INFO_GENDER, gender);
            values.put(FIELD_USER_INFO_AGE, age);
            values.put(FIELD_USER_INFO_BIRTHDAY, birthday);
            values.put(FIELD_USER_INFO_POSITION, position);
            values.put(FIELD_USER_INFO_WEIGHT, weight);
            values.put(FIELD_USER_INFO_HEIGHT, height);
            values.put(FIELD_USER_INFO_QQ, qq);
            values.put(FIELD_USER_INFO_MOBILE, mobile);
            values.put(FIELD_USER_INFO_EMAIL, email);
            values.put(FIELD_USER_INFO_IMG, img);
            values.put(FIELD_USER_INFO_IMGID, imgId);
            return values;

        }
    }

    public static final String MOTION_LIST = "motion_list";

    /**
     * 运动列表
     */
    public static final class MotionListTable {


        //本地ID
        public static final String FIELD_MOTION_LIST_ID = "motion_list_id";
        //服务器ID
        public static final String FIELD_MOTION_LIST_SERVICE_ID = "motion_list_service_id";
        //用户名
        public static final String FIELD_MOTION_LIST_USERNAME = "motion_list_username";
        //YearTime
        public static final String FIELD_MOTION_LIST_YEAR_TIME = "motion_list_year_time";
        //HourTime
        public static final String FIELD_MOTION_LIST_HOUR_TIME = "motion_list_hour_time";
        //列表TYPE
        public static final String FIELD_MOTION_LIST_TYPE = "motion_list_type";
        //时长
        public static final String FIELD_MOTION_LIST_TIME_LONG = "motion_list_time_long";
        //距离
        public static final String FIELD_MOTION_LIST_DISTANCE = "motion_list_distance";

        //本地ID
        public static final int INDEX_MOTION_LIST_ID = 0;
        //服务器ID
        public static final int INDEX_MOTION_LIST_SERVICE_ID = 1;
        //用户名
        public static final int INDEX_MOTION_LIST_USERNAME = 2;
        //YearTime
        public static final int INDEX_MOTION_LIST_YEAR_TIME = 3;
        //HourTime
        public static final int INDEX_MOTION_LIST_HOUR_TIME = 4;
        //列表TYPE
        public static final int INDEX_MOTION_LIST_TYPE = 5;
        //时长
        public static final int INDEX_MOTION_LIST_TIME_LONG = 6;
        //距离
        public static final int INDEX_MOTION_LIST_DISTANCE = 7;

        public static final String[] PROJECT = new String[]{
                FIELD_MOTION_LIST_ID, FIELD_MOTION_LIST_SERVICE_ID, FIELD_MOTION_LIST_USERNAME,
                FIELD_MOTION_LIST_YEAR_TIME, FIELD_MOTION_LIST_HOUR_TIME, FIELD_MOTION_LIST_TYPE,
                FIELD_MOTION_LIST_TIME_LONG, FIELD_MOTION_LIST_DISTANCE};

        public static String CREATE_SQL =
                "CREATE TABLE IF NOT EXISTS  " + MOTION_LIST + "("
                        + FIELD_MOTION_LIST_ID + " integer primary key autoincrement, "
                        + FIELD_MOTION_LIST_SERVICE_ID + " varchar(200),"
                        + FIELD_MOTION_LIST_USERNAME + " varchar(200),"
                        + FIELD_MOTION_LIST_YEAR_TIME + " varchar(200),"
                        + FIELD_MOTION_LIST_HOUR_TIME + " varchar(200),"
                        + FIELD_MOTION_LIST_TYPE + " varchar(200),"
                        + FIELD_MOTION_LIST_TIME_LONG + " varchar(200),"
                        + FIELD_MOTION_LIST_DISTANCE + " varchar(200));";

        public static final String DROP_SQL = "DROP TABLE IF EXISTS "
                + MOTION_LIST + ";";

        public static ContentValues getContentValues(String serviceId,String username, String yearTime, String hourTime,
                                                     String type,String timeLong,String distance) {
            ContentValues values = new ContentValues();
            values.put(FIELD_MOTION_LIST_SERVICE_ID, serviceId);
            values.put(FIELD_MOTION_LIST_USERNAME, username);
            values.put(FIELD_MOTION_LIST_YEAR_TIME, yearTime);
            values.put(FIELD_MOTION_LIST_HOUR_TIME, hourTime);
            values.put(FIELD_MOTION_LIST_TYPE, type);
            values.put(FIELD_MOTION_LIST_TIME_LONG, timeLong);
            values.put(FIELD_MOTION_LIST_DISTANCE, distance);
            return values;
        }
    }

    /**
     * 运动信息
     */
    public static final String MOTION_DETAIL = "motion_detail";

    public static final class MotionDetailTable {
        //运动本地ID
        public static final int INDEX_DETAIL_ID = 0;
        //运动服务器ID
        public static final int INDEX_DETAIL_SERVICE_ID = 1;
        //经度
        public static final int INDEX_DETAIL_LONGITUDE = 2;
        //纬度
        public static final int INDEX_DETAIL_LATITUDE = 3;
        //地址
        public static final int INDEX_DETAIL_ADDRESS = 4;
        //开始时间（时间戳）
        public static final int INDEX_DETAIL_BEGIN_TIME = 5;
        //运动时长
        public static final int INDEX_DETAIL_SPEND = 6;
        //图片
        public static final int INDEX_DETAIL_PICTURE = 7;
        //结束时间（时间戳）
        public static final int INDEX_DETAIL_END_TIME = 8;
        //总距离
        public static final int INDEX_DETAIL_TOTAL_DIST = 9;
        //总步数
        public static final int INDEX_DETAIL_TOTAL_STEP = 10;
        //横向总距离
        public static final int INDEX_DETAIL_TOTAL_HOR_DIST = 11;
        //纵向总距离
        public static final int INDEX_DETAIL_TOTAL_VER_DIST = 12;
        //总时间
        public static final int INDEX_DETAIL_TOTAL_TIME = 13;
        //活跃总时间
        public static final int INDEX_DETAIL_TOTAL_ACTIVE_TIME = 14;
        //活跃时间占比
        public static final int INDEX_DETAIL_ACTIVE_RATE = 15;
        //平均移动速度
        public static final int INDEX_DETAIL_AVG_SPEED = 16;
        //最大移动速度
        public static final int INDEX_DETAIL_MAX_SPEED = 17;
        //冲向次数
        public static final int INDEX_DETAIL_SPURT_COUNT = 18;
        //变向次数
        public static final int INDEX_DETAIL_BREAKIN_COUNT = 19;
        //变向平均触底时间
        public static final int INDEX_DETAIL_BREAKIN_AVG_TIME = 20;
        //纵跳次数
        public static final int INDEX_DETAIL_VER_JUMP_COUNT = 21;
        //纵跳平均高度
        public static final int INDEX_DETAIL_VER_JUMP_AVG_HIGH = 22;
        //平均滞空时间
        public static final int INDEX_DETAIL_AVG_HOVER_TIME = 23;
        //平均着地旋转角
        public static final int INDEX_DETAIL_AVG_TOUCH_ANGLE = 24;
        //着地类型
        public static final int INDEX_DETAIL_TOUCH_TYPE = 25;
        //本场表现
        public static final int INDEX_DETAIL_PERF_RANK = 26;
        //跑步等级
        public static final int INDEX_DETAIL_RUN_RANK = 27;
        //突破等级
        public static final int INDEX_DETAIL_BREAK_RANK = 28;
        //弹跳等级
        public static final int INDEX_DETAIL_BOUNCE_RANK = 29;
        //平均出手距离
        public static final int INDEX_DETAIL_AVG_SHOT_DIST = 30;
        //最大出手距离
        public static final int INDEX_DETAIL_MAX_SHOT_DIST = 31;
        //手感
        public static final int INDEX_DETAIL_HANDLE = 32;
        //消耗卡路里
        public static final int INDEX_DETAIL_CALORIE = 33;
        //运动轨迹
        public static final int INDEX_DETAIL_TRAIL = 34;
        public static final int INDEX_DETAIL_VER_JUMP_POINT = 35;
        public static final int INDEX_DETAIL_BLUETOOTH_FOOT = 36;
        public static final int INDEX_DETAIL_BALL_TYPE=37;
        public static final int INDEX_STADIUM_TYPE =  38;//球场类型


        //运动本地ID
        public static final String FIELD_MOTION_DETAIL_ID = "motion_detail_id";
        //运动服务器ID
        public static final String FIELD_MOTION_DETAIL_SERVICE_ID = "motion_detail_service_id";
        //经度
        public static final String FIELD_MOTION_DETAIL_LONGITUDE = "motion_detail_longitude";
        //纬度
        public static final String FIELD_MOTION_DETAIL_LATITUDE = "motion_detail_latitude";
        //地址
        public static final String FIELD_MOTION_DETAIL_ADDRESS = "motion_detail_address";
        //开始时间（时间戳）
        public static final String FIELD_MOTION_DETAIL_BEGIN_TIME = "motion_detail_begin_time";
        //运动时长
        public static final String FIELD_MOTION_DETAIL_SPEND = "motion_detail_spend";
        //图片
        public static final String FIELD_MOTION_DETAIL_PICTURE = "motion_detail_picture";
        //结束时间（时间戳）
        public static final String FIELD_MOTION_DETAIL_END_TIME = "motion_detail_end_time";
        //总距离
        public static final String FIELD_MOTION_DETAIL_TOTAL_DIST = "motion_detail_total_dist";
        //总步数
        public static final String FIELD_MOTION_DETAIL_TOTAL_STEP = "motion_detail_total_step";
        //横向总距离
        public static final String FIELD_MOTION_DETAIL_TOTAL_HOR_DIST = "motion_detail_total_hor_dist";
        //纵向总距离
        public static final String FIELD_MOTION_DETAIL_TOTAL_VER_DIST = "motion_detail_total_ver_dist";
        //总时间
        public static final String FIELD_MOTION_DETAIL_TOTAL_TIME = "motion_detail_total_time";
        //活跃总时间
        public static final String FIELD_MOTION_DETAIL_TOTAL_ACTIVE_TIME = "motion_detail_total_active_time";
        //活跃时间占比
        public static final String FIELD_MOTION_DETAIL_ACTIVE_RATE = "motion_detail_active_rate";
        //平均移动速度
        public static final String FIELD_MOTION_DETAIL_AVG_SPEED = "motion_detail_avg_speed";
        //最大移动速度
        public static final String FIELD_MOTION_DETAIL_MAX_SPEED = "motion_detail_max_speed";
        //冲向次数
        public static final String FIELD_MOTION_DETAIL_SPURT_COUNT = "motion_detail_spurt_count";
        //变向次数
        public static final String FIELD_MOTION_DETAIL_BREAKIN_COUNT = "motion_detail_breakin_count";
        //变向平均触底时间
        public static final String FIELD_MOTION_DETAIL_BREAKIN_AVG_TIME = "motion_detail_breakin_avg_time";
        //纵跳次数
        public static final String FIELD_MOTION_DETAIL_VER_JUMP_COUNT = "motion_detail_ver_jump_count";
        //纵跳平均高度
        public static final String FIELD_MOTION_DETAIL_VER_JUMP_AVG_HIGH = "motion_detail_ver_jump_avg_high";
        //平均滞空时间
        public static final String FIELD_MOTION_DETAIL_AVG_HOVER_TIME = "motion_detail_avg_hover_time";
        //平均着地旋转角
        public static final String FIELD_MOTION_DETAIL_AVG_TOUCH_ANGLE = "motion_detail_avg_touch_angle";
        //着地类型
        public static final String FIELD_MOTION_DETAIL_TOUCH_TYPE = "motion_detail_touch_type";
        //本场表现
        public static final String FIELD_MOTION_DETAIL_PERF_RANK = "motion_detail_perf_rank";
        //跑步等级
        public static final String FIELD_MOTION_DETAIL_RUN_RANK = "motion_detail_run_rank";
        //突破等级
        public static final String FIELD_MOTION_DETAIL_BREAK_RANK = "motion_detail_break_rank";
        //弹跳等级
        public static final String FIELD_MOTION_DETAIL_BOUNCE_RANK = "motion_detail_bounce_rank";
        //平均出手距离
        public static final String FIELD_MOTION_DETAIL_AVG_SHOT_DIST = "motion_detail_avg_shot_dist";
        //最大出手距离
        public static final String FIELD_MOTION_DETAIL_MAX_SHOT_DIST = "motion_detail_max_shot_dist";
        //手感
        public static final String FIELD_MOTION_DETAIL_HANDLE = "motion_detail_handle";
        //消耗卡路里
        public static final String FIELD_MOTION_DETAIL_CALORIE = "motion_detail_calorie";
        //运动轨迹
        public static final String FIELD_MOTION_DETAIL_TRAIL = "motion_detail_trale";
        public static final String FIELD_MOTION_DETAIL_VER_JUMP_POINT = "verJumpPoint";//		纵跳点(纵跳的高度的集合，以”,”分隔)
        //芯片所在脚
        public static final String FIELD_MOTION_DETAIL_BLUETOOTH_FOOT = "bluetoothFoot";
        public static final String FIELD_MOTION_DETAIL_BALL_TYPE="ball_type";
        public static final String FIELD_STADIUM_TYPE = "stadium_type";//球场类型
        public static final String[] PROJECT = new String[]{
                FIELD_MOTION_DETAIL_ID, FIELD_MOTION_DETAIL_SERVICE_ID, FIELD_MOTION_DETAIL_LONGITUDE,
                FIELD_MOTION_DETAIL_LATITUDE, FIELD_MOTION_DETAIL_ADDRESS, FIELD_MOTION_DETAIL_BEGIN_TIME,
                FIELD_MOTION_DETAIL_SPEND, FIELD_MOTION_DETAIL_PICTURE, FIELD_MOTION_DETAIL_END_TIME,
                FIELD_MOTION_DETAIL_TOTAL_DIST, FIELD_MOTION_DETAIL_TOTAL_STEP, FIELD_MOTION_DETAIL_TOTAL_HOR_DIST,
                FIELD_MOTION_DETAIL_TOTAL_VER_DIST, FIELD_MOTION_DETAIL_TOTAL_TIME, FIELD_MOTION_DETAIL_TOTAL_ACTIVE_TIME,
                FIELD_MOTION_DETAIL_ACTIVE_RATE, FIELD_MOTION_DETAIL_AVG_SPEED, FIELD_MOTION_DETAIL_MAX_SPEED,
                FIELD_MOTION_DETAIL_SPURT_COUNT, FIELD_MOTION_DETAIL_BREAKIN_COUNT, FIELD_MOTION_DETAIL_BREAKIN_AVG_TIME,
                FIELD_MOTION_DETAIL_VER_JUMP_COUNT, FIELD_MOTION_DETAIL_VER_JUMP_AVG_HIGH, FIELD_MOTION_DETAIL_AVG_HOVER_TIME,
                FIELD_MOTION_DETAIL_AVG_TOUCH_ANGLE, FIELD_MOTION_DETAIL_TOUCH_TYPE, FIELD_MOTION_DETAIL_PERF_RANK,
                FIELD_MOTION_DETAIL_RUN_RANK, FIELD_MOTION_DETAIL_BREAK_RANK, FIELD_MOTION_DETAIL_BOUNCE_RANK,
                FIELD_MOTION_DETAIL_AVG_SHOT_DIST, FIELD_MOTION_DETAIL_MAX_SHOT_DIST, FIELD_MOTION_DETAIL_HANDLE,
                FIELD_MOTION_DETAIL_CALORIE, FIELD_MOTION_DETAIL_TRAIL,FIELD_MOTION_DETAIL_VER_JUMP_POINT,
                FIELD_MOTION_DETAIL_BLUETOOTH_FOOT,FIELD_MOTION_DETAIL_BALL_TYPE,FIELD_STADIUM_TYPE};
        public static String CREATE_SQL =
                "CREATE TABLE IF NOT EXISTS  " + MOTION_DETAIL + "("
                        + FIELD_MOTION_DETAIL_ID + " integer primary key autoincrement, "
                        + FIELD_MOTION_DETAIL_SERVICE_ID + " varchar(200),"
                        + FIELD_MOTION_DETAIL_LONGITUDE + " varchar(200),"
                        + FIELD_MOTION_DETAIL_LATITUDE + " varchar(200),"
                        + FIELD_MOTION_DETAIL_ADDRESS + " varchar(200),"
                        + FIELD_MOTION_DETAIL_BEGIN_TIME + " varchar(200),"
                        + FIELD_MOTION_DETAIL_SPEND + " varchar(200),"
                        + FIELD_MOTION_DETAIL_PICTURE + " varchar(200),"
                        + FIELD_MOTION_DETAIL_END_TIME + " varchar(200),"
                        + FIELD_MOTION_DETAIL_TOTAL_DIST + " varchar(200),"
                        + FIELD_MOTION_DETAIL_TOTAL_STEP + " varchar(200),"
                        + FIELD_MOTION_DETAIL_TOTAL_HOR_DIST + " varchar(200),"
                        + FIELD_MOTION_DETAIL_TOTAL_VER_DIST + " varchar(200),"
                        + FIELD_MOTION_DETAIL_TOTAL_TIME + " varchar(200),"
                        + FIELD_MOTION_DETAIL_TOTAL_ACTIVE_TIME + " varchar(200),"
                        + FIELD_MOTION_DETAIL_ACTIVE_RATE + " varchar(200),"
                        + FIELD_MOTION_DETAIL_AVG_SPEED + " varchar(200),"
                        + FIELD_MOTION_DETAIL_MAX_SPEED + " varchar(200),"
                        + FIELD_MOTION_DETAIL_SPURT_COUNT + " varchar(200),"
                        + FIELD_MOTION_DETAIL_BREAKIN_COUNT + " varchar(200),"
                        + FIELD_MOTION_DETAIL_BREAKIN_AVG_TIME + " varchar(200),"
                        + FIELD_MOTION_DETAIL_VER_JUMP_COUNT + " varchar(200),"
                        + FIELD_MOTION_DETAIL_VER_JUMP_AVG_HIGH + " varchar(200),"
                        + FIELD_MOTION_DETAIL_AVG_HOVER_TIME + " varchar(200),"
                        + FIELD_MOTION_DETAIL_AVG_TOUCH_ANGLE + " varchar(200),"
                        + FIELD_MOTION_DETAIL_TOUCH_TYPE + " varchar(200),"
                        + FIELD_MOTION_DETAIL_PERF_RANK + " varchar(200),"
                        + FIELD_MOTION_DETAIL_RUN_RANK + " varchar(200),"
                        + FIELD_MOTION_DETAIL_BREAK_RANK + " varchar(200),"
                        + FIELD_MOTION_DETAIL_BOUNCE_RANK + " varchar(200),"
                        + FIELD_MOTION_DETAIL_AVG_SHOT_DIST + " varchar(200),"
                        + FIELD_MOTION_DETAIL_MAX_SHOT_DIST + " varchar(200),"
                        + FIELD_MOTION_DETAIL_HANDLE + " varchar(200),"
                        + FIELD_MOTION_DETAIL_CALORIE + " varchar(200),"
                        + FIELD_MOTION_DETAIL_TRAIL + " varchar(200),"
                        + FIELD_MOTION_DETAIL_VER_JUMP_POINT + " varchar(200),"
                        + FIELD_MOTION_DETAIL_BLUETOOTH_FOOT + " varchar(200),"
                        + FIELD_MOTION_DETAIL_BALL_TYPE + " varchar(200),"
                        + FIELD_STADIUM_TYPE + " varchar(200));";

        public static final String DROP_SQL = "DROP TABLE IF EXISTS "
                + MOTION_DETAIL + ";";

        public static ContentValues getContentValues(
                String serviceID, String longitude, String latitude, String address,
                String beginTime, String spend, String picture, String endTime, String totalDist, String totalStep,
                String totalHorDist, String totalVerDist, String totalTime, String totalActiveTime,
                String activeRate, String avgSpeed, String maxSpeed, String spurtCount, String breakinCount,
                String breakinAvgTime, String verJumpCount, String verJumpAvgHigh, String avgHoverTime,
                String avgTouchAngle, String touchType, String perfRank, String runRank, String breakRank,
                String bounceRank, String avgShotDist, String maxShotDist, String handle, String crlorie,
                String trail,String verJumpPoint,String bluetoothFoot,String ballType,String stadiumType) {
            ContentValues values = new ContentValues();
            values.put(FIELD_MOTION_DETAIL_SERVICE_ID, serviceID);
            values.put(FIELD_MOTION_DETAIL_LONGITUDE, longitude);
            values.put(FIELD_MOTION_DETAIL_LATITUDE, latitude);
            values.put(FIELD_MOTION_DETAIL_ADDRESS, address);
            values.put(FIELD_MOTION_DETAIL_BEGIN_TIME, beginTime);
            values.put(FIELD_MOTION_DETAIL_SPEND, spend);
            values.put(FIELD_MOTION_DETAIL_PICTURE, picture);
            values.put(FIELD_MOTION_DETAIL_END_TIME, endTime);
            values.put(FIELD_MOTION_DETAIL_TOTAL_DIST, totalDist);
            values.put(FIELD_MOTION_DETAIL_TOTAL_STEP, totalStep);
            values.put(FIELD_MOTION_DETAIL_TOTAL_HOR_DIST, totalHorDist);
            values.put(FIELD_MOTION_DETAIL_TOTAL_VER_DIST, totalVerDist);
            values.put(FIELD_MOTION_DETAIL_TOTAL_TIME, totalTime);
            values.put(FIELD_MOTION_DETAIL_TOTAL_ACTIVE_TIME, totalActiveTime);
            values.put(FIELD_MOTION_DETAIL_ACTIVE_RATE, activeRate);
            values.put(FIELD_MOTION_DETAIL_AVG_SPEED, avgSpeed);
            values.put(FIELD_MOTION_DETAIL_MAX_SPEED, maxSpeed);
            values.put(FIELD_MOTION_DETAIL_SPURT_COUNT, spurtCount);
            values.put(FIELD_MOTION_DETAIL_BREAKIN_COUNT, breakinCount);
            values.put(FIELD_MOTION_DETAIL_BREAKIN_AVG_TIME, breakinAvgTime);
            values.put(FIELD_MOTION_DETAIL_VER_JUMP_COUNT, verJumpCount);
            values.put(FIELD_MOTION_DETAIL_VER_JUMP_AVG_HIGH, verJumpAvgHigh);
            values.put(FIELD_MOTION_DETAIL_AVG_HOVER_TIME, avgHoverTime);
            values.put(FIELD_MOTION_DETAIL_AVG_TOUCH_ANGLE, avgTouchAngle);
            values.put(FIELD_MOTION_DETAIL_TOUCH_TYPE, touchType);
            values.put(FIELD_MOTION_DETAIL_PERF_RANK, perfRank);
            values.put(FIELD_MOTION_DETAIL_RUN_RANK, runRank);
            values.put(FIELD_MOTION_DETAIL_BREAK_RANK, breakRank);
            values.put(FIELD_MOTION_DETAIL_BOUNCE_RANK, bounceRank);
            values.put(FIELD_MOTION_DETAIL_AVG_SHOT_DIST, avgShotDist);
            values.put(FIELD_MOTION_DETAIL_MAX_SHOT_DIST, maxShotDist);
            values.put(FIELD_MOTION_DETAIL_HANDLE, handle);
            values.put(FIELD_MOTION_DETAIL_CALORIE, crlorie);
            values.put(FIELD_MOTION_DETAIL_TRAIL, trail);
            values.put(FIELD_MOTION_DETAIL_VER_JUMP_POINT, verJumpPoint);
            values.put(FIELD_MOTION_DETAIL_BLUETOOTH_FOOT, bluetoothFoot);
            values.put(FIELD_MOTION_DETAIL_BALL_TYPE, ballType);
            values.put(FIELD_STADIUM_TYPE, stadiumType);
            return values;

        }
    }


    /**
     * 约球列表
     */
    public static final String REACH_DETAIL = "reach_detail";

    public static final class ReachDetailTable {
        //约球本地ID
        public static final int INDEX_REACH_DETIAL_ID = 0;
        //约球ID
        public static final int INDEX_REACH_DETIAL_SERVICE_ID = 1;
        //球场ID
        public static final int INDEX_REACH_DETIAL_COURT_ID = 2;
        //会员ID
        public static final int INDEX_REACH_DETIAL_VIP_ID = 3;
        //球赛时间（时间戳）
        public static final int INDEX_REACH_DETIAL_TIME = 4;
        //时长
        public static final int INDEX_REACH_DETIAL_DURATION = 5;
        //人数
        public static final int INDEX_REACH_DETIAL_PEOPLE = 6;
        //类型
        public static final int INDEX_REACH_DETIAL_TYPE = 7;
        //图片
        public static final int INDEX_REACH_DETIAL_PICTURE = 8;
        //经度
        public static final int INDEX_REACH_DETIAL_LONGITUDE = 9;
        //纬度
        public static final int INDEX_REACH_DETIAL_LATITUDE = 10;
        //省名
        public static final int INDEX_REACH_DETIAL_PROVINCE = 11;
        //城市名
        public static final int INDEX_REACH_DETIAL_CITY = 12;
        //区县名
        public static final int INDEX_REACH_DETIAL_DISTRICT = 13;
        //街道
        public static final int INDEX_REACH_DETIAL_STREET = 14;
        //详情地址
        public static final int INDEX_REACH_DETIAL_ADDRESS = 15;
        //联系方式
        public static final int INDEX_REACH_DETIAL_MOBILE = 16;
        //联系人
        public static final int INDEX_REACH_DETIAL_CONTACT = 17;
        //参与人数
        public static final int INDEX_REACH_DETIAL_JOIN = 18;
        //距离（单位千米 km)
        public static final int INDEX_REACH_DETIAL_DISTANCE = 19;
        //备注
        public static final int INDEX_REACH_DETIAL_REMARKS = 20;
        //用户
        public static final int INDEX_REACH_DETIAL_USERNAME = 21;
        //会员IMG
        public static final int INDEX_REACH_DETIAL_VIP_IMG = 22;
        //口号
        public static final int INDEX_REACH_DETIAL_SLOGAN = 23;


        //约球本地ID
        public static final String FIELD_REACH_DETIAL_ID = "reach_detail_id";
        //约球ID
        public static final String FIELD_REACH_DETIAL_SERVICE_ID = "reach_detail_service_id";
        //球场ID
        public static final String FIELD_REACH_DETIAL_COURT_ID = "reach_detail_court_id";
        //会员ID
        public static final String FIELD_REACH_DETIAL_VIP_ID = "reach_detail_vip_id";
        //球赛时间（时间戳）
        public static final String FIELD_REACH_DETIAL_TIME = "reach_detail_time";
        //时长
        public static final String FIELD_REACH_DETIAL_DURATION = "reach_detail_duration";
        //人数
        public static final String FIELD_REACH_DETIAL_PEOPLE = "reach_detail_people";
        //类型
        public static final String FIELD_REACH_DETIAL_TYPE = "reach_detail_type";
        //图片
        public static final String FIELD_REACH_DETIAL_PICTURE = "reach_detail_picture";
        //经度
        public static final String FIELD_REACH_DETIAL_LONGITUDE = "reach_detail_longitude";
        //纬度
        public static final String FIELD_REACH_DETIAL_LATITUDE = "reach_detail_latitude";
        //省名
        public static final String FIELD_REACH_DETIAL_PROVINCE = "reach_detail_province";
        //城市名
        public static final String FIELD_REACH_DETIAL_CITY = "reach_detail_city";
        //区县名
        public static final String FIELD_REACH_DETIAL_DISTRICT = "reach_detail_disrict";
        //街道
        public static final String FIELD_REACH_DETIAL_STREET = "reach_detail_street";
        //详情地址
        public static final String FIELD_REACH_DETIAL_ADDRESS = "reach_detail_address";
        //联系方式
        public static final String FIELD_REACH_DETIAL_MOBILE = "reach_detail_mobile";
        //联系人
        public static final String FIELD_REACH_DETIAL_CONTACT = "reach_detail_contact";
        //参与人数
        public static final String FIELD_REACH_DETIAL_JOIN = "reach_detail_join";
        //距离（单位千米 km)
        public static final String FIELD_REACH_DETIAL_DISTANCE = "reach_detail_distance";
        //备注
        public static final String FIELD_REACH_DETIAL_REMARKS = "reach_detail_remarks";
        //用户
        public static final String FIELD_REACH_DETIAL_USERNAME = "reach_detail_username";
        //会员IMG
        public static final String FIELD_REACH_DETIAL_VIP_IMG = "reach_detail_vip_img";
        //口号
        public static final String FIELD_REACH_DETIAL_SLOGAN = "reach_detail_slogan";

        public static final String[] PROJECT = new String[]{
                FIELD_REACH_DETIAL_ID, FIELD_REACH_DETIAL_SERVICE_ID, FIELD_REACH_DETIAL_COURT_ID,
                FIELD_REACH_DETIAL_VIP_ID, FIELD_REACH_DETIAL_TIME, FIELD_REACH_DETIAL_DURATION,
                FIELD_REACH_DETIAL_PEOPLE, FIELD_REACH_DETIAL_TYPE, FIELD_REACH_DETIAL_PICTURE,
                FIELD_REACH_DETIAL_LONGITUDE, FIELD_REACH_DETIAL_LATITUDE, FIELD_REACH_DETIAL_PROVINCE,
                FIELD_REACH_DETIAL_CITY, FIELD_REACH_DETIAL_DISTRICT, FIELD_REACH_DETIAL_STREET, FIELD_REACH_DETIAL_ADDRESS,
                FIELD_REACH_DETIAL_MOBILE, FIELD_REACH_DETIAL_CONTACT, FIELD_REACH_DETIAL_JOIN, FIELD_REACH_DETIAL_DISTANCE,
                FIELD_REACH_DETIAL_REMARKS, FIELD_REACH_DETIAL_USERNAME, FIELD_REACH_DETIAL_VIP_IMG,FIELD_REACH_DETIAL_SLOGAN};

        public static String CREATE_SQL =
                "CREATE TABLE IF NOT EXISTS  " + REACH_DETAIL + "("
                        + FIELD_REACH_DETIAL_ID + " integer primary key autoincrement, "
                        + FIELD_REACH_DETIAL_SERVICE_ID + " varchar(200),"
                        + FIELD_REACH_DETIAL_COURT_ID + " varchar(200),"
                        + FIELD_REACH_DETIAL_VIP_ID + " varchar(200),"
                        + FIELD_REACH_DETIAL_TIME + " varchar(200),"
                        + FIELD_REACH_DETIAL_DURATION + " varchar(200),"
                        + FIELD_REACH_DETIAL_PEOPLE + " varchar(200),"
                        + FIELD_REACH_DETIAL_TYPE + " varchar(200),"
                        + FIELD_REACH_DETIAL_PICTURE + " varchar(200),"
                        + FIELD_REACH_DETIAL_LONGITUDE + " varchar(200),"
                        + FIELD_REACH_DETIAL_LATITUDE + " varchar(200),"
                        + FIELD_REACH_DETIAL_PROVINCE + " varchar(200),"
                        + FIELD_REACH_DETIAL_CITY + " varchar(200),"
                        + FIELD_REACH_DETIAL_DISTRICT + " varchar(200),"
                        + FIELD_REACH_DETIAL_STREET + " varchar(200),"
                        + FIELD_REACH_DETIAL_ADDRESS + " varchar(200),"
                        + FIELD_REACH_DETIAL_MOBILE + " varchar(200),"
                        + FIELD_REACH_DETIAL_CONTACT + " varchar(200),"
                        + FIELD_REACH_DETIAL_JOIN + " varchar(200),"
                        + FIELD_REACH_DETIAL_DISTANCE + " varchar(200),"
                        + FIELD_REACH_DETIAL_REMARKS + " varchar(200),"
                        + FIELD_REACH_DETIAL_USERNAME + " varchar(200),"
                        + FIELD_REACH_DETIAL_VIP_IMG + " varchar(200),"
                        + FIELD_REACH_DETIAL_SLOGAN + " varchar(200));";

        public static final String DROP_SQL = "DROP TABLE IF EXISTS "
                + REACH_DETAIL + ";";

        public static ContentValues getContentValues(String serviceID, String courtID, String vipID, String time, String duration, String people,
                                                     String type, String picture, String longitude, String latitude, String province, String city, String district,
                                                     String street, String address, String mobile, String contact, String join, String distance, String remarks, String username,
                                                     String vipImg,String slogan) {
            ContentValues values = new ContentValues();
            values.put(FIELD_REACH_DETIAL_SERVICE_ID, serviceID);
            values.put(FIELD_REACH_DETIAL_COURT_ID, courtID);
            values.put(FIELD_REACH_DETIAL_VIP_ID, vipID);
            values.put(FIELD_REACH_DETIAL_TIME, time);
            values.put(FIELD_REACH_DETIAL_DURATION, duration);
            values.put(FIELD_REACH_DETIAL_PEOPLE, people);
            values.put(FIELD_REACH_DETIAL_TYPE, type);
            values.put(FIELD_REACH_DETIAL_PICTURE, picture);
            values.put(FIELD_REACH_DETIAL_LONGITUDE, longitude);
            values.put(FIELD_REACH_DETIAL_LATITUDE, latitude);
            values.put(FIELD_REACH_DETIAL_PROVINCE, province);
            values.put(FIELD_REACH_DETIAL_CITY, city);
            values.put(FIELD_REACH_DETIAL_DISTRICT, district);
            values.put(FIELD_REACH_DETIAL_STREET, street);
            values.put(FIELD_REACH_DETIAL_ADDRESS, address);
            values.put(FIELD_REACH_DETIAL_MOBILE, mobile);
            values.put(FIELD_REACH_DETIAL_CONTACT, contact);
            values.put(FIELD_REACH_DETIAL_JOIN, join);
            values.put(FIELD_REACH_DETIAL_DISTANCE, distance);
            values.put(FIELD_REACH_DETIAL_REMARKS, remarks);
            values.put(FIELD_REACH_DETIAL_USERNAME, username);
            values.put(FIELD_REACH_DETIAL_VIP_IMG, vipImg);
            values.put(FIELD_REACH_DETIAL_SLOGAN, slogan);
            return values;


        }
    }


    /**
     * 蓝牙列表
     */
    public static final String BLUETOOTH_LIST = "bluetooth_list";

    public static final class BluetoothListTable {
        //蓝牙本地ID
        public static final int INDEX_BLUETOOTH_LIST_ID = 0;
        //蓝牙服务器ID
        public static final int IINDEX_BLUETOOTH_LIST_SERVICE_ID = 1;
        //蓝牙SN号码
        public static final int INDEX_BLUETOOTH_LIST_SN = 2;
        //蓝牙MAC地址
        public static final int INDEX_BLUETOOTH_LIST_MAC = 3;
        //蓝牙CreateTime
        public static final int INDEX_BLUETOOTH_LIST_CREATE_TIME = 4;
        //用户username
        public static final int INDEX_BLUETOOTH_LIST_USERNAME = 5;

        //蓝牙本地ID
        public static final String FIELD_BLUETOOTH_LIST_ID = "bluetooth_list_id";
        //蓝牙服务器ID
        public static final String FIELD_BLUETOOTH_LIST_SERVICE_ID = "bluetooth_list_service_id";
        //蓝牙SN号码
        public static final String FIELD_BLUETOOTH_LIST_SN = "bluetooth_list_sn";
        //蓝牙MAC地址
        public static final String FIELD_BLUETOOTH_LIST_MAC = "bluetooth_list_mac";
        //蓝牙CreateTime
        public static final String FIELD_BLUETOOTH_LIST_CREATE_TIME = "bluetooth_list_create_time";
        //用户username
        public static final String FIELD_BLUETOOTH_LIST_USERNAME = "bluetooth_list_username";

        public static final String[] PROJECT = new String[]{
                FIELD_BLUETOOTH_LIST_ID, FIELD_BLUETOOTH_LIST_SERVICE_ID, FIELD_BLUETOOTH_LIST_SN,
                FIELD_BLUETOOTH_LIST_MAC, FIELD_BLUETOOTH_LIST_CREATE_TIME, FIELD_BLUETOOTH_LIST_USERNAME};

        public static String CREATE_SQL =
                "CREATE TABLE IF NOT EXISTS  " + BLUETOOTH_LIST + "("
                        + FIELD_BLUETOOTH_LIST_ID + " integer primary key autoincrement, "
                        + FIELD_BLUETOOTH_LIST_SERVICE_ID + " varchar(200),"
                        + FIELD_BLUETOOTH_LIST_SN + " varchar(200),"
                        + FIELD_BLUETOOTH_LIST_MAC + " varchar(200),"
                        + FIELD_BLUETOOTH_LIST_CREATE_TIME + " varchar(200),"
                        + FIELD_BLUETOOTH_LIST_USERNAME + " varchar(200));";

        public static final String DROP_SQL = "DROP TABLE IF EXISTS "
                + BLUETOOTH_LIST + ";";

        public static ContentValues getContentValues(String serviceID, String sn, String mac, String createTime, String username) {
            ContentValues values = new ContentValues();
            values.put(FIELD_BLUETOOTH_LIST_SERVICE_ID, serviceID);
            values.put(FIELD_BLUETOOTH_LIST_SN, sn);
            values.put(FIELD_BLUETOOTH_LIST_MAC, mac);
            values.put(FIELD_BLUETOOTH_LIST_CREATE_TIME, createTime);
            values.put(FIELD_BLUETOOTH_LIST_USERNAME, username);
            return values;


        }
    }


    /**
     * 运动缓存数据
     */
    public static final String MOTION_BLUETOOTH_DATA = "motion_bluetooth_data";

    public static final class MotionBluetoothDataTable {
        public static final String FIELD_ID = "id";//本地ID
        public static final String FIELD_SN = "sn";//蓝牙SN号
        public static final String FIELD_FOOTER = "footer";//		左右脚 R右脚 L左脚
        public static final String FIELD_LONGITUDE = "longitude";//		经度
        public static final String FIELD_LATITUDE = "latitude";//		纬度
        public static final String FIELD_ADDRESS = "address";//		地址
        public static final String FIELD_BEGIN_TIME = "beginTime";//		开始时间(时间戳)
        public static final String FIELD_SPEND = "spend";//		运动时长
        public static final String FIELD_PICTURE = "picture";//		图片
        public static final String FIELD_END_TIME = "endTime";//		结束时间(时间戳)
        public static final String FIELD_TOTAL_DIST = "totalDist";//		总距离
        public static final String FIELD_TOTAL_STEP = "totalStep";//		总步数
        public static final String FIELD_TOTAL_HOR_DIST = "totalHorDist";//		横向总距离
        public static final String FIELD_TOTAL_VER_DIST = "totalVerDist";//		纵向总距离
        public static final String FIELD_TOTAL_TIME = "totalTime";//		总时间
        public static final String FIELD_TOTAL_ACTIVE_TIME = "totalActiveTime";//		活跃总时间
        public static final String FIELD_ACTIVE_RATE = "activeRate";//		活跃时间占比
        public static final String FIELD_AVG_SPEED = "avgSpeed";//		平均移动速度
        public static final String FIELD_MAX_SPEED = "maxSpeed";//		最大移动速度
        public static final String FIELD_SPURT_COUNT = "spurtCount";//		冲向次数
        public static final String FIELD_BREAKIN_COUNT = "breakinCount";//		变向次数
        public static final String FIELD_BREAKIN_AVG_TIME = "breakinAvgTime";//		变向平均触底时间
        public static final String FIELD_VER_JUMP_POINT = "verJumpPoint";//		纵跳点(纵跳的高度的集合，以”,”分隔)
        public static final String FIELD_VER_JUMP_COUNT = "verJumpCount";//		纵跳次数
        public static final String FIELD_VER_JUMP_AVG_HIGH = "verJumpAvgHigh";//		纵跳平均高度
        public static final String FIELD_VER_JUMP_MAX_HIGH = "verJumpMaxHigh";//纵跳最大高度
        public static final String FIELD_AVG_HOVER_TIME = "avgHoverTime";//平均滞空时间
        public static final String FIELD_AVG_TOUCH_ANGLE = "avgTouchAngle";//平均着地旋转角
        public static final String FIELD_TOUCH_TYPE = "touchType";//着地类型
        public static final String FIELD_PERF_RANK = "perfRank";//本场表现
        public static final String FIELD_RUN_RANK = "runRank";//跑动等级
        public static final String FIELD_BREAK_RANK = "breakRank";//突破等级
        public static final String FIELD_BOUNCE_RANK = "bounceRank";//弹跳等级
        public static final String FIELD_AVG_SHOT_DIST = "avgShotDist";//平均出手距离
        public static final String FIELD_MAX_SHOT_DIST = "maxShotDist";//最大出手距离
        public static final String FIELD_HANDLE = "handle";//手感
        public static final String FIELD_CALORIE = "calorie";//消耗卡路里
        public static final String FIELD_TRAIL = "trail";//运动轨迹
        public static final String FIELD_VIP_ID = "vipId";//会员ID
        public static final String FIELD_BALL_TYPE = "ball_type";//场地类型
        public static final String FIELD_HEADER = "header";//
        public static final String FIELD_STADIUM_TYPE = "stadium_type";//球场类型

        public static final int INDEX_ID = 0;//本地ID
        public static final int INDEX_SN = 1;//蓝牙SN号
        public static final int INDEX_FOOTER = 2;//		左右脚 R右脚 L左脚
        public static final int INDEX_LONGITUDE = 3;//		经度
        public static final int INDEX_LATITUDE = 4;//		纬度
        public static final int INDEX_ADDRESS = 5;//		地址
        public static final int INDEX_BEGIN_TIME = 6;//		开始时间(时间戳)
        public static final int INDEX_SPEND = 7;//		运动时长
        public static final int INDEX_PICTURE = 8;//		图片
        public static final int INDEX_END_TIME = 9;//		结束时间(时间戳)
        public static final int INDEX_TOTAL_DIST = 10;//		总距离
        public static final int INDEX_TOTAL_STEP = 11;//		总步数
        public static final int INDEX_TOTAL_HOR_DIST = 12;//		横向总距离
        public static final int INDEX_TOTAL_VER_DIST = 13;//		纵向总距离
        public static final int INDEX_TOTAL_TIME = 14;//		总时间
        public static final int INDEX_TOTAL_ACTIVE_TIME = 15;//		活跃总时间
        public static final int INDEX_ACTIVE_RATE = 16;//		活跃时间占比
        public static final int INDEX_AVG_SPEED = 17;//		平均移动速度
        public static final int INDEX_MAX_SPEED = 18;//		最大移动速度
        public static final int INDEX_SPURT_COUNT = 19;//		冲向次数
        public static final int INDEX_BREAKIN_COUNT = 20;//		变向次数
        public static final int INDEX_BREAKIN_AVG_TIME = 21;//		变向平均触底时间
        public static final int INDEX_VER_JUMP_POINT = 22;//		纵跳点(纵跳的高度的集合，以”,”分隔)
        public static final int INDEX_VER_JUMP_COUNT = 23;//		纵跳次数
        public static final int INDEX_VER_JUMP_AVG_HIGH = 24;//		纵跳平均高度
        public static final int INDEX_VER_JUMP_MAX_HIGH = 25;//纵跳最大高度
        public static final int INDEX_AVG_HOVER_TIME = 26;//平均滞空时间
        public static final int INDEX_AVG_TOUCH_ANGLE = 27;//平均着地旋转角
        public static final int INDEX_TOUCH_TYPE = 28;//着地类型
        public static final int INDEX_PERF_RANK = 29;//本场表现
        public static final int INDEX_RUN_RANK = 30;//跑动等级
        public static final int INDEX_BREAK_RANK = 31;//突破等级
        public static final int INDEX_BOUNCE_RANK = 32;//弹跳等级
        public static final int INDEX_AVG_SHOT_DIST = 33;//平均出手距离
        public static final int INDEX_MAX_SHOT_DIST = 34;//最大出手距离
        public static final int INDEX_HANDLE = 35;//手感
        public static final int INDEX_CALORIE = 36;//消耗卡路里
        public static final int INDEX_TRAIL = 37;//运动轨迹
        public static final int INDEX_VIP_ID = 38;//会员ID
        public static final int INDEX_BALL_TYPE = 39;//场地类型
        public static final int INDEX_HEADER = 40;//
        public static final int INDEX_STADIUM_TYPE = 41;//球场类型

        public static final String[] PROJECT = new String[]{
                FIELD_ID, FIELD_SN, FIELD_FOOTER,
                FIELD_LONGITUDE, FIELD_LATITUDE, FIELD_ADDRESS,
                FIELD_BEGIN_TIME, FIELD_SPEND, FIELD_PICTURE,
                FIELD_END_TIME, FIELD_TOTAL_DIST, FIELD_TOTAL_STEP,
                FIELD_TOTAL_HOR_DIST, FIELD_TOTAL_VER_DIST, FIELD_TOTAL_TIME,
                FIELD_TOTAL_ACTIVE_TIME, FIELD_ACTIVE_RATE, FIELD_AVG_SPEED,
                FIELD_MAX_SPEED, FIELD_SPURT_COUNT, FIELD_BREAKIN_COUNT,
                FIELD_BREAKIN_AVG_TIME, FIELD_VER_JUMP_POINT, FIELD_VER_JUMP_COUNT,
                FIELD_VER_JUMP_AVG_HIGH, FIELD_VER_JUMP_MAX_HIGH, FIELD_AVG_HOVER_TIME,
                FIELD_AVG_TOUCH_ANGLE, FIELD_TOUCH_TYPE, FIELD_PERF_RANK,
                FIELD_RUN_RANK, FIELD_BREAK_RANK, FIELD_BOUNCE_RANK,
                FIELD_AVG_SHOT_DIST, FIELD_MAX_SHOT_DIST, FIELD_HANDLE,
                FIELD_CALORIE, FIELD_TRAIL, FIELD_VIP_ID,FIELD_BALL_TYPE,FIELD_HEADER,FIELD_STADIUM_TYPE};

        public static String CREATE_SQL =
                "CREATE TABLE IF NOT EXISTS  " + MOTION_BLUETOOTH_DATA + "("
                        + FIELD_ID + " integer primary key autoincrement, "
                        + FIELD_SN + " varchar(200),"
                        + FIELD_FOOTER + " varchar(200),"
                        + FIELD_LONGITUDE + " varchar(200),"
                        + FIELD_LATITUDE + " varchar(200),"
                        + FIELD_ADDRESS + " varchar(200),"
                        + FIELD_BEGIN_TIME + " varchar(200),"
                        + FIELD_SPEND + " varchar(200),"
                        + FIELD_PICTURE + " varchar(200),"
                        + FIELD_END_TIME + " varchar(200),"
                        + FIELD_TOTAL_DIST + " varchar(200),"
                        + FIELD_TOTAL_STEP + " varchar(200),"
                        + FIELD_TOTAL_HOR_DIST + " varchar(200),"
                        + FIELD_TOTAL_VER_DIST + " varchar(200),"
                        + FIELD_TOTAL_TIME + " varchar(200),"
                        + FIELD_TOTAL_ACTIVE_TIME + " varchar(200),"
                        + FIELD_ACTIVE_RATE + " varchar(200),"
                        + FIELD_AVG_SPEED + " varchar(200),"
                        + FIELD_MAX_SPEED + " varchar(200),"
                        + FIELD_SPURT_COUNT + " varchar(200),"
                        + FIELD_BREAKIN_COUNT + " varchar(200),"
                        + FIELD_BREAKIN_AVG_TIME + " varchar(200),"
                        + FIELD_VER_JUMP_POINT + " text, "
                        + FIELD_VER_JUMP_COUNT + " varchar(200),"
                        + FIELD_VER_JUMP_AVG_HIGH + " varchar(200),"
                        + FIELD_VER_JUMP_MAX_HIGH + " varchar(200),"
                        + FIELD_AVG_HOVER_TIME + " varchar(200),"
                        + FIELD_AVG_TOUCH_ANGLE + " varchar(200),"
                        + FIELD_TOUCH_TYPE + " varchar(200),"
                        + FIELD_PERF_RANK + " varchar(200),"
                        + FIELD_RUN_RANK + " varchar(200), "
                        + FIELD_BREAK_RANK + " varchar(200),"
                        + FIELD_BOUNCE_RANK + " varchar(200),"
                        + FIELD_AVG_SHOT_DIST + " varchar(200),"
                        + FIELD_MAX_SHOT_DIST + " varchar(200),"
                        + FIELD_HANDLE + " varchar(200),"
                        + FIELD_CALORIE + " varchar(200),"
                        + FIELD_TRAIL + " text, "
                        + FIELD_VIP_ID + " varchar(200), "
                        + FIELD_BALL_TYPE + " varchar(200), "
                        + FIELD_HEADER + " varchar(400), "
                        + FIELD_STADIUM_TYPE + " varchar(200));";

        public static final String DROP_SQL = "DROP TABLE IF EXISTS "
                + MOTION_BLUETOOTH_DATA + ";";

        public static ContentValues getContentValues(String vipId, String sn, String footer, String longitude, String latitude, String address,
                                                     String beginTime, String spend, String picture, String endTime, String totalDist, String totalStep,
                                                     String totalHorDist, String totalVerDist, String totalTime, String totalActiveTime,
                                                     String activeRate, String avgSpeed, String maxSpeed, String spurtCount, String breakinCount,
                                                     String breakinAvgTime, String verJumpPoint, String verJumpCount, String verJumpAvgHigh,
                                                     String verJumpMaxHigh, String avgHoverTime, String avgTouchAngle, String touchType, String perfRank,
                                                     String runRank, String breakRank, String bounceRank, String avgShotDist, String maxShotDist,
                                                     String handle, String calorie, String trail,String ballType,String header,String stadiumType) {
            ContentValues values = new ContentValues();
            values.put(FIELD_VIP_ID,vipId);
            values.put(FIELD_SN, sn);
            values.put(FIELD_FOOTER, footer);
            values.put(FIELD_LONGITUDE, longitude);
            values.put(FIELD_LATITUDE, latitude);
            values.put(FIELD_ADDRESS, address);
            values.put(FIELD_BEGIN_TIME, beginTime);
            values.put(FIELD_SPEND, spend);
            values.put(FIELD_PICTURE, picture);
            values.put(FIELD_END_TIME, endTime);
            values.put(FIELD_TOTAL_DIST, totalDist);
            values.put(FIELD_TOTAL_STEP, totalStep);
            values.put(FIELD_TOTAL_HOR_DIST, totalHorDist);
            values.put(FIELD_TOTAL_VER_DIST, totalVerDist);
            values.put(FIELD_TOTAL_TIME, totalTime);
            values.put(FIELD_TOTAL_ACTIVE_TIME, totalActiveTime);
            values.put(FIELD_ACTIVE_RATE, activeRate);
            values.put(FIELD_AVG_SPEED, avgSpeed);
            values.put(FIELD_MAX_SPEED, maxSpeed);
            values.put(FIELD_SPURT_COUNT, spurtCount);
            values.put(FIELD_BREAKIN_COUNT, breakinCount);
            values.put(FIELD_BREAKIN_AVG_TIME, breakinAvgTime);
            values.put(FIELD_VER_JUMP_POINT, verJumpPoint);
            values.put(FIELD_VER_JUMP_COUNT, verJumpCount);
            values.put(FIELD_VER_JUMP_AVG_HIGH, verJumpAvgHigh);
            values.put(FIELD_VER_JUMP_MAX_HIGH, verJumpMaxHigh);
            values.put(FIELD_AVG_HOVER_TIME, avgHoverTime);
            values.put(FIELD_AVG_TOUCH_ANGLE, avgTouchAngle);
            values.put(FIELD_TOUCH_TYPE, touchType);
            values.put(FIELD_PERF_RANK, perfRank);
            values.put(FIELD_RUN_RANK, runRank);
            values.put(FIELD_BREAK_RANK, breakRank);
            values.put(FIELD_BOUNCE_RANK, bounceRank);
            values.put(FIELD_AVG_SHOT_DIST, avgShotDist);
            values.put(FIELD_MAX_SHOT_DIST, maxShotDist);
            values.put(FIELD_HANDLE, handle);
            values.put(FIELD_CALORIE, calorie);
            values.put(FIELD_TRAIL, trail);
            values.put(FIELD_VIP_ID, vipId);
            values.put(FIELD_BALL_TYPE, ballType);
            values.put(FIELD_HEADER,header);
            values.put(FIELD_STADIUM_TYPE, stadiumType);
            return values;


        }
    }
}
