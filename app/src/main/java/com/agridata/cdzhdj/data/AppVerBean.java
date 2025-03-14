package com.agridata.cdzhdj.data;

import java.io.Serializable;
import java.util.List;

/**
 * @auther 56454
 * @date 2022/7/5
 * @time 11:29.
 */

public class AppVerBean implements Serializable {

    public int Status;
    public int ErrorCode;
    public String Message;
    public List<ResultBean> Result;


    public static class ResultBean implements Serializable {
        public String Mid;
        public Object SourceId;
        public String Name;
        public String VersionNo;
        public String Remark;
        public String _PartId;
        public String FilePath;
        public long CreateAt;
        public long LastUpdate;
        public String CreateAtStr;
        public String LastUpdateStr;
        public String CreatorId;
        public String ModifierId;
        public String CreatorName;
        public String ModifierName;
        public String PartitionId;
        public String CategoryId;
        public String CategoryName;
        public String CategoryType;
        public List<String> PartitionIds;

        @Override
        public String toString() {
            return "ResultBean{" +
                    "Mid='" + Mid + '\'' +
                    ", SourceId=" + SourceId +
                    ", Name='" + Name + '\'' +
                    ", VersionNo='" + VersionNo + '\'' +
                    ", Remark='" + Remark + '\'' +
                    ", _PartId='" + _PartId + '\'' +
                    ", FilePath='" + FilePath + '\'' +
                    ", CreateAt=" + CreateAt +
                    ", LastUpdate=" + LastUpdate +
                    ", CreateAtStr='" + CreateAtStr + '\'' +
                    ", LastUpdateStr='" + LastUpdateStr + '\'' +
                    ", CreatorId='" + CreatorId + '\'' +
                    ", ModifierId='" + ModifierId + '\'' +
                    ", CreatorName='" + CreatorName + '\'' +
                    ", ModifierName='" + ModifierName + '\'' +
                    ", PartitionId='" + PartitionId + '\'' +
                    ", CategoryId='" + CategoryId + '\'' +
                    ", CategoryName='" + CategoryName + '\'' +
                    ", CategoryType='" + CategoryType + '\'' +
                    ", PartitionIds=" + PartitionIds +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AppVerBean{" +
                "Status=" + Status +
                ", ErrorCode=" + ErrorCode +
                ", Message='" + Message + '\'' +
                ", Result=" + Result +
                '}';
    }
}
