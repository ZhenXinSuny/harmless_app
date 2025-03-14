package com.agridata.cdzhdj.data;


import com.agridata.cdzhdj.dbutils.InfoConverter;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;


/**
 * @auther 56454
 * @date 2022/7/15
 * @time 13:53.
 */

@Entity
public class HarmlessAnimalBean  {

    @Id(autoincrement = true)
    public Long id;
    public int Status;
    public int ErrorCode;
    public String Message;

    @Convert(columnType = String.class, converter = InfoConverter.class)
    public List<ResultAnimalBean> Result;


    public HarmlessAnimalBean() {

    }

    @Generated(hash = 1387134274)
    public HarmlessAnimalBean(Long id, int Status, int ErrorCode, String Message,
            List<ResultAnimalBean> Result) {
        this.id = id;
        this.Status = Status;
        this.ErrorCode = ErrorCode;
        this.Message = Message;
        this.Result = Result;
    }


    public  class ResultAnimalBean  {

        public ResultAnimalBean() {

        }



        public String Mid;

        public String Name;
        @SerializedName("ID")
        public int AnimalID;
        public String AnimalName;
        public int AnimalLevel;
        public int AnimalParentID;
        public int AnimalLivedays;
        public int EartagCode;
        public int SortOrder;


        @Override
        public String toString() {
            return "ResultAnimalBean{" +
                    "Mid='" + Mid + '\'' +
                    ", Name='" + Name + '\'' +
                    ", AnimalID=" + AnimalID +
                    ", AnimalName='" + AnimalName + '\'' +
                    ", AnimalLevel=" + AnimalLevel +
                    ", AnimalParentID=" + AnimalParentID +
                    ", AnimalLivedays=" + AnimalLivedays +
                    ", EartagCode=" + EartagCode +
                    ", SortOrder=" + SortOrder +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HarmlessAnimalBean{" +
                "Status=" + Status +
                ", ErrorCode=" + ErrorCode +
                ", Message='" + Message + '\'' +
                ", Result=" + Result +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return this.Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public int getErrorCode() {
        return this.ErrorCode;
    }

    public void setErrorCode(int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getMessage() {
        return this.Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<ResultAnimalBean> getResult() {
        return this.Result;
    }

    public void setResult(List<ResultAnimalBean> Result) {
        this.Result = Result;
    }
}
