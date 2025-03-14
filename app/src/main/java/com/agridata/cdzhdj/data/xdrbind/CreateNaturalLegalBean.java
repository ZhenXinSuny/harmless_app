package com.agridata.cdzhdj.data.xdrbind;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-02 15:55.
 * @Description :描述
 */
public class CreateNaturalLegalBean {

    public String command="CreateNaturalLegal";
    public ParamsBean params;


    public   static class  ParamsBean {
        public  String UserId;
        public String XDRMid;
        public String CertNo;//身份证号
        public String Mobile;
        public String UserName;
        public int UserType;// "用户类型（1：自然人、2：法人） ",
        public String CorpName;//"法人名称",
        public String CertificateSno;//"法人社会信用代码或工商注册号",

    }


}
