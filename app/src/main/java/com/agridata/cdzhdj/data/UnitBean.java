package com.agridata.cdzhdj.data;

/**
 * @auther 56454
 * @date 2022/6/27
 * @time 15:01.
 */
public class UnitBean {

    public UnitBean(String ID, String unitName) {
        this.ID = ID;
        UnitName = unitName;
    }
    public  String ID;
    public String UnitName;

    @Override
    public String toString() {
        return "UnitBean{" +
                "ID='" + ID + '\'' +
                ", AnimalName='" + UnitName + '\'' +
                '}';
    }
}
