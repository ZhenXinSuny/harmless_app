package com.agridata.cdzhdj.data;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-14 17:35.
 * @Description :描述
 */
public class AnimalToDisBean {
    public int  DiseaseID;
    public String DiseaseName;

    @Override
    public String toString() {
        return "AnimalToDisBean{" +
                "DiseaseID=" + DiseaseID +
                ", DiseaseName='" + DiseaseName + '\'' +
                '}';
    }
}
