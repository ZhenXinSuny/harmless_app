package com.agridata.cdzhdj.data;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-26 10:37.
 * @Description :描述
 */
public class UpDataImmuneDetailsBean {


    public  String BranchID;//免疫批次
    public  String Batch;//	疫苗批次
    public  String Capacity;//免疫剂量
    public  String Unit;//	单位
    public  String VaccineFactory;//产出企业简称
    public  int IIST;//	是否强制免疫
    public  int AnimalID;//动物ID
    public  String Mid;
    public  UpImmuneDetailsBean.DiseaseBean DiseaseID;
    public  UpImmuneDetailsBean.VaccineBean VaccineId;

    public String CategoryId ="a7126bcc198a41228172b4c97315411b";
    public String _PartId ="d5896b31964e425382df52f655dedfc2";
    public String CategoryName="37E78807D56F2718-T_IMMUNE_Detail";

    public static class DiseaseBean{
        public  String key;
        public String Name;

        @Override
        public String toString() {
            return "DiseaseBean{" +
                    "key='" + key + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }
    }
    public static class VaccineBean{
        public  String key;
        public String Name;

        @Override
        public String toString() {
            return "VaccineBean{" +
                    "key='" + key + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UpImmuneDetailsBean{" +
                "BranchID='" + BranchID + '\'' +
                ", Batch='" + Batch + '\'' +
                ", Capacity='" + Capacity + '\'' +
                ", Unit='" + Unit + '\'' +
                ", VaccineFactory='" + VaccineFactory + '\'' +
                ", IIST=" + IIST +
                ", AnimalID=" + AnimalID +
                ", DiseaseID=" + DiseaseID +
                ", VaccineId=" + VaccineId +
                ", CategoryId='" + CategoryId + '\'' +
                ", _PartId='" + _PartId + '\'' +
                '}';
    }
}
