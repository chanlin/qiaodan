package com.jordan.usersystemlibrary.config;

/**
 * Created by icean on 20,7/2/2.
 */

public class UserSystemConfig {

    public static final String USER_SYSTEM_DEFAULT_ADDRESS = "http://smartball.qiaodan.com:8090/jordan-web/";
    //public static final String USER_SYSTEM_DEFAULT_ADDRESS = "http://47.90.55.236:8080/jordan-web/";

    public static final String USER_SYSTEM_OFFLINE_FILE = "user_data";

    public static final String OFFLINE_USER_FILE = "user_info";
    public static final String OFFLINE_USER_TOKEN = "user_token";
    public static final String OFFLINE_USER_INFO = "user_json";


    public static final String URI_CHECK_ACCOUNT = "v0/user/vip/unique.htm";
    public static final String URI_REGISTER = "v0/user/vip/register.htm";
    public static final String URI_LOGIN = "v0/user/vip/login.htm";
    public static final String URI_GET_PERSONAL_INFO = "v0/user/vip/info.htm";
    public static final String URI_GET_ACCOUNT_INFO = "v0/user/vip/accountInfo.htm";
    public static final String URI_MODIFY_USER_INFO_SINGLE = "v0/user/vip/setSingleInfo.htm";
    public static final String URI_MODIFY_USER_INFO_ALL = "v0/user/vip/setAllInfo.htm";
    public static final String URI_MODIFY_PASSWORD = "v0/user/vip/modifyPassword.htm";
    public static final String URI_FORGET_PASSWORD = "v0/user/vip/forgetPassword.htm";
    public static final String URI_LOGOUT = "v0/user/vip/logout.htm";
    public static final String URI_REACH_LIST = "v0/sport/reach/list.htm";
    public static final String URI_CREATE_LIST = "v0/sport/reach/createList.htm";
    public static final String URI_JOIN_LIST = "v0/sport/reach/joinList.htm";
    public static final String URI_CREATE_REACH = "v0/sport/reach/create.htm";
    public static final String URI_REACH_DETAIL = "v0/sport/reach/detail.htm";
    public static final String URI_REACH_JOIN = "v0/sport/reach/join.htm";
    public static final String URI_MOVE_LIST = "v0/sport/move/list.htm";
    public static final String URI_MOVE_DETAIL = "v0/sport/move/detail.htm";
    public static final String URI_MOVE_UPLOAD = "v0/sport/move/upload.htm";
    public static final String URI_MOVE_UPLOADS = "v0/sport/move/uploads.htm";
    public static final String URI_MOVE_REDAR = "v0/sport/move/radar.htm";
    public static final String URI_TRAIN_DICT = "v0/sport/train/dict.htm";
    public static final String URI_TRAIN_LIST = "v0/sport/train/list.htm";
    public static final String URI_TRAIN_DETAIL = "v0/sport/train/detail.htm";
    public static final String URI_SHOES_BIND = "v0/emall/shoes/bind.htm";
    public static final String URI_SHOES_BOX = "v0/emall/shoes/box.htm";
    public static final String URI_SHOES_LIST = "v0/emall/shoes/list.htm";
    public static final String URI_SHOES_RECO = "v0/emall/shoes/reco.htm";
    public static final String URI_SHOES_UNBIND = "v0/emall/shoes/unbund.htm";
    public static final String URI_TRAIN_COUNT = "v0/sport/train/count.htm";
    public static final String URI_TRAIN_UPLOAD = "v0/sport/train/upload.htm";

    public static final String ACCOUNT_TYPE_USER_NAME = ",";//用户名类型账号
    public static final String ACCOUNT_TYPE_CELL_PHONE = "2";//手机类型账号
    public static final String ACCOUNT_TYPE_EMAIL = "3";//email类型账号

    public static final String LOGING_TYPE_BY_PASSWORD = ",";//使用密码登陆
    public static final String LOGIN_TYPE_BY_CODE = "2";//使用验证码登陆

    public static final String VERIFICATION_TYPE_CODE = ",";//密保校验
    public static final String VERIFICATION_TYPE_MOBILE = "2";//手机校验
    public static final String VERIFICATION_TYPE_EMAIL = "3";//电子邮箱校验

    public static final String USER_INFO_NAME = "name";
    public static final String USER_INFO_NICK = "nick";
    public static final String USER_INFO_GENDER = "gender";
    public static final String USER_INFO_AGE = "age";
    public static final String USER_INFO_BIRTHDAY = "birth";
    public static final String USER_INFO_POSITION = "position";
    public static final String USER_INFO_WEIGHT = "weight";
    public static final String USER_INFO_HEIGHT = "height";
    public static final String USER_INFO_QQ = "qq";
    public static final String USER_INFO_IMG = "img";

    public static final String GENDER_MALE = ",";
    public static final String GENDER_FEMALE = "2";

    public static final class CheckAccountMessageConfig {
        public static final String JSON_REQUEST_KEY_ACCOUNT = "account";
        public static final String JSON_REQUEST_KEY_ACCOUNT_TYPE = "type";
    }

    public static final class RegisterMessageConfig {
        public static final String JSON_REQUEST_KEY_ACCOUNT = "account";
        public static final String JSON_REQUEST_KEY_PASSWORD = "password";
        public static final String JSON_REQUEST_KEY_ACCOUNT_TYPE = "type";
        public static final String JSON_REQUEST_KEY_CODE = "code";
    }

    public static final class LoginMessageConfig {
        public static final String JSON_REQUEST_KEY_ACCOUNT = "account";
        public static final String JSON_REQUEST_KEY_PASSWORD = "password";
        public static final String JSON_REQUEST_KEY_LOGIN_TYPE = "type";
        public static final String JSON_REQUEST_KEY_CODE = "code";

        public static final String JSON_RESPONSE_ROOT_INFO = "info";
        public static final String JSON_RESPONSE_ROOT_TOKEN = "token";

        public static final String JSON_RESPONSE_USER_NAME = "username";
        public static final String JSON_RESPONSE_NAME = "name";
        public static final String JSON_RESPONSE_NICK = "nick";
        public static final String JSON_RESPONSE_GENDER = "gender";
        public static final String JSON_RESPONSE_AGE = "age";
        public static final String JSON_RESPONSE_BIRTHDAY = "birth";
        public static final String JSON_RESPONSE_POSITION = "position";
        public static final String JSON_RESPONSE_WEIGHT = "weight";
        public static final String JSON_RESPONSE_HEIGHT = "height";
        public static final String JSON_RESPONSE_QQ = "qq";
        public static final String JSON_RESPONSE_MOBILE = "mobile";
        public static final String JSON_RESPONSE_EMAIL = "email";
        public static final String JSON_RESPONSE_IMG_URL = "imgUrl";
    }

    public static final class GetPersonalMessageConfig {
        public static final String JSON_REQUEST_KEY_ACCOUNT = "account";

        public static final String JSON_RESPONSE_ROOT_INFO = "info";

        public static final String JSON_RESPONSE_USER_NAME = "username";
        public static final String JSON_RESPONSE_NAME = "name";
        public static final String JSON_RESPONSE_NICK = "nick";
        public static final String JSON_RESPONSE_GENDER = "gender";
        public static final String JSON_RESPONSE_AGE = "age";
        public static final String JSON_RESPONSE_BIRTHDAY = "birth";
        public static final String JSON_RESPONSE_POSITION = "position";
        public static final String JSON_RESPONSE_WEIGHT = "weight";
        public static final String JSON_RESPONSE_HEIGHT = "height";
        public static final String JSON_RESPONSE_QQ = "qq";
        public static final String JSON_RESPONSE_MOBILE = "mobile";
        public static final String JSON_RESPONSE_EMAIL = "email";
        public static final String JSON_RESPONSE_IMG_URL = "imgUrl";
    }

    public static final class GetAccountMessageConfig {
        public static final String JSON_REQUEST_KEY_ACCOUNT = "account";

        public static final String JSON_RESPONSE_VIPID = "vipId";
        public static final String JSON_RESPONSE_USER_NAME = "username";
        public static final String JSON_RESPONSE_MOBILE = "mobile";
        public static final String JSON_RESPONSE_EMAIL = "email";
    }

    public static final class ModifyInfoSingleMessageConfig {
        public static final String JSON_REQUEST_KEY_KEY = "key";
        public static final String JSON_REQUEST_KEY_VALUE = "value";
    }

    public static final class ModifyInfoAllMessageConfig {
        public static final String JSON_REQUEST_NAME = "name";
        public static final String JSON_REQUEST_NICK = "nick";
        public static final String JSON_REQUEST_GENDER = "gender";
        public static final String JSON_REQUEST_AGE = "age";
        public static final String JSON_REQUEST_BIRTHDAY = "birthday";
        public static final String JSON_REQUEST_POSITION = "position";
        public static final String JSON_REQUEST_WEIGHT = "weight";
        public static final String JSON_REQUEST_HEIGHT = "height";
        public static final String JSON_REQUEST_QQ = "qq";
        public static final String JSON_REQUEST_IMG_URL = "img";
    }

    public static final class ModifyPasswordMessageConfig {
        public static final String JSON_REQUEST_OLD_PASSWORD = "oldPass";
        public static final String JSON_REQUEST_NEW_PASSWORD = "newPass";
    }

    public static final class ForgetPasswordMessageConfig {
        public static final String JSON_REQUEST_MOBILE = "mobile";
        public static final String JSON_REQUEST_PASSWORD = "password";
        public static final String JSON_REQUEST_VERIFICATION_TYPE = "type";
        public static final String JSON_REQUEST_VERIFICATION_CODE = "code";
    }

    public static final class LogoutMessageConfig {

    }

    public static final class OfflineUserConfigFile {
        public static final String KEY_TOKEN = "user_token";
        public static final String KEY_USER_JSON = "user_json";
        public static final String KEY_LOGIN_DATETIME = "login_datetime";
    }

    public static final class GetReachListConfig {
        public static final String JSON_REQUEST_BEING_TIME = "beginTime";
        public static final String JSON_REQUEST_END_TIME = "endTime";
        public static final String JSON_REQUEST_TYPE = "type";
        public static final String JSON_REQUEST_LONGITUDE = "longitude";
        public static final String JSON_REQUEST_LATITUDE = "latitude";
        public static final String JSON_REQUEST_PROVINCE = "province";
        public static final String JSON_REQUEST_CITY = "city";
        public static final String JSON_REQUEST_DISTRICT = "district";
        public static final String JSON_REQUEST_LIMITED = "limited";
        public static final String JSON_REQUEST_PAGE_NO = "pageNo";
        public static final String JSON_REQUEST_PAGE_SIZE = "pageSize";
        public static final String JSON_REQUEST_SORT = "sort";
    }

    public static final class GetCreateListConfig {
        public static final String JSON_REQUEST_PAGE_NO = "pageNo";
        public static final String JSON_REQUEST_PAGE_SIZE = "pageSize";
    }

    public static final class GetJoinListConfig {
        public static final String JSON_REQUEST_PAGE_NO = "pageNo";
        public static final String JSON_REQUEST_PAGE_SIZE = "pageSize";
    }

    public static final class GetMoveListConfig {
        public static final String JSON_REQUEST_PAGE_NO = "pageNo";
        public static final String JSON_REQUEST_PAGE_SIZE = "pageSize";
        public static final String JSON_REQUEST_PAGE_BEGIN_TIME = "beginTime";
        public static final String JSON_REQUEST_PAGE_END_TIME = "endTime";
    }

    public static final class GetMoveUploadConfig {
        public static final String JSON_REQUEST_PAGE_VIP_ID = "vipId";//	会员ID
        public static final String JSON_REQUEST_PAGE_SN = "sn";//		蓝牙SN号
        public static final String JSON_REQUEST_PAGE_FOOTER = "footer";//		左右脚 R右脚 L左脚
        public static final String JSON_REQUEST_PAGE_LONGITUDE = "longitude";//		经度
        public static final String JSON_REQUEST_PAGE_LATITUDE = "latitude";//		纬度
        public static final String JSON_REQUEST_PAGE_ADDRESS = "address";//		地址
        public static final String JSON_REQUEST_PAGE_BEGIN_TIME = "beginTime";//		开始时间(时间戳)
        public static final String JSON_REQUEST_PAGE_SPEND = "spend";//		运动时长
        public static final String JSON_REQUEST_PAGE_PICTURE = "picture";//		图片
        public static final String JSON_REQUEST_PAGE_ENDTIME = "endTime";//		结束时间(时间戳)
        public static final String JSON_REQUEST_PAGE_TOTAL_DIST = "totalDist";//		总距离
        public static final String JSON_REQUEST_PAGE_TOTAL_STEP = "totalStep";//		总步数
        public static final String JSON_REQUEST_PAGE_TOTAL_HOR_DIST = "totalHorDist";//		横向总距离
        public static final String JSON_REQUEST_PAGE_TOTAL_VER_DIST = "totalVerDist";//		纵向总距离
        public static final String JSON_REQUEST_PAGE_TOTAL_TIME = "totalTime";//		总时间
        public static final String JSON_REQUEST_PAGE_TOTAL_ACTIVE = "totalActiveTime";//		活跃总时间
        public static final String JSON_REQUEST_PAGE_ACTIVE_RATE = "activeRate";//		活跃时间占比
        public static final String JSON_REQUEST_PAGE_AVG_SPEED = "avgSpeed";//		平均移动速度
        public static final String JSON_REQUEST_PAGE_MAX_SPEED = "maxSpeed";//		最大移动速度
        public static final String JSON_REQUEST_PAGE_SPURT_COUNT = "spurtCount";//		冲向次数
        public static final String JSON_REQUEST_PAGE_BREAK_IN_COUNT = "breakinCount";//		变向次数
        public static final String JSON_REQUEST_PAGE_BREAK_IN_AVG_TIME = "breakinAvgTime";//		变向平均触底时间
        public static final String JSON_REQUEST_PAGE_VER_JUMP_POINT = "verJumpPoint";//		纵跳点(纵跳的高度的集合，以”,”分隔)
        public static final String JSON_REQUEST_PAGE_VER_JUMP_COUNT = "verJumpCount";//		纵跳次数
        public static final String JSON_REQUEST_PAGE_VER_JUMP_AVG_HIGH = "verJumpAvgHigh";//		纵跳平均高度
        public static final String JSON_REQUEST_PAGE_VER_JUMP_MAX_HIGH = "verJumpMaxHigh";//		纵跳最大高度
        public static final String JSON_REQUEST_PAGE_AVG_HOVER_TIME = "avgHoverTime";//		平均滞空时间
        public static final String JSON_REQUEST_PAGE_AVG_TOUCH_ANGLE = "avgTouchAngle";//		平均着地旋转角
        public static final String JSON_REQUEST_PAGE_TOUCH_TYPE = "touchType";//		着地类型
        public static final String JSON_REQUEST_PAGE_PERF_RANK = "perfRank";//		本场表现
        public static final String JSON_REQUEST_PAGE_RUN_RANK = "runRank";//		跑动等级
        public static final String JSON_REQUEST_PAGE_BREAK_RANK = "breakRank";//		突破等级
        public static final String JSON_REQUEST_PAGE_BOUNCE_RANK = "bounceRank";//		弹跳等级
        public static final String JSON_REQUEST_PAGE_AVG_SHOT_DIST = "avgShotDist";//		平均出手距离
        public static final String JSON_REQUEST_PAGE_MAX_SHOT_DIST = "maxShotDist";//		最大出手距离
        public static final String JSON_REQUEST_PAGE_HANDLE = "handle";//		手感
        public static final String JSON_REQUEST_PAGE_CALORIE = "calorie";//		消耗卡路里
        public static final String JSON_REQUEST_PAGE_TRAIL = "trail";//		运动轨迹
        public static final String JSON_REQUEST_PAGE_HEADER = "header";//	头100字节内容
        public static final String JSON_REQUEST_PAGE_FIELD_TYPE = "fieldType";//	头100字节内容
    }

    public static final class GetMoveUploadsConfig {
        public static final String JSON_REQUEST_SPORTS = "sports";//	会员ID
        public static final String JSON_REQUEST_PAGE_VIP_ID = "vipId";//	会员ID
        public static final String JSON_REQUEST_PAGE_SN = "sn";//		蓝牙SN号
        public static final String JSON_REQUEST_PAGE_FOOTER = "footer";//		左右脚 R右脚 L左脚
        public static final String JSON_REQUEST_PAGE_LONGITUDE = "longitude";//		经度
        public static final String JSON_REQUEST_PAGE_LATITUDE = "latitude";//		纬度
        public static final String JSON_REQUEST_PAGE_ADDRESS = "address";//		地址
        public static final String JSON_REQUEST_PAGE_BEGIN_TIME = "beginTime";//		开始时间(时间戳)
        public static final String JSON_REQUEST_PAGE_SPEND = "spend";//		运动时长
        public static final String JSON_REQUEST_PAGE_PICTURE = "picture";//		图片
        public static final String JSON_REQUEST_PAGE_ENDTIME = "endTime";//		结束时间(时间戳)
        public static final String JSON_REQUEST_PAGE_TOTAL_DIST = "totalDist";//		总距离
        public static final String JSON_REQUEST_PAGE_TOTAL_STEP = "totalStep";//		总步数
        public static final String JSON_REQUEST_PAGE_TOTAL_HOR_DIST = "totalHorDist";//		横向总距离
        public static final String JSON_REQUEST_PAGE_TOTAL_VER_DIST = "totalVerDist";//		纵向总距离
        public static final String JSON_REQUEST_PAGE_TOTAL_TIME = "totalTime";//		总时间
        public static final String JSON_REQUEST_PAGE_TOTAL_ACTIVE = "totalActiveTime";//		活跃总时间
        public static final String JSON_REQUEST_PAGE_ACTIVE_RATE = "activeRate";//		活跃时间占比
        public static final String JSON_REQUEST_PAGE_AVG_SPEED = "avgSpeed";//		平均移动速度
        public static final String JSON_REQUEST_PAGE_MAX_SPEED = "maxSpeed";//		最大移动速度
        public static final String JSON_REQUEST_PAGE_SPURT_COUNT = "spurtCount";//		冲向次数
        public static final String JSON_REQUEST_PAGE_BREAK_IN_COUNT = "breakinCount";//		变向次数
        public static final String JSON_REQUEST_PAGE_BREAK_IN_AVG_TIME = "breakinAvgTime";//		变向平均触底时间
        public static final String JSON_REQUEST_PAGE_VER_JUMP_POINT = "verJumpPoint";//		纵跳点(纵跳的高度的集合，以”,”分隔)
        public static final String JSON_REQUEST_PAGE_VER_JUMP_COUNT = "verJumpCount";//		纵跳次数
        public static final String JSON_REQUEST_PAGE_VER_JUMP_AVG_HIGH = "verJumpAvgHigh";//		纵跳平均高度
        public static final String JSON_REQUEST_PAGE_VER_JUMP_MAX_HIGH = "verJumpMaxHigh";//		纵跳最大高度
        public static final String JSON_REQUEST_PAGE_AVG_HOVER_TIME = "avgHoverTime";//		平均滞空时间
        public static final String JSON_REQUEST_PAGE_AVG_TOUCH_ANGLE = "avgTouchAngle";//		平均着地旋转角
        public static final String JSON_REQUEST_PAGE_TOUCH_TYPE = "touchType";//		着地类型
        public static final String JSON_REQUEST_PAGE_PERF_RANK = "perfRank";//		本场表现
        public static final String JSON_REQUEST_PAGE_RUN_RANK = "runRank";//		跑动等级
        public static final String JSON_REQUEST_PAGE_BREAK_RANK = "breakRank";//		突破等级
        public static final String JSON_REQUEST_PAGE_BOUNCE_RANK = "bounceRank";//		弹跳等级
        public static final String JSON_REQUEST_PAGE_AVG_SHOT_DIST = "avgShotDist";//		平均出手距离
        public static final String JSON_REQUEST_PAGE_MAX_SHOT_DIST = "maxShotDist";//		最大出手距离
        public static final String JSON_REQUEST_PAGE_HANDLE = "handle";//		手感
        public static final String JSON_REQUEST_PAGE_CALORIE = "calorie";//		消耗卡路里
        public static final String JSON_REQUEST_PAGE_TRAIL = "trail";//		运动轨迹
        public static final String JSON_REQUEST_PAGE_TYPE = "type";//		运动场地类型
        public static final String JSON_REQUEST_PAGE_HEADER = "header";//		头100字节
    }

    public static final class GetMoveDetailConfig {
        public static final String JSON_REQUEST_ID = "id";
    }

    public static final class GetMoveEvalConfig {
        public static final String JSON_REQUEST_VIP_ID = "vipId";
    }

    public static final class GetCreateReachConfig {
        public static final String JSON_REQUEST_TIME = "time";
        public static final String JSON_REQUEST_DURATION = "duration";
        public static final String JSON_REQUEST_PEOPLE = "people";
        public static final String JSON_REQUEST_TYPE = "type";
        public static final String JSON_REQUEST_PICTURE = "picture";
        public static final String JSON_REQUEST_LONGITUDE = "longitude";
        public static final String JSON_REQUEST_LATITUDE = "latitude";
        public static final String JSON_REQUEST_PROVINCE = "province";
        public static final String JSON_REQUEST_CITY = "city";
        public static final String JSON_REQUEST_DISTRICT = "district";
        public static final String JSON_REQUEST_STREET = "street";
        public static final String JSON_REQUEST_ADDRESS = "address";
        public static final String JSON_REQUEST_SLOGAN = "slogan";
        public static final String JSON_REQUEST_REMARKS = "remarks";
    }

    public static final class GetReachDetailConfig {
        public static final String JSON_REQUEST_ID = "id";
    }

    public static final class GetReachJoinConfig {
        public static final String JSON_REQUEST_ID = "id";
    }

    public static final class GetShoesBindConfig {
        public static final String JSON_REQUEST_PAGE_TYPE = "type";
        public static final String JSON_REQUEST_PAGE_SHOES_ID = "shoesId";
        public static final String JSON_REQUEST_PAGE_CODE = "code";
        public static final String JSON_REQUEST_PAGE_NAME = "name";
        public static final String JSON_REQUEST_PAGE_PRICE = "price";
        public static final String JSON_REQUEST_PAGE_COLOR = "color";
        public static final String JSON_REQUEST_PAGE_SIZE = "size";
        public static final String JSON_REQUEST_PAGE_STYLE = "style";
        public static final String JSON_REQUEST_PAGE_PICTURE = "picture";
        public static final String JSON_REQUEST_PAGE_BUY_TIME = "buyTime";
    }
    public static final class GetShoesUnBindConfig {
        public static final String JSON_REQUEST_PAGE_IDS = "ids";
    }

    public static final class GetTrainDetailConfig {
        public static final String JSON_REQUEST_ID = "id";
    }
    public static final class GetTrainUploadConfig {
        public static final String JSON_REQUEST_ID = "moveId";
        public static final String JSON_REQUEST_SOURCE = "source";
        public static final String JSON_REQUEST_TYPE = "type";
        public static final String JSON_REQUEST_PLATFORM = "platform";
        public static final String JSON_REQUEST_SAY = "say";
        public static final String JSON_REQUEST_IMG = "img";
        public static final String JSON_REQUEST_URL = "url";
    }

    public static final class GetTrainListConfig {
        public static final String JSON_REQUEST_ID = "tdId";
        public static final String JSON_REQUEST_PAGE_NO = "pageNo";
        public static final String JSON_REQUEST_PAGE_SIZE = "pageSize";
        public static final String JSON_REQUEST_TYPE = "type";
        public static final String JSON_REQUEST_POSITION = "position";
    }
    public static final class GetTrainCountConfig {
        public static final String JSON_REQUEST_ID = "id";
        public static final String JSON_REQUEST_COUNT= "count";
        public static final String JSON_REQUEST_READS= "reads";
    }

    public static final class GetShoesListConfig {
        public static final String JSON_REQUEST_STYLE_NUMBER = "styleNumber";
        public static final String JSON_REQUEST_MARKET_TIME = "marketTime";
        public static final String JSON_REQUEST_COLOR = "color";
        public static final String JSON_REQUEST_SIZE = "size";
        public static final String JSON_REQUEST_FOR_PEOPLE = "forPeople";
        public static final String JSON_REQUEST_FOR_SPACE = "forSpace";
        public static final String JSON_REQUEST_FOR_POSITION = "forPosition";
        public static final String JSON_REQUEST_STYLE = "style";
        public static final String JSON_REQUEST_FUNCTION = "function";
        public static final String JSON_REQUEST_MIN_PRICE = "minPrice";
        public static final String JSON_REQUEST_MAX_PRICE = "maxprice";
        public static final String JSON_REQUEST_PAGE_NO = "pageNo";
        public static final String JSON_REQUEST_PAGE_SIZE = "pageSize";

    }
}
