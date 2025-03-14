package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @auther 56454
 * @date 2022/7/19
 * @time 16:03.
 */
public class AnimalName   {



    private List<Animal> Result;

    @Override
    public String toString() {
        return "AnimalName{" +
                "Result=" + Result +
                '}';
    }

    public static  class  Animal {
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
            return "AnimalName{" +
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




}
