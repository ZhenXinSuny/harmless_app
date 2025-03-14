package com.agridata.cdzhdj.data;

/**
 * @auther 56454
 * @date 2022/6/27
 * @time 15:01.
 */
public class AnimalBean {

    public AnimalBean(String ID, String animalName) {
        this.ID = ID;
        AnimalName = animalName;
    }

    public  String ID;
    public String AnimalName;

    @Override
    public String toString() {
        return "AnimalBean{" +
                "ID='" + ID + '\'' +
                ", AnimalName='" + AnimalName + '\'' +
                '}';
    }
}
