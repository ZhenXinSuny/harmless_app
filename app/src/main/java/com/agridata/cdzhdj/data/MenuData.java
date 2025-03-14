package com.agridata.cdzhdj.data;

/**
 * @auther 56454
 * @date 2022/6/21
 * @time 17:00.
 */
public class MenuData {
    public String name;
    public int img;
    public int  id;

    public int  count = 0;

    public MenuData(String name, int img, int id) {
        this.name = name;
        this.img = img;
        this.id = id;
    }

    @Override
    public String toString() {
        return "MenuData{" +
                "name='" + name + '\'' +
                ", img=" + img +
                ", id=" + id +
                '}';
    }
}
