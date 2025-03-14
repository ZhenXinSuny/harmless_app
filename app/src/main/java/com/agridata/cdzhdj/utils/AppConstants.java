package com.agridata.cdzhdj.utils;

public class AppConstants {

    public static  String  AppUrlDown = "http://www.cdzhdj.cn/vbfs/storage/api/file/download?mid=";


    //服务协议
    public static final String AGREEMENT_URL = "http://www.cdzhdj.cn/project/apk/agreement.html";

    //隐私政策
    public static final String PRIVACY_URL = "http://www.cdzhdj.cn/project/apk/privacy.html";

    //UUID
    public static String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    /**
     * 村级防疫员、乡防疫管理员、县防疫管理员、市防疫管理员
     */
    public static String FangYiYuanID = "3b7b3be84b534e2b83e889d42c270579";//村级防疫员
    public static String XZFYMaster = "9741b9f0930c4f41af8bcbe1326a95a0";//乡防疫管理员

    public static String XianMaster = "b35a0931755f465a98c4325682ffc1b3";//县防疫管理员

    public static String ShiMaster = "f16767331c2247f985b374704acc9cc8";//市防疫管理员

    public  static  String  XDRID ="37757bda379e4d77b4ad35b631501439";//相对人

    public static String GUANFANGSHOUYIID = "4c93e19688f347aaaae6527bf4f3afea";//写死 官方兽医

    public static String  TUZAICHANGID="30d489cc64294e6b881a9d345246dd3c";//屠宰场工作人员（入场查验）

      public static String  WHHXCJDY="b7189dc965c44daa848c21bfcfa843d3";//无害化现场监督员






    public static String WeiXinUserId = "f9b2748b27e14481918b650d19219ca7";
    /**
     * code 码
     */
    public interface CODE {
        int STATE_CODE = 200;                                    //200 ro  400
        int CODE_ZERO = 0;                                    //0
        int CODE_ONE = 1;                                    //1
        int CODE_FUONE = -1;                                   //-1
    }

    /**
     * SharedPreferences常量
     */
    public interface SP {
        String RESOURCE_PATH = "resource_path";                          // App资源路径
        String DEVICE_ID = "device_id";                              // deviceId
        String XIAO_DU_TV = "xiao_du_tv";                              //默认消毒
        String IS_SHOW_SERVICE = "is_show_service";
    }


    public  interface  IsSelfWrite{
        int   ZZMY =1009;                          // App资源路径
        int  FYYMY= 1010;
    }
    /**
     * 验证器常量
     */
    public interface Validator {
        // 正则表达式：验证用户名（规则：11位手机号或邮箱）
        String REGEX_ACCOUNT = "^(\\d{11})|([\\w\\.\\-_]+@[\\w-]+[\\.][\\w-]+[\\w-_.]*)$";

        //正则表达式：验证校验码（规则：4位数字）
        String REGEX_CHECKCODE = "\\d{6}";

        // 正则表达式：验证密码（规则6-20位数字或字母或符号至少包括2种）
        //  String REGEX_PASSWORD = "^([A-Z]|[a-z]|[0-9]|[`-=[];,./~!@#$%^*()_+}{:?]){6,20}$";
        String REGEX_PASSWORD = "^(?!^[0-9]+$)(?!^[a-zA-Z]+$)(?!^[^a-zA-Z0-9]+$)^.{6,20}$";

        // 正则表达式：验证手机号
        // String REGEX_MOBILE = "^[1][34578][0-9]{9}$";
        String REGEX_MOBILE = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

        // 正则表达式：验证昵称
        String REGEX_NICKNAME = "^([\\u4e00-\\u9fa5\\w_-]*)$";

        // 正则表达式：验证汉字、字母、数字
        String REGEX_CHAR_NUMBER = "^([\\u4e00-\\u9fa5\\w]*)$";

        // 正则表达式：验证邮箱
        //String REGEX_EMAIL = "^([a-z0-9A-Z_]+[-|\\.]?)+@([a-z0-9A-Z]+?\\.)+[a-zA-Z]{2,}$";
        String REGEX_EMAIL = "[\\w\\.\\-_]+@[\\w-]+[\\.][\\w-]+[\\w-_.]*$";

        // @{senderCube:10225,name:Cymbi}消息验证
        String REGEX_AT_MESSAGE = "@\\{cube:[^,]*,name:[^\\}]*\\}";

        //{"qrKey":"ebd4b9ed-579d-4ccd-bb7c-91d2a200f0acqr_login","osName":"web","expiredTimestamp":1492756338953} 扫码登录
        String REGEX_SCAN_LOGIN = "\\{\"qrKey\":\".+\",\"osName\":\".+\",\"expiredTimestamp\":\\d*\\}";

        // 定义html转义字符的正则表达式 如：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        String REGEX_HTML_SPECIAL = "\\&[a-zA-Z]{1,10};";

        //群卡片电话格式
        String REGEX_CARD_PHONE = "^[0-9\\-]{3,20}$";
    }
}
