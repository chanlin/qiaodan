package com.jordan.commonlibrary.config;


/**
 * Created by icean on 2017/1/27.
 */

public final class CommonSystemConfig {
    public static final String COMMON_DEFAULT_ADDRESS = "http://smartball.qiaodan.com:8090/jordan-web";
    //public static final String COMMON_DEFAULT_ADDRESS = "http://47.90.55.236:8080/jordan-web";

    public static final String URI_UPLOAD_FILE_SINGLE = "/v0/sys/media/upload.htm";
    public static final String URI_UPLOAD_FILE_SOME = "/v0/sys/media/uploads.htm";
    public static final String URI_LIST_FILE = "/v0/sys/media/view.htm";
    public static final String URI_CHECK_VERSION = "/v0/sys/ver/renew.htm";
    public static final String URI_SEND_CODE = "/v0/sys/sms/send.htm";
    public static final String URI_CHECK_CODE = "/v0/sys/sms/valid.htm";
    public static final String URI_BLUETOOTH_BIND = "/v0/user/bht/bind.htm";
    public static final String URI_BLUETOOTH_LIST = "/v0/user/bht/list.htm";
    public static final String URI_BLUETOOTH_UNBIND = "/v0/user/bht/unbund.htm";
    public static final String URI_BLUETOOTH_UPDATE = "/v0/user/bht/update.htm";

    public static final String ANDROID_DEVICE_TYPE = "1";//安卓系统
    public static final String HARDWARE_DEVICE_TYPE = "9";//硬件

    /**
     * 媒体文件用途
     */
    public static final String MEDIA_FILE_TYPE_HEAD_PORTRAIT = "1";//头像
    public static final String MEDIA_FILE_TYPE_PICTURE = "2";//图片
    public static final String MEDIA_FILE_TYPE_AUDIO = "3";//音频
    public static final String MEDIA_FILE_TYPE_VEDIO = "4";//视频
    public static final String MEDIA_FILE_TYPE_FILE = "5";//一般文件

    /**
     * 验证码使用场景
     */
    public static final String VERIFICATION_CODE_SECNE_REGISTER = "1";//注册
    public static final String VERIFICATION_CODE_SECNE_LOGIN = "2";//登陆
    public static final String VERIFICATION_CODE_SECNE_FORGET_PASSWORD = "3";//忘记密码
    public static final String VERIFICATION_CODE_SECNE_BIND = "4";//绑定电话
    public static final String VERIFICATION_CODE_SECNE_UN_BIND = "5";//解绑
    public static final String VERIFICATION_CODE_SECNE_CANCEL = "6";//销户

    /**
     * 使用何种方式来接收验证码
     */
    public static final String RECEIVE_WAY_SMS = "1";//短信接收
    public static final String RECEIVE_WAY_EMAIL = "2";//邮件接收


    /**
     * 上传单个或者多个媒体文件的请求/响应（成功）消息字段配置
     */
    public static final class MediasMessageConfig {
        public static final String JSON_MEDIA_ROOT = "medias";

        public static final String JSON_REQUEST_KEY_TYPE = "type";
        public static final String JSON_REQUEST_KEY_NAME = "name";
        public static final String JSON_REQUEST_KEY_FILE_CONTENT = "file";

        public static final String JSON_RESPONSE_KEY_ID = "id";
        public static final String JSON_RESPONSE_KEY_NAME = "name";
        public static final String JSON_RESPONSE_KEY_URL = "url";
    }

    /**
     * 查看媒体文件
     */
    public static final class ViewMediasMessageConfig {
        public static final String JSON_REQUEST_KEY_IDS = "ids";

        public static final String JSON_RESPONSE_KEY_ROOT = "medias";
        public static final String JSON_RESPONSE_KEY_ID = "id";
        public static final String JSON_RESPONSE_KEY_NAME = "name";
        public static final String JSON_RESPONSE_KEY_URL = "url";
    }

    /**
     * 检查应用程序版本
     */
    public static final class CheckAppUpdateMessageConfig {
        public static final String JSON_REQUEST_KEY_TYPE = "type";//设备类型，这里固定为1表示安卓

        public static final String JSON_RESPONSE_KEY_LOWEST_VERSION = "lowestVer";//最低版本
        public static final String JSON_RESPONSE_KEY_NEWEST_VERSION = "newVer";//最新版本
        public static final String JSON_RESPONSE_KEY_LINK = "link";//下载链接
    }

    /**
     * 获取验证码
     */
    public static final class GetCodeMessgaeCofig {
        public static String JSON_REQUEST_KEY_ACCOUNT = "account";
        public static String JSON_REQUEST_KEY_TYPE = "type";
        public static String JSON_REQUEST_KEY_GENRE = "genre";
    }

    /**
     * 检查验证码
     */
    public static final class CheckCodeMessgaeCofig {
        public static String JSON_REQUEST_KEY_ACCOUNT = "account";
        public static String JSON_REQUEST_KEY_TYPE = "type";
        public static String JSON_REQUEST_KEY_CODE = "code";
    }
    /**
     * 绑定蓝牙
     */
    public static final class BluetoothBindCofig {
        public static String JSON_REQUEST_KEY_NAME = "name";
        public static String JSON_REQUEST_KEY_SN = "sn";
        public static String JSON_REQUEST_KEY_MAC = "mac";
    }
    public static final class BluetoothUnBindCofig {
        public static String JSON_REQUEST_KEY_IDS = "ids";
    }
    public static final class BluetoothUpdateBindCofig {
        public static String JSON_REQUEST_KEY_NAME = "name";
        public static String JSON_REQUEST_KEY_ID = "id";
    }
}
