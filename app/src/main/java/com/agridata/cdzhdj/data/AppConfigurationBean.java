package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-12-29 11:50.
 * @Description :描述
 */
public class AppConfigurationBean {

    public int code;
    public String message;
    public DataBean data;

    public static class DataBean {
        @SerializedName("AppCode")
        public String appCode;
        @SerializedName("Appkey")
        public String appkey;
        @SerializedName("Signature")
        public boolean signature;
        @SerializedName("TokenStatus")
        public boolean tokenStatus;
        @SerializedName("SaaSApiUrl")
        public String saaSApiUrl;
        @SerializedName("DataApiUrl")
        public String dataApiUrl;
        @SerializedName("FileApiUrl")
        public String fileApiUrl;
        @SerializedName("WFApiUrl")
        public String wFApiUrl;
        @SerializedName("VBCCApiUrl")
        public String vBCCApiUrl;
        @SerializedName("SFApiUrl")
        public String sFApiUrl;
        @SerializedName("UploadType")
        public String uploadType;
        @SerializedName("ReceiveApiUrl")
        public String receiveApiUrl;
        @SerializedName("WebUrl")
        public String webUrl;
        @SerializedName("RootPart")
        public String rootPart;
        @SerializedName("TerraceId")
        public String terraceId;
        @SerializedName("MapCenter")
        public String mapCenter;
        @SerializedName("ConfigCode")
        public String configCode;
        @SerializedName("IdentityType")
        public String identityType;
        @SerializedName("VBIOT")
        public VBIOTBean vBIOT;
        @SerializedName("ProvinceCode")
        public String provinceCode;
        @SerializedName("ProvinceId")
        public String provinceId;
        @SerializedName("SecretKey")
        public String secretKey;

        public static class VBIOTBean {
            @SerializedName("PartId")
            public String partId;
        }
    }
}
