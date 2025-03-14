package com.agridata.cdzhdj.data.law;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-20 11:14.
 * @Description :描述 复查结果
 */
public class ReexaminationFillingBean {

    public String Mid;
    public String CategoryId = "78c21f0133c6471bb3d6fa5421020137";
    public String CategoryName = "8A0983F66D9360FB-T_YZT_Enforcement";
    public String _PartId = "d5896b31964e425382df52f655dedfc2";
    public int ReviewStatus;//	复查状态 1
    public int ReviewResult;//复查结果 0  1

    public String SignatureOfRechecker;//	复查人员签字
    public String SignatureOfThePersonReviewed;//	被复查人签字
    public String ReviewTime;//复查时间
    public String ReviewTheSituation;//发现问题复查情况
    public ReviewProblemShootingBean ReviewProblemShooting;//复查发现其它问题照片

    public  static class  ReviewProblemShootingBean{
        public List<String> imageList;

        @Override
        public String toString() {
            return "ProblemShootingBean{" +
                    "imageList=" + imageList +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ReexaminationFillingBean{" +
                "Mid='" + Mid + '\'' +
                ", CategoryId='" + CategoryId + '\'' +
                ", CategoryName='" + CategoryName + '\'' +
                ", _PartId='" + _PartId + '\'' +
                ", ReviewStatus=" + ReviewStatus +
                ", ReviewResult=" + ReviewResult +
                ", SignatureOfRechecker='" + SignatureOfRechecker + '\'' +
                ", SignatureOfThePersonReviewed='" + SignatureOfThePersonReviewed + '\'' +
                ", ReviewTime='" + ReviewTime + '\'' +
                ", ReviewTheSituation='" + ReviewTheSituation + '\'' +
                ", ReviewProblemShooting=" + ReviewProblemShooting +
                '}';
    }
}
