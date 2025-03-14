package com.agridata.cdzhdj.data.logbean;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-08-28 10:25.
 * @Description :日志 上传bean
 */
public class LogData {


    public String CategoryId = "ae3a1ccce66b449abc18577bd9197ff8";
    public String CategoryName = "5C7C768E11A108B9-T_WHH_CLog";
    public String _PartId = "d5896b31964e425382df52f655dedfc2";
    /**
     *  收集单号
     */
    public  String CollectionNumber;

    /**
     * 收集人名称
     */
    public String CollectionName;

    /**
     * 图片类型
     */
    public int PicType;

    /**
     * 上传状态
     */
    public int UploadStatus;

    /**
     * 上传时间
     */
    public String  UploadTime;

    /**
     * 上传方式（批量，单张）
     */
    public  int UploadType;

    public String ErrorMessage;

    @Override
    public String toString() {
        return "LogData{" +
                "CategoryId='" + CategoryId + '\'' +
                ", CategoryName='" + CategoryName + '\'' +
                ", _PartId='" + _PartId + '\'' +
                ", CollectionNumber='" + CollectionNumber + '\'' +
                ", CollectionName='" + CollectionName + '\'' +
                ", PicType=" + PicType +
                ", UploadStatus=" + UploadStatus +
                ", UploadTime='" + UploadTime + '\'' +
                ", UploadType=" + UploadType +
                ", ErrorMessage='" + ErrorMessage + '\'' +
                '}';
    }
}
