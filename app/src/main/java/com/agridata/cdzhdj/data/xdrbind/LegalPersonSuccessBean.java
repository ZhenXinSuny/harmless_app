package com.agridata.cdzhdj.data.xdrbind;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-02 19:40.
 * @Description :描述
 */
public class LegalPersonSuccessBean {

    public int code;
    public String msg;
    public Data data;

    public static class Data {
        public String mid;
        public Object sourceId;
        public String name;
        public String partid;
        public String createAt;
        public String lastUpdate;
        public String creatorId;
        public String modifierId;
        public String partitionId;
        public List<String> partitionIds;
        public String categoryId;
        public boolean disabled;
        public boolean deleted;
        public String appId;
        public int id;
        public String userName;
        public LegalPersonInfo legalPersonInfo;
        public NaturalPersonInfo naturalPersonInfo;
        public String mobile;
        public String userId;
        public Object zhiWeiGUID;
        public String userType;

        public static class LegalPersonInfo {
            @SerializedName("LegalMobile")
            public String legalMobile;
            @SerializedName("LegalName")
            public String legalName;
            @SerializedName("CertificateSno")
            public String certificateSno;
            @SerializedName("CorpType")
            public String corpType;
            @SerializedName("CorpStatus")
            public String corpStatus;
            @SerializedName("LegalCertType")
            public String legalCertType;
            @SerializedName("LegalCertNo")
            public String legalCertNo;
            @SerializedName("LegalCertnoEndDate")
            public String legalCertnoEndDate;
            @SerializedName("LegalCertnoBeginDate")
            public String legalCertnoBeginDate;
            @SerializedName("CorpName")
            public String corpName;
        }

        public static class NaturalPersonInfo {
            @SerializedName("UserMobile")
            public String userMobile;
            @SerializedName("UserName")
            public String userName;
            @SerializedName("CertExpDate")
            public String certExpDate;
            @SerializedName("UserRealLv")
            public String userRealLv;
            @SerializedName("CreateTime")
            public String createTime;
            @SerializedName("CertType")
            public String certType;
            @SerializedName("CertTypeCode")
            public String certTypeCode;
            @SerializedName("CertNo")
            public String certNo;
            @SerializedName("CertEffDate")
            public String certEffDate;
        }
    }
}
