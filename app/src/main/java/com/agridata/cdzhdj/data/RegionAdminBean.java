package com.agridata.cdzhdj.data;

public class RegionAdminBean {

    public String name;
    public int img;
    public int  id;
    public int type;
    public int  num;
    public  String unitName;

    public RegionAdminBean(String name, int img, int id, int type, int num, String unitName) {
        this.name = name;
        this.img = img;
        this.id = id;
        this.type = type;
        this.num = num;
        this.unitName = unitName;
    }

    @Override
    public String toString() {
        return "RegionAdminBean{" +
                "name='" + name + '\'' +
                ", img=" + img +
                ", id=" + id +
                ", type=" + type +
                ", num=" + num +
                ", unitName='" + unitName + '\'' +
                '}';
    }
}
