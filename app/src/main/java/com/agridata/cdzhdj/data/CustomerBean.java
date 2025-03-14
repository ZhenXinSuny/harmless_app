package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-01-13 14:14.
 * @Description :描述
 */
public class CustomerBean {

    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public Object message;
    @SerializedName("Result")
    public List<Result> result;

    public static class Result {
        @SerializedName("Mid")
        public String mid;
        @SerializedName("Name")
        public String name;
        public Object userId;
        public String funId;
        public Object login_bg;
        public Object login_logo;
        public Object login_title;
        public Object first_id;
        public String partId;
        public boolean alarm_enable;
        public Object alarm_topic;
        public Object alarm_page;
        public boolean alarm_audio;
        public Object mail_enable;
        public Object mail_page;
        public Object mail_topic;
        public Object mail_audio;
        public Object map_center;
        public Object map_zoom;
        public int map_index;
        public Object alarm_role;
        public Object mail_role;
        public int menuMode;
        public Object title_setting;
        //public LoginSetting login_setting;
        public Object modules;
        public boolean explain_enable;
        public Object explain_role;
        public Object explain_id;
        @SerializedName("SMSAppID")
        public Object sMSAppID;
        public Object map_tdkey;
        public int map_cst;
        public boolean register_enble;
        public Object register_roles;
        public Object first_mobile_id;
        public Object alarm_delay;
        public Object toastr_topic;
        public Object toastr_opts;
        public Object toastr_role;
        public Object toastr_enable;
        public Object isFuncMenu;
        public boolean isLoginSMS;
        public Object register_page;
        @SerializedName("CreateAt")
        public long createAt;
        @SerializedName("LastUpdate")
        public long lastUpdate;
        @SerializedName("CreateAtStr")
        public String createAtStr;
        @SerializedName("LastUpdateStr")
        public String lastUpdateStr;
        @SerializedName("CreatorId")
        public String creatorId;
        @SerializedName("ModifierId")
        public String modifierId;
        @SerializedName("CreatorName")
        public String creatorName;
        @SerializedName("ModifierName")
        public String modifierName;
        @SerializedName("PartitionId")
        public String partitionId;
        @SerializedName("PartitionIds")
        public List<String> partitionIds;
        @SerializedName("CategoryId")
        public String categoryId;
        @SerializedName("CategoryName")
        public String categoryName;
        @SerializedName("CategoryType")
        public String categoryType;

        public static class LoginSetting {
            public Logo logo;
            public Input input;
            public Title title;
            public Footer footer;
            public Position position;

            public static class Logo {
                public Object width;
                public Object height;
            }

            public static class Input {
                public String mode;
                public String color;
                public String margin;
            }

            public static class Title {
                public int size;
                public String color;
                public String margin;
            }

            public static class Footer {
                public String html;
            }

            public static class Position {
                public Object top;
                public Object left;
                public String mode;
                public Object right;
                public Object width;
                public Object bottom;
                public boolean enable;
                public Object height;
            }
        }
    }
}
