package com.agridata.cdzhdj.data.entrycheck;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-11-24 14:48.
 * @Description :描述
 */
public class StaffListBean {
    public int SlaughterStaffID;
    public String SlaughterStaffName;
    public int Amount;

    public int ID;

    @Override
    public String toString() {
        return "StaffList{" +
                "SlaughterStaffID=" + SlaughterStaffID +
                ", SlaughterStaffName='" + SlaughterStaffName + '\'' +
                ", Amount=" + Amount +
                ", ID=" + ID +
                '}';
    }
}
