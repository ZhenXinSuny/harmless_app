package com.agridata.cdzhdj.data.law;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-20 14:24.
 * @Description :描述
 */
public class SpotCheckBean {
    public  TestResultBean  TestResult;

    public String Mid;
    public String CategoryId = "78c21f0133c6471bb3d6fa5421020137";
    public String CategoryName = "8A0983F66D9360FB-T_YZT_Enforcement";
    public String _PartId = "d5896b31964e425382df52f655dedfc2";

     public int  SpotCheckStatus;

     public  static class  TestResultBean{

     public List<String> result;

     public String description;

         @Override
         public String toString() {
             return "TestResultBean{" +
                     "result=" + result +
                     ", description='" + description + '\'' +
                     '}';
         }
     }

    @Override
    public String toString() {
        return "SpotCheckBean{" +
                "TestResult=" + TestResult +
                ", Mid='" + Mid + '\'' +
                ", CategoryId='" + CategoryId + '\'' +
                ", CategoryName='" + CategoryName + '\'' +
                ", _PartId='" + _PartId + '\'' +
                ", SpotCheckStatus=" + SpotCheckStatus +
                '}';
    }
}
