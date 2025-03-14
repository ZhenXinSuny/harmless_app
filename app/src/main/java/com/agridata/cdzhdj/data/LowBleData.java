package com.agridata.cdzhdj.data;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-22 17:05.
 * @Description :描述
 */
public class LowBleData {



    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "LowBleData{" +
                "address='" + address + '\'' +
                '}';
    }
}
