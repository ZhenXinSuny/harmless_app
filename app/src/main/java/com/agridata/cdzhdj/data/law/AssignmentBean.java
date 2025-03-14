package com.agridata.cdzhdj.data.law;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-17 16:20.
 * @Description :描述
 */
public class AssignmentBean {
    public String Mid;
    public String CategoryId = "78c21f0133c6471bb3d6fa5421020137";
    public String CategoryName = "8A0983F66D9360FB-T_YZT_Enforcement";
    public String _PartId = "d5896b31964e425382df52f655dedfc2";
    public int AssignmentStatus;
    public AssigningAgencyBean AssigningAgency;
    public AssignPersonBean AssignPerson;

    public   static class AssigningAgencyBean {
        public String Mid;
        public String Name;

    }

    public static class AssignPersonBean {
        public String Mid;
        public String Name;

    }
}
