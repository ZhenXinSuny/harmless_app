package com.agridata.cdzhdj.data;

import java.io.Serializable;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 14:14.
 * 动物清单 bean
 */
public class AnimalMenuBean implements Serializable {

    public int Pos;
    public String EarTag;
    public String Weight;
    public String AnimalName;
    public String AnimalID;
    public int AnimalType;
    public String ShuLiang;
    public int  ShiFouMuPig;

    @Override
    public String toString() {
        return "AnimalMenuBean{" +
                "Pos=" + Pos +
                ", EarTag='" + EarTag + '\'' +
                ", Weight='" + Weight + '\'' +
                ", AnimalName='" + AnimalName + '\'' +
                ", AnimalID='" + AnimalID + '\'' +
                ", AnimalType=" + AnimalType +
                ", ShuLiang='" + ShuLiang + '\'' +
                ", ShiFouMuPig=" + ShiFouMuPig +
                '}';
    }
}
