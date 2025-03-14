package com.agridata.cdzhdj.data.entrycheck;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-11-24 15:10.
 * @Description :描述
 */
public class EntryCheckDZHBean {
    public String Name;
    public String CheckMid;
    public  String  BreederId;
    public String Amount;
    public String CertNo;

    @Override
    public String toString() {
        return "EntryCheckDZHBean{" +
                "Name='" + Name + '\'' +
                ", CheckMid='" + CheckMid + '\'' +
                ", BreederId='" + BreederId + '\'' +
                ", Amount='" + Amount + '\'' +
                ", CertNo='" + CertNo + '\'' +
                '}';
    }
}
